package com.stuartvancampen.favorplus.transaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.background.AsyncJsonHTTPTask;
import com.stuartvancampen.favorplus.session.Session;
import com.stuartvancampen.favorplus.user.User;
import com.stuartvancampen.favorplus.user.UserList;
import com.stuartvancampen.favorplus.util.MyActivity;

/**
 * Created by stuart on 1/18/16.
 */
public class NewTransactionDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = CreateTransactionDialogFragment.class.getSimpleName();
    private UserList mFriendList;
    private User mFriend;
    private EditText mValueText;
    private EditText mDescriptionText;
    private User mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFriend = null;
    }

    public void show(FragmentManager fragmentManager, UserList friendList) {
        show(fragmentManager, TAG);
        mFriendList = friendList;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.new_transaction_unknown_friend, null, false);

        TextView userName = (TextView) dialog.findViewById(R.id.user_name);
        mUser = Session.getInstance().getLoggedInUser();
        userName.setText(mUser.getFullName() + " owes");

        Spinner spinner = (Spinner) dialog.findViewById(R.id.friend_name_spinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        for (User friend : mFriendList) {
            adapter.add(friend.getFirstName());
        }
        spinner.setOnItemSelectedListener(this);

        mValueText = (EditText) dialog.findViewById(R.id.transaction_value_entry);
        mDescriptionText = (EditText) dialog.findViewById(R.id.transaction_description_entry);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.create_transaction_button)
                .setView(dialog)
                .setPositiveButton(R.string.submit, mOnPostListener)
                .setNegativeButton(R.string.cancel, mOnCancelListener)
                .show();
    }

    DialogInterface.OnClickListener mOnPostListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (mFriend != null ) {
                Transaction newTransaction = new Transaction(mUser, mFriend, Long.valueOf(mValueText.getText().toString()), mDescriptionText.getText().toString());
                ((MyActivity) getActivity()).startAsyncTask(new AsyncJsonHTTPTask<>(Transaction.class, newTransaction, Transaction.getCreateUrl(getActivity()), AsyncJsonHTTPTask.HttpVerb.POST));

                Toast.makeText(getActivity(), "You owe " + mFriend.getFirstName() + " one!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
            else {
                Toast.makeText(getActivity(), "Select Friend", Toast.LENGTH_SHORT).show();
            }
        }
    };

    DialogInterface.OnClickListener mOnCancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dismiss();
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mFriend = mFriendList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mFriend = null;
    }
}

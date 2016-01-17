package com.stuartvancampen.favorplus.transaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.background.AsyncJsonHTTPTask;
import com.stuartvancampen.favorplus.session.Session;
import com.stuartvancampen.favorplus.user.User;
import com.stuartvancampen.favorplus.util.MyActivity;

/**
 * Created by stuart on 1/13/16.
 */
public class CreateTransactionDialogFragment extends DialogFragment {

    private static final String TAG = CreateTransactionDialogFragment.class.getSimpleName();
    private User mFriend;
    private EditText mValueText;
    private EditText mDescriptionText;
    private User mUser;

    public void show(FragmentManager fragmentManager, User friend) {
        show(fragmentManager, TAG);
        mFriend = friend;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.new_transaction, null, false);

        TextView userName = (TextView) dialog.findViewById(R.id.user_name);
        mUser = Session.getInstance().getLoggedInUser();
        userName.setText(mUser.getFullName());

        TextView friendName = (TextView) dialog.findViewById(R.id.friend_name);
        friendName.setText(mFriend.getFullName());

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
            Transaction newTransaction = new Transaction(mUser, mFriend, Long.valueOf(mValueText.getText().toString()), mDescriptionText.getText().toString());
            ((MyActivity) getActivity()).startAsyncTask(new AsyncJsonHTTPTask<>(Transaction.class, newTransaction, Transaction.getCreateUrl(getActivity()), AsyncJsonHTTPTask.HttpVerb.POST));

            Toast.makeText(getActivity(), "You owe " + mFriend.getFirstName() + " one!", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    };

    DialogInterface.OnClickListener mOnCancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dismiss();
        }
    };
}

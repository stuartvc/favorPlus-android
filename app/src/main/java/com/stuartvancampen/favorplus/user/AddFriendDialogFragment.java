package com.stuartvancampen.favorplus.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.background.AsyncJsonHTTPTask;
import com.stuartvancampen.favorplus.friendships.Friendship;
import com.stuartvancampen.favorplus.friendships.FrienshipRequest;
import com.stuartvancampen.favorplus.util.MyActivity;

/**
 * Created by stuart on 1/20/16.
 */
public class AddFriendDialogFragment extends DialogFragment {

    private static final String TAG = AddFriendDialogFragment.class.getSimpleName();
    private EditText mFriendEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, TAG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.add_friend_dialog_fragment, null, false);

        mFriendEmail = (EditText) dialog.findViewById(R.id.friend_email_entry);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add_friend)
                .setView(dialog)
                .setPositiveButton(R.string.submit, mOnPostListener)
                .setNegativeButton(R.string.cancel, mOnCancelListener)
                .show();
    }

    DialogInterface.OnClickListener mOnPostListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String email = mFriendEmail.getText().toString();
            if (email.length() != 0) {
                FrienshipRequest frienshipRequest = new FrienshipRequest(null, email);
                ((MyActivity) getActivity()).startAsyncTask(new AsyncJsonHTTPTask<>(Friendship.class, frienshipRequest, FrienshipRequest.getCreateUrl(getActivity()), AsyncJsonHTTPTask.HttpVerb.POST));

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
}

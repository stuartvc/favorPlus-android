package com.stuartvancampen.favorplus.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stuartvancampen.favorplus.R;
import com.stuartvancampen.favorplus.maindrawer.MainDrawerActivity;
import com.stuartvancampen.favorplus.session.Session;
import com.stuartvancampen.favorplus.util.BaseFragment;

/**
 * Created by Stuart on 17/10/2015.
 */
public class LoginFragment extends BaseFragment implements OnLoginCallback {

    private ProgressDialog mLoadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session session = Session.getInstance();
        if (!session.isLoggedIn()) {
            new ASyncLoginRequest(this, getActivity());
        }
        else {
            logInSuccess();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        final EditText email = (EditText) view.findViewById(R.id.user_email);
        final EditText password = (EditText) view.findViewById(R.id.user_password);

        TextView loginButton = (TextView) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(email.getText().toString(), password.getText().toString());
            }
        });

        TextView signUpButton = (TextView) view.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(SignUpActivity.create(getActivity()));
            }
        });

        return view;
    }

    private void login(String email, String password) {
        Toast.makeText(getActivity(),
                "email:" + email + ", password:" + password,
                Toast.LENGTH_LONG)
             .show();

        mLoadingDialog = new ProgressDialog(getActivity());
        mLoadingDialog.setMessage("Loading...");
        mLoadingDialog.show();

        new ASyncLoginRequest(email, password, this, getActivity());
    }

    private void logInSuccess() {
        //getActivity().startActivity(PostsActivity.create(getActivity()));
        //TODO start app
        getActivity().startActivity(MainDrawerActivity.create(getActivity()));
        getActivity().finish();
    }

    @Override
    public void onLoginComplete(boolean success) {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
        if (success) {
            logInSuccess();
        }
        else {
            Toast.makeText(getActivity(), R.string.authentication_error, Toast.LENGTH_LONG).show();
        }
    }
}

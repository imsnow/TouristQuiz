package ru.mishaignatov.touristquiz.ui.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import ru.mishaignatov.touristquiz.R;

/***
 * Created by Mike on 17.02.2016.
 */
public class EnterNameDialog extends DialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.dialog_enter_name, container, false);

        CallbackManager callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton)v.findViewById(R.id.facebook_login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "success =" + loginResult.getAccessToken().toString());
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "error");
        }});

        return v;
    }
}

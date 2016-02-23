package ru.mishaignatov.touristquiz.ui.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;

import ru.mishaignatov.touristquiz.R;

/***
 * Created by Mike on 17.02.2016.
 */
public class EnterNameDialog extends DialogFragment implements View.OnClickListener {

    private Button mCheckButton, mSendButton;
    private EditText mNameEdit;
    private CallbackManager callbackManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.dialog_enter_name, container, false);

        callbackManager = CallbackManager.Factory.create();
        /*
        LoginButton loginButton = (LoginButton)v.findViewById(R.id.facebook_login_button);
        loginButton.setVisibility(View.GONE); //
        loginButton.setFragment(this);
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
            }
        });
        */

        v.findViewById(R.id.button_cancel).setOnClickListener(this);
        mSendButton = (Button)v.findViewById(R.id.button_send);
        mSendButton.setOnClickListener(this);

        mCheckButton = (Button)v.findViewById(R.id.button_check);
        mCheckButton.setOnClickListener(this);

        disableButton();

        mNameEdit = (EditText)v.findViewById(R.id.name_edit_text);
        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    private void disableButton(){
        mCheckButton.setEnabled(false);
        mSendButton.setEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button_check:
                break;
            case R.id.button_cancel:
                dismiss();
                break;
            case R.id.button_send:
                break;
        }
    }
}

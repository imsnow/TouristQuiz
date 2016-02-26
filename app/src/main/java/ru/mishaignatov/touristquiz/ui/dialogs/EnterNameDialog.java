package ru.mishaignatov.touristquiz.ui.dialogs;

import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.User;
import ru.mishaignatov.touristquiz.presenters.EnterNamePresenter;
import ru.mishaignatov.touristquiz.presenters.EnterNamePresenterImpl;
import ru.mishaignatov.touristquiz.ui.fragments.LeaderBoardFragment;
import ru.mishaignatov.touristquiz.ui.views.EnterNameView;

/***
 * Created by Mike on 17.02.2016.
 */
public class EnterNameDialog extends BaseDialogFragment implements View.OnClickListener, EnterNameView {

    private Button /*mCheckButton,*/ mSendButton;
    private EditText mNameEdit;
    private ProgressBar mProgressBar;
    private ImageView mAcceptView;

    private EnterNamePresenter mPresenter;

    private String mName;
    private User.TypeName type;
    //private CallbackManager callbackManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_enter_name, container, false);

        v.findViewById(R.id.button_cancel).setOnClickListener(this);

        LoginButton loginButton = (LoginButton)v.findViewById(R.id.facebook_login_button);

        mSendButton = (Button)v.findViewById(R.id.button_send);
        mSendButton.setOnClickListener(this);
        mSendButton.setEnabled(false);

        mProgressBar = (ProgressBar)v.findViewById(R.id.enter_name_progress_bar);
        mAcceptView  = (ImageView)v.findViewById(R.id.image_accept);

        mNameEdit = (EditText)v.findViewById(R.id.name_edit_text);
        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                onWaitingUser();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 3){
                    mName = s.toString();
                    mPresenter.checkName(mName);
                }
            }
        });

        mPresenter = new EnterNamePresenterImpl(this, getContext());

        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button_cancel:
                result();
                break;
            case R.id.button_send:
                mPresenter.sendName();
                break;
        }
    }

    @Override
    public void onWaitingUser() {
        mSendButton.setEnabled(false);
        mProgressBar.setVisibility(View.GONE);
        mAcceptView.setVisibility(View.GONE);
    }

    @Override
    public void onCheckingName() {
        mSendButton.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);
        mAcceptView.setVisibility(View.GONE);
    }

    @Override
    public void onBusyName() {
        Toast.makeText(getActivity(), "Это имя занято", Toast.LENGTH_LONG).show();
        mAcceptView.setVisibility(View.VISIBLE);
        mAcceptView.setImageResource(R.drawable.ic_close);
        mProgressBar.setVisibility(View.GONE);
        mSendButton.setEnabled(false);
    }

    @Override
    public void onAcceptingName() {
        mAcceptView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mAcceptView.setImageResource(R.drawable.ic_accept);
        mSendButton.setEnabled(true);
    }

    private void result(){
        LeaderBoardFragment frag = (LeaderBoardFragment) getTargetFragment();
        frag.onResultDialog();
        dismiss();
    }

    @Override
    public void onNameOk() {
        result();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        result();
        super.onCancel(dialog);
    }

    @Override
    public int getTitleColor() {
        return R.color.success_title;
    }

    @Override
    public int getTitleString() {
        return R.string.dialog_enter_name_title;
    }
}

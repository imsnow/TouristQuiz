package ru.mishaignatov.touristquiz.ui.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.Utils;
import ru.mishaignatov.touristquiz.presenters.EnterNamePresenter;
import ru.mishaignatov.touristquiz.presenters.EnterNamePresenterImpl;
import ru.mishaignatov.touristquiz.ui.fragments.LeaderBoardFragment;
import ru.mishaignatov.touristquiz.ui.views.EnterNameView;

/***
 * Created by Mike on 17.02.2016.
 */
public class EnterNameDialog extends BaseDialogFragment implements View.OnClickListener, EnterNameView {

    private ProgressBar mProgressBar;
    private ImageView mAcceptView;

    private EnterNamePresenter mPresenter;

    private String mName;
    private CallbackManager callbackManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new EnterNamePresenterImpl(this, getContext());

        callbackManager = CallbackManager.Factory.create();

        final TextView text = new TextView(getContext());
        text.setText(getString(R.string.dialog_enter_name_hint));
        text.setGravity(Gravity.CENTER);
        text.setPadding(0, 0, 0 , Utils.dpToPx(8));
        addView(text);

        final LoginButton loginButton = new LoginButton(getContext());
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, mPresenter.getCallback());
        addView(loginButton);

        final TextView or = new TextView(getContext());
        or.setText(getString(R.string.or));
        or.setGravity(Gravity.CENTER);
        addView(or);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.item_enter_name, getContainer(), false);

        mProgressBar = (ProgressBar)ll.findViewById(R.id.enter_name_progress_bar);
        mAcceptView  = (ImageView)ll.findViewById(R.id.image_accept);

        EditText mNameEdit = (EditText) ll.findViewById(R.id.name_edit_text);
        mNameEdit.setSingleLine();
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
        addView(ll);

        addCancelAndOkButtons(R.string.send, this);
        mSendButton.setEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult");
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        switch (tag){
            case "cancel": result(); break;
            case "send": mPresenter.sendName(); break;
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
    public void closeDialog() {
        dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        result();
        super.onCancel(dialog);
    }

    @Override
    public int getTitleColor() {
        return R.drawable.dialog_title_green;
    }

    @Override
    public int getTitleString() {
        return R.string.dialog_enter_name_title;
    }
}

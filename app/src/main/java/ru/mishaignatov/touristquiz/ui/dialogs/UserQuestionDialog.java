package ru.mishaignatov.touristquiz.ui.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.server.ApiHelper;

/**
 * Created by Leva on 16.01.2016.
 **/
public class UserQuestionDialog extends DialogFragment implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener {

    private EditText mQusetionText;
    private EditText mAnswerText;
    private EditText mCountryText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.fragment_dialog_user_question, null);
        v.findViewById(R.id.button_send).setOnClickListener(this);
        v.findViewById(R.id.button_cancel).setOnClickListener(this);

        mQusetionText = (EditText) v.findViewById(R.id.user_question_text);
        mAnswerText = (EditText) v.findViewById(R.id.user_question_answers);
        mCountryText = (EditText) v.findViewById(R.id.user_question_country);
        //v.findViewById(R.id.btnMaybe).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button_send:
                sendQuestion();
                break;
            case R.id.button_cancel:
                dismiss();
                break;
        }
    }

    private void sendQuestion(){
        if (mQusetionText.length() == 0 || mAnswerText.length() == 0 || mCountryText.length() == 0){
            Toast.makeText(getActivity(), "Заполните все поля", Toast.LENGTH_LONG).show();
            return;
        }

        ApiHelper.getHelper(getActivity()).userQuestion(GameManager.getInstance(getActivity()).getUser(),
                mQusetionText.getText().toString(), mAnswerText.getText().toString(), mCountryText.getText().toString(), this, this);
    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(getActivity(), "Спасибо. Твой вопрос появится в ближайших версиях", Toast.LENGTH_LONG).show();
        dismiss();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), "Ошибка. проверьте подключение к интернету", Toast.LENGTH_LONG).show();
    }
}

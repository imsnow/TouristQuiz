package ru.mishaignatov.touristquiz.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
public class UserQuestionDialog extends BaseDialogFragment implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener {

    private EditText mQuestionText;
    private EditText mAnswerText;
    private EditText mCountryText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_user_question, getContainer(), false);
        v.findViewById(R.id.button_send).setOnClickListener(this);
        v.findViewById(R.id.button_cancel).setOnClickListener(this);

        mQuestionText = (EditText) v.findViewById(R.id.user_question_text);
        mAnswerText = (EditText) v.findViewById(R.id.user_question_answers);
        mCountryText = (EditText) v.findViewById(R.id.user_question_country);

        addView(v);
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
        if (mQuestionText.length() == 0 || mAnswerText.length() == 0 || mCountryText.length() == 0){
            Toast.makeText(getActivity(), "Заполните все поля", Toast.LENGTH_LONG).show();
            return;
        }

        ApiHelper.getHelper(getActivity()).userQuestion(GameManager.getInstance(getActivity()).getUser(),
                mQuestionText.getText().toString(), mAnswerText.getText().toString(), mCountryText.getText().toString(), this, this);
    }

    @Override
    public void onResponse(String response) {
        Log.d("TAG", response);
        Toast.makeText(getActivity(), "Спасибо. Твой вопрос появится в ближайших версиях", Toast.LENGTH_LONG).show();
        dismiss();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), "Ошибка. проверьте подключение к интернету", Toast.LENGTH_LONG).show();
        Log.d("TAG", "error = " + error.getMessage());
    }

    @Override
    public int getTitleColor() {
        return R.drawable.dialog_title_green;
    }

    @Override
    public int getTitleString() {
        return R.string.dialog_question_title;
    }
}

package ru.mishaignatov.touristquiz.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.orm.Question;

/**
 * Created by Leva on 22.12.2015.
 *
 */
public class QuestionFragment extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {

    private TextView questionText, mTimerText;
    private AnswerButton button1, button2, button3, button4;
    private FrameLayout layout;

    private Question mCurrentQuestion;

    private ActivityInterface headerInterface;

    private Handler mHandler = new Handler();
    private int mTimerCnt = GameManager.QUESTION_TIME;
    private boolean isCount = true;

    // temp
    private int drawables[] = {R.drawable.lime100, R.drawable.deep_orange, R.drawable.green100, R.drawable.blue100};

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        headerInterface = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_question, container, false);

        TextView error = (TextView)v.findViewById(R.id.error_text);
        error.setOnClickListener(this);

        layout = (FrameLayout)v.findViewById(R.id.layout);

        mTimerText = (TextView)v.findViewById(R.id.timer_view);
        questionText = (TextView)v.findViewById(R.id.quiz_text);
        button1      = (AnswerButton)v.findViewById(R.id.button1);
        button2      = (AnswerButton)v.findViewById(R.id.button2);
        button3      = (AnswerButton)v.findViewById(R.id.button3);
        button4      = (AnswerButton)v.findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        update();

        return v;
    }


    private Runnable showTimerValue = new Runnable() {

        @Override
        public void run() {
            if(isCount) {
                mTimerCnt--;
                mTimerText.setText(String.valueOf(mTimerCnt));
                if(mTimerCnt>0)
                    mHandler.postDelayed(showTimerValue, 1000);
            }
        }
    };

    public void update(){

        mTimerCnt = GameManager.QUESTION_TIME;
        mTimerText.setText(String.valueOf(mTimerCnt));
        isCount = true;
        updateQuestion();
        headerInterface.onUpdateHeader("");
        mHandler.postDelayed(showTimerValue, 1000);
    }

    private void updateQuestion(){

        mCurrentQuestion = GameManager.getInstance(getActivity()).getQuestion();

        if(mCurrentQuestion == null) { // Вопросы по этой стране закончились
            DialogHelper.showDialogNextLevel(getActivity(), this);
            return;
        }

        String[] list = mCurrentQuestion.getRandomListAnswers();

        questionText.setText(mCurrentQuestion.quiz);
        button1.setText(list[0].trim());
        button2.setText(list[1].trim());
        button3.setText(list[2].trim());
        button4.setText(list[3].trim());

        layout.setBackgroundResource(drawables[mCurrentQuestion.getType()]);
    }

    @Override
    public void onClick(View v) {
        Log.d("TAG", "onClick");
        int id = v.getId();
        if(id == R.id.error_text) { // send error
            DialogHelper.showDialogErrorInQuestion(getActivity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == -1) {// Send
                        //sendErrorEmail(currentQuiz);
                        Toast.makeText(getActivity(), "You can send message in next version", Toast.LENGTH_LONG).show();
                    }
                    if(which == -2) { // cancel
                        dialog.dismiss();
                    }
                }
            });
        }
        else if (v instanceof AnswerButton){ // One of fourth answers buttons
            isCount = false;
            String s = ((AnswerButton) v).getText().toString();
            GameManager.getInstance(getActivity()).userAnswered(this, mCurrentQuestion, s, mTimerCnt, this);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d("TAG", "DialogInterface = " + which);
        if(which == -1) // Success and failure dialog
            update();
        if(which == -2) // Next Level Dialog
            getActivity().onBackPressed();
    }

    /*
    private void sendErrorEmail(final Quiz quiz){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"ruwinmike@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Tourist Quiz");
        i.putExtra(Intent.EXTRA_TEXT, "Quiz = " + quiz.getText() + "\n"
                                    + "Answers = " + quiz.getStringAnswers() + "\n");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
    */
}

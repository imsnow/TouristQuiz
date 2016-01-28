package ru.mishaignatov.touristquiz.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.mishaignatov.touristquiz.QuestionPresenter;
import ru.mishaignatov.touristquiz.QuestionPresenterImpl;
import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.Utils;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.orm.Question;

/**
 * Created by Leva on 22.12.2015.
 *
 */
public class QuestionFragment extends Fragment implements
        View.OnClickListener, QuestionView {

    private TextView questionText;//, mTimerText;
    private AnswerButton button1, button2, button3, button4;
    private Animation shakeAnim;

    private FrameLayout layout;

    private Question mCurrentQuestion;
    //private Stopwatch mStopwatch;

    private ActivityInterface headerInterface;

    private QuestionPresenter mPresenter;

    // temp
    private String bg_resource[] = {"background/places.png", "background/kitchen.png", "background/geo.png", "background/history.png"};

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
        layout.setOnClickListener(null);

        //mTimerText = (TextView)v.findViewById(R.id.timer_view);
        questionText = (TextView)v.findViewById(R.id.quiz_text);
        button1      = (AnswerButton)v.findViewById(R.id.button1);
        button2      = (AnswerButton)v.findViewById(R.id.button2);
        button3      = (AnswerButton)v.findViewById(R.id.button3);
        button4      = (AnswerButton)v.findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        //mStopwatch = new Stopwatch(this);

        shakeAnim = AnimationUtils.loadAnimation(App.getContext(), R.anim.shake);
        shakeAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // stop timer
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // update question
                //updateQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mPresenter = new QuestionPresenterImpl(this);
        mPresenter.takeQuestion();
        //updateQuestion();

        return v;
    }
/*
    @Override
    public boolean userAnswered(Question question, String answer) {
        return Question.isAnswer(question, answer);
    }

    @Override
    public void updateQuestion(Question question){

        headerInterface.onUpdateHeader("");

        mCurrentQuestion = GameManager.getInstance(getActivity()).takeQuestion();

        if(mCurrentQuestion == null) { // Вопросы по этой стране закончились
            DialogHelper.showDialogNextLevel(getActivity(), this);
            return;
        }

        List<String> list = mCurrentQuestion.getRandomListAnswers();

        questionText.setText(mCurrentQuestion.quiz);
        button1.setText(list.get(0).trim());
        button2.setText(list.get(1).trim());
        button3.setText(list.get(2).trim());
        button4.setText(list.get(3).trim());

        //layout.setBackgroundResource(bg_resource[mCurrentQuestion.getType()]);

        Utils.setBackground(layout, loadBitmap(mCurrentQuestion.getType()));
        //layout.setBackground(loadBitmap(mCurrentQuestion.getType()));
        //mStopwatch.start();
    }
*/

    @Override
    public void onClick(View v) {
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
            //mStopwatch.stop();
            String s = ((AnswerButton) v).getText().toString();
            mPresenter.onAnswerButtonClick(s, (AnswerButton) v);

            /*
            if (userAnswered(mCurrentQuestion, s)) {
                // user answered true
                GameManager.getInstance(getActivity()).userAnsweredTrue(this, mCurrentQuestion, s, 10, this);
            }
            else
                // user failed
                v.startAnimation(shakeAnim);
            */
            //GameManager.getInstance(getActivity()).userAnswered(this, mCurrentQuestion, s, 10, this); // TODO
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
    /*
    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d("TAG", "DialogInterface = " + which);
        if(which == -1) // Success and failure dialog
            //updateQuestion();
            ;
        if(which == -2) // Next Level Dialog
            getActivity().onBackPressed();
    }
    */

    @Override
    public void onTrueAnswer() {
        showSuccessDialog();
    }

    @Override
    public void onFailAnswer(AnswerButton button) {

    }

    @Override
    public void showSuccessDialog() {

    }

    @Override
    public void showErrorInQuestionDialog() {

    }

    @Override
    public void showDialogNextLevel() {
        DialogHelper.showDialogNextLevel(getActivity(), this);
    }

    @Override
    public void startDeleteButton(int resource_id) {

    }

    @Override
    public void setQuestion(Question question) {

        mCurrentQuestion = question;

        if(mCurrentQuestion == null) { // Вопросы по этой стране закончились
            showDialogNextLevel();
            return;
        }

        List<String> list = mCurrentQuestion.getRandomListAnswers();

        questionText.setText(mCurrentQuestion.quiz);
        button1.setText(list.get(0).trim());
        button2.setText(list.get(1).trim());
        button3.setText(list.get(2).trim());
        button4.setText(list.get(3).trim());

        //layout.setBackgroundResource(bg_resource[mCurrentQuestion.getType()]);

        Utils.setBackground(layout, Utils.loadBitmapFromAssetes(getActivity().getApplicationContext(),
                bg_resource[mCurrentQuestion.getType()]));
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

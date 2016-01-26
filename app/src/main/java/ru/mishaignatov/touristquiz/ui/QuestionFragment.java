package ru.mishaignatov.touristquiz.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.Utils;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.Stopwatch;
import ru.mishaignatov.touristquiz.orm.Question;

/**
 * Created by Leva on 22.12.2015.
 *
 */
public class QuestionFragment extends Fragment implements
        View.OnClickListener, DialogInterface.OnClickListener, Stopwatch.Callback, QuestionView {

    private TextView questionText;//, mTimerText;
    private AnswerButton button1, button2, button3, button4;
    private Animation shakeAnim;

    private FrameLayout layout;

    private Question mCurrentQuestion;
    //private Stopwatch mStopwatch;

    private ActivityInterface headerInterface;

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
                updateQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        updateQuestion();

        return v;
    }

    @Override
    public boolean userAnswered(Question question, String answer) {
        return Question.isAnswer(question, answer);
    }

    @Override
    public void updateQuestion(){

        mCurrentQuestion = GameManager.getInstance(getActivity()).getQuestion();

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

    private Drawable loadBitmap(int index){
        AssetManager assetManager = getActivity().getAssets();

        InputStream istr = null;
        //Bitmap bitmap = null;
        try {
            istr = assetManager.open(bg_resource[index]);
            //bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
            e.printStackTrace();
        }
        return new BitmapDrawable(getResources(), istr);
    }

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
            if (userAnswered(mCurrentQuestion, s))
                // user answered true
                ;
            else
                // user failed
                v.startAnimation(shakeAnim);

            //GameManager.getInstance(getActivity()).userAnswered(this, mCurrentQuestion, s, 10, this); // TODO
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d("TAG", "DialogInterface = " + which);
        if(which == -1) // Success and failure dialog
            updateQuestion();
        if(which == -2) // Next Level Dialog
            getActivity().onBackPressed();
    }

    @Override
    public void onFinished() {

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

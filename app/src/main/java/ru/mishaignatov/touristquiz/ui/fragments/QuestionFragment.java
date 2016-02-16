package ru.mishaignatov.touristquiz.ui.fragments;

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

import java.text.DecimalFormat;
import java.util.List;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.Utils;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.orm.Question;
import ru.mishaignatov.touristquiz.presenters.QuestionPresenter;
import ru.mishaignatov.touristquiz.presenters.QuestionPresenterImpl;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.DialogHelper;
import ru.mishaignatov.touristquiz.ui.MainActivity;
import ru.mishaignatov.touristquiz.ui.dialogs.FailDialog;
import ru.mishaignatov.touristquiz.ui.dialogs.SuccessDialog;
import ru.mishaignatov.touristquiz.ui.views.QuestionView;
import ru.mishaignatov.touristquiz.ui.views.AnswerButton;

/**
 * Created by Leva on 22.12.2015.
 *
 */
public class QuestionFragment extends Fragment implements
        View.OnClickListener, QuestionView {

    private TextView questionText;
    private AnswerButton button1, button2, button3, button4;
    private Animation shakeAnim;

    private AnswerButton mClickedButton;

    private FrameLayout layout;

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
        shakeAnim.setAnimationListener(this);

        Bundle args = getArguments();
        int countryID = args.getInt("COUNTRY_ID");

        mPresenter = new QuestionPresenterImpl(this, countryID);
        mPresenter.takeQuestion();

        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.error_text) { // send error
            DialogHelper.showDialogErrorInQuestion(getActivity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == -1) {// Send
                        //sendErrorEmail(currentQuiz);
                        Toast.makeText(getActivity(), "You can send message in next version", Toast.LENGTH_LONG).show();
                    }
                    if (which == -2) { // cancel
                        dialog.dismiss();
                    }
                }
            });
        }
        else if (v instanceof AnswerButton){ // One of fourth answers buttons
            //mStopwatch.stop();
            String s = ((AnswerButton) v).getText().toString();
            mClickedButton = (AnswerButton) v;
            mPresenter.onAnswerButtonClick(s, mClickedButton);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onTrueAnswer(long timeInMills, int score, int millis) {
        showSuccessDialog(timeInMills, score, millis);
    }

    @Override
    public void onFailAnswer(AnswerButton button) {
        mClickedButton.startAnimation(shakeAnim); // after finishing of animation
    }

    @Override
    public void onTotalFailure() {
        FailDialog failDialog = new FailDialog();
        failDialog.setTargetFragment(this, 0x22);
        failDialog.show(getFragmentManager(), "fail");
    }

    @Override
    public void showSuccessDialog(long timeInMills, int score, int millis) {

        Bundle args = new Bundle();
        args.putString("KEY_TIME", String.format("%s", 1f * timeInMills / 1000));
        args.putString("KEY_SCORES", new DecimalFormat("#.##").format(score));
        args.putString("KEY_MILLIS", String.valueOf(millis));

        SuccessDialog successDialog = new SuccessDialog();
        successDialog.setArguments(args);
        successDialog.setTargetFragment(this, 0x22);
        successDialog.show(getFragmentManager(), "success");
    }

    @Override
    public void onResultDialog() {
        mPresenter.sendResult();
        mPresenter.takeQuestion();
    }

    @Override
    public void showErrorInQuestionDialog() {

    }

    @Override
    public void showDialogNextLevel() {
        DialogHelper.showDialogNextLevel(getActivity());
        getActivity().onBackPressed();
    }

    @Override
    public void startDeleteButton(int resource_id) {

    }

    @Override
    public void setQuestion(Question question) {

        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);

        if(question == null) { // Вопросы по этой стране закончились
            showDialogNextLevel();
            return;
        }
        List<String> list = question.getRandomListAnswers();

        questionText.setText(question.quiz);
        button1.setText(list.get(0).trim());
        button2.setText(list.get(1).trim());
        button3.setText(list.get(2).trim());
        button4.setText(list.get(3).trim());

        Utils.setBackground(layout, Utils.loadBitmapFromAssetes(getActivity().getApplicationContext(),
                bg_resource[question.getType()]));

        headerInterface.onUpdateHeader(mPresenter.getCurrentCountry());
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // if user click buttons, when anim
        button1.setClickable(false);
        button2.setClickable(false);
        button3.setClickable(false);
        button4.setClickable(false);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        button1.setClickable(true);
        button2.setClickable(true);
        button3.setClickable(true);
        button4.setClickable(true);
        mClickedButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // unused
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

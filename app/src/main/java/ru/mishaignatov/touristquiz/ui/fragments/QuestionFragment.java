package ru.mishaignatov.touristquiz.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.Utils;
import ru.mishaignatov.touristquiz.database.Question;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.User;
import ru.mishaignatov.touristquiz.presenters.QuestionPresenter;
import ru.mishaignatov.touristquiz.presenters.QuestionPresenterImpl;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.MainActivity;
import ru.mishaignatov.touristquiz.ui.dialogs.FailDialog;
import ru.mishaignatov.touristquiz.ui.dialogs.LevelDoneDialog;
import ru.mishaignatov.touristquiz.ui.dialogs.SuccessDialog;
import ru.mishaignatov.touristquiz.ui.views.CircleTextView;
import ru.mishaignatov.touristquiz.ui.views.QuestionView;

/**
 * Created by Leva on 22.12.2015.
 *
 */
public class QuestionFragment extends BaseToolbarFragment implements
        View.OnClickListener, QuestionView, User.ResultInterface {

    private TextView levelText;
//    private TextView questionText;
    private CircleTextView questionText;
    private TextView button1, button2, button3, button4;
    private Animation shakeAnim;

    private TextView mClickedButton;

    private FrameLayout layout;

    private ActivityInterface activityInterface;

    private QuestionPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity)
            activityInterface = (MainActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_question, container, false);

        initHeader(v);

        layout = (FrameLayout)v.findViewById(R.id.layout);
        layout.setOnClickListener(null);

        levelText    = (TextView) v.findViewById(R.id.question_level_name);
        //mTimerText = (TextView)v.findViewById(R.id.timer_view);
        questionText = (CircleTextView) v.findViewById(R.id.quiz_text);
        button1      = (TextView) v.findViewById(R.id.button1);
        button2      = (TextView)v.findViewById(R.id.button2);
        button3      = (TextView)v.findViewById(R.id.button3);
        button4      = (TextView)v.findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        //mStopwatch = new Stopwatch(this);

        shakeAnim = AnimationUtils.loadAnimation(App.getContext(), R.anim.shake);
        shakeAnim.setAnimationListener(this);

        Bundle args = getArguments();
        int countryID = args.getInt("COUNTRY_ID");

        mPresenter = new QuestionPresenterImpl(this, countryID, this);
        mPresenter.takeQuestion();

        return v;
    }

    @Override
    public void onClick(View v) {
        // One of fourth answers buttons
        if (v instanceof TextView){
            //mStopwatch.stop();
            String s = ((TextView) v).getText().toString();
            mClickedButton = (TextView) v;
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
        mClickedButton.setBackgroundResource(R.color.success_title);
        showSuccessDialog(timeInMills, score, millis);
    }

    @Override
    public void onFailAnswer(TextView button) {
        mClickedButton.setBackgroundResource(R.color.fail_title);
        mClickedButton.startAnimation(shakeAnim); // after finishing of animation
    }

    @Override
    public void onTotalFailure() {
        FailDialog failDialog = new FailDialog();
        failDialog.setTargetFragment(this, 0x22);
        //failDialog.show(getChildFragmentManager(), "fail");
        activityInterface.showDialog(failDialog, "fail");
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
        activityInterface.showDialog(successDialog, "success");
        //successDialog.show(getChildFragmentManager(), "success");
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
        //DialogHelper.showDialogNextLevel(getActivity());
        //LevelDoneDialog dialog = new LevelDoneDialog();
        //dialog.show(getActivity().getSupportFragmentManager(), "level_done");
        activityInterface.showDialog(new LevelDoneDialog(), "level_done");
        getActivity().onBackPressed();
    }

    @Override
    public void startDeleteButton(int resource_id) {

    }

    @Override
    public void setQuestion(Question question) {

        button1.setBackgroundResource(R.color.answer_button);
        button2.setBackgroundResource(R.color.answer_button);
        button3.setBackgroundResource(R.color.answer_button);
        button4.setBackgroundResource(R.color.answer_button);
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

        Utils.setBackground(layout, Utils.loadBitmapFromAssets(App.getContext(),
                App.BGs[question.getType()]));

        Utils.setBackground(questionText, Utils.loadBitmapFromAssets(App.getContext(), App.CIRCLEs[question.getType()]));

        //activityInterface.onUpdateHeader("");
        updateHeader("");

        levelText.setText(mPresenter.getCurrentLevel());
    }

    @Override
    public void questionNotAnswered() {
        mPresenter.setQuestionNotShown();
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

    @Override
    public void onFiveAnsweredTrue() {
        activityInterface.onShowAchievement();
    }

    @Override
    public void onTenAnsweredTrue() {
        activityInterface.onShowAchievement();
    }
}

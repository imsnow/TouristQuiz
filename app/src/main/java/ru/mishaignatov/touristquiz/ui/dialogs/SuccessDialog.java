package ru.mishaignatov.touristquiz.ui.dialogs;


import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.Utils;
import ru.mishaignatov.touristquiz.game.Bonus;
import ru.mishaignatov.touristquiz.ui.fragments.QuestionFragment;

/**
 * Created by Mike on 11.02.2016.
 **/
public class SuccessDialog extends BaseDialogFragment implements View.OnClickListener{

    private String time;
    private String scores;
    private String millis;
    private int factor = 1;
    boolean isBonus = false;

    private int titleColor = R.drawable.dialog_title_green;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            time = args.getString("KEY_TIME");
            scores = args.getString("KEY_SCORES");
            millis = args.getString("KEY_MILLIS");
            isBonus = args.getBoolean("KEY_IS_BONUS", false);
            if (isBonus)
                factor = args.getInt("KEY_BONUS_FACTOR");
        }
        addImageAndTextView(R.drawable.ic_time, R.string.show_time_result, time);
        final TextView scoresView = addImageAndTextView(R.drawable.ic_scores, R.string.show_plus_and_result, scores);
        addImageAndTextView(R.drawable.ic_miles, R.string.show_plus_and_result, millis);

        if (isBonus) {

            final Bonus b = new Bonus(factor == 2 ? Bonus.Type.BY_TWO : Bonus.Type.BY_THREE);

            titleColor = b.getResourceColor();

            TextView text = new TextView(getContext());
            text.setText(b.getTitle());
            text.setGravity(Gravity.CENTER);
            text.setTextColor(getResources().getColor(b.getTextColor()));
            text.setTextSize(32);

            addView(text);

            AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1.0f);
            alphaAnimation.setDuration(500);
            alphaAnimation.setStartOffset(300);
            alphaAnimation.setFillAfter(true);

            int newValue = Integer.parseInt(scores);
            int f = newValue * factor;
            String newScores = String.valueOf(f);
            scoresView.setTextColor(getResources().getColor(b.getTextColor()));
            scoresView.setText(getString(R.string.show_plus_and_result, newScores));

            scoresView.startAnimation(alphaAnimation);

            text.startAnimation(alphaAnimation);
        }

        addNextButton(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        result();
        super.onCancel(dialog);
    }

    @Override
    public void onClick(View v) {
        result();
        dismiss();
    }

    public void result(){
        QuestionFragment frag = (QuestionFragment)getTargetFragment();
        frag.onResultDialog();
    }

    @Override
    public int getTitleColor() {
        return titleColor;
    }

    @Override
    public int getTitleString() {
        return R.string.dialog_success_title;
    }
}

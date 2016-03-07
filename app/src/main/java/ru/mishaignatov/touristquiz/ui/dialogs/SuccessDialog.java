package ru.mishaignatov.touristquiz.ui.dialogs;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.ui.fragments.QuestionFragment;

/**
 * Created by Mike on 11.02.2016.
 **/
public class SuccessDialog extends BaseDialogFragment implements View.OnClickListener{

    private String time;
    private String scores;
    private String millis;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            time = args.getString("KEY_TIME");
            scores = args.getString("KEY_SCORES");
            millis = args.getString("KEY_MILLIS");
        }
        addImageAndTextView(R.drawable.ic_time, R.string.show_time_result, time);
        addImageAndTextView(R.drawable.ic_scores, R.string.show_plus_and_result, scores);
        addImageAndTextView(R.drawable.ic_miles, R.string.show_plus_and_result, millis);
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
        return R.drawable.dialog_title_green;
    }

    @Override
    public int getTitleString() {
        return R.string.dialog_success_title;
    }
}

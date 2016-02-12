package ru.mishaignatov.touristquiz.ui.dialogs;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.ui.fragments.QuestionFragment;

/**
 * Created by Mike on 11.02.2016.
 **/
public class SuccessDialog extends DialogFragment implements View.OnClickListener{

    private String time;
    private String scores;
    private String millis;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.dialog_success, null);
        v.findViewById(R.id.dialog_success_next).setOnClickListener(this);

        Bundle args = getArguments();
        if (args != null) {
            time = args.getString("KEY_TIME");
            scores = args.getString("KEY_SCORES");
            millis = args.getString("KEY_MILLIS");
        }

        ((TextView) v.findViewById(R.id.dialog_success_time)).setText(getString(R.string.show_time_result, time));
        ((TextView) v.findViewById(R.id.dialog_success_scores)).setText(getString(R.string.show_plus_and_result, scores));
        ((TextView) v.findViewById(R.id.dialog_success_millis)).setText(getString(R.string.show_plus_and_result, millis));

        return v;
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
}

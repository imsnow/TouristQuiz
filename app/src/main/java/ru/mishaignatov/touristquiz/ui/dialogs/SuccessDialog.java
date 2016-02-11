package ru.mishaignatov.touristquiz.ui.dialogs;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.R;

/**
 * Created by Mike on 11.02.2016.
 **/
public class SuccessDialog extends DialogFragment implements View.OnClickListener{

    private String time;
    private String scores;
    private String millis;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            time = savedInstanceState.getString("KEY_TIME");
            scores = savedInstanceState.getString("KEY_SCORES");
            millis = savedInstanceState.getString("KEY_MILLIS");
        }
        super.onCreate(savedInstanceState);
    }
    /*
    public SuccessDialog(int millis, int scores, float time) {
        super();
        this.millis = millis;
        this.scores = scores;
        this.time = time;
    }
    */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.dialog_success, null);
        v.findViewById(R.id.dialog_success_next).setOnClickListener(this);

        ((TextView) v.findViewById(R.id.dialog_success_time)).setText(time);
        ((TextView) v.findViewById(R.id.dialog_success_scores)).setText(scores);
        ((TextView) v.findViewById(R.id.dialog_success_millis)).setText(millis);

        return v;
    }

    @Override
    public void onClick(View v) {

    }
}

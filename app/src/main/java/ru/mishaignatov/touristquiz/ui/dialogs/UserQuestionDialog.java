package ru.mishaignatov.touristquiz.ui.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mishaignatov.touristquiz.R;

/**
 * Created by Leva on 16.01.2016.
 **/
public class UserQuestionDialog extends DialogFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.fragment_dialog_user_question, null);
        //v.findViewById(R.id.btnYes).setOnClickListener(this);
        //v.findViewById(R.id.btnNo).setOnClickListener(this);
        //v.findViewById(R.id.btnMaybe).setOnClickListener(this);
        return v;
    }
}

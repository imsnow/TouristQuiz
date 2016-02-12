package ru.mishaignatov.touristquiz.ui.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.ui.fragments.QuestionFragment;

/***
 * Created by Mike on 12.02.2016.
 */
public class FailDialog extends DialogFragment implements View.OnClickListener{

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.dialog_fail, null);
        v.findViewById(R.id.dialog_fail_next).setOnClickListener(this);
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

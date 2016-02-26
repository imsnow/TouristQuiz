package ru.mishaignatov.touristquiz.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mishaignatov.touristquiz.R;

/***
 * Created by Mike on 26.02.2016.
 */
public class LevelDoneDialog extends BaseDialogFragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_level_done, container, false);
        //v.findViewById(R.id.dialog_fail_next).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getTitleColor() {
        return R.color.success_title;
    }

    @Override
    public int getTitleString() {
        return R.string.dialog_level_done_title;
    }
}

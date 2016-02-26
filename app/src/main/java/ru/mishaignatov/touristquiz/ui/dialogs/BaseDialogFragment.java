package ru.mishaignatov.touristquiz.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/***
 * Created by Mike on 26.02.2016.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    // return color resource
    public abstract int getTitleColor();
    // return string title resource
    public abstract int getTitleString();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupTitle();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setupTitle(){
        TextView title = (TextView)getDialog().findViewById(android.R.id.title);
        title.setBackgroundResource(getTitleColor());
        title.setText(getString(getTitleString()));
    }
}

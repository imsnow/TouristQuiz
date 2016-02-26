package ru.mishaignatov.touristquiz.ui.dialogs;

import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/***
 * Created by Mike on 26.02.2016.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    // return color resource
    public abstract int getTitleColor();
    // return string title resource
    public abstract int getTitleString();

    @Override
    public void onStart() {
        super.onStart();
        setupTitle();
    }

    private void setupTitle(){
        TextView title = (TextView)getDialog().findViewById(android.R.id.title);
        title.setBackgroundResource(getTitleColor());
        title.setGravity(Gravity.CENTER);
        title.setText(getString(getTitleString()));
    }
}

package ru.mishaignatov.touristquiz.ui.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.ui.fragments.QuestionFragment;

/***
 * Created by Mike on 12.02.2016.
 */
public class FailDialog extends BaseDialogFragment implements View.OnClickListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addImageAndTextView(R.drawable.ic_miles, R.string.minus_millis, null);
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
        return R.drawable.dialog_title_red;
    }

    @Override
    public int getTitleString() {
        return R.string.dialog_fail_title;
    }
}

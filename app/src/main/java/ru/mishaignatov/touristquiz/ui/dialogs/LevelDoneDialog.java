package ru.mishaignatov.touristquiz.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.Utils;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.ui.MainActivity;
import ru.mishaignatov.touristquiz.ui.fragments.LevelListFragment;

/***
 * Created by Mike on 26.02.2016.
 */
public class LevelDoneDialog extends BaseDialogFragment implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TextView text = new TextView(getContext());
        text.setText(getString(R.string.level_text));
        text.setTextAppearance(getContext(), R.style.DialogTextViewBig);
        text.setPadding(0, 0, 0, Utils.dpToPx(8));
        text.setGravity(Gravity.CENTER);

        addView(text);
        addImageAndTextView(R.drawable.ic_miles, R.string.show_plus_and_result, "30");
        addNextButton(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getTag().equals("next")) {
            GameManager.getInstance(getContext()).getUser().addMiles(30);
            LevelListFragment frag = ((MainActivity)getActivity()).getLevelListFragment();
            if (frag != null && frag.isVisible())
                frag.update();
            dismiss();
        }
    }

    @Override
    public int getTitleColor() {
        return R.drawable.dialog_title_green;
    }

    @Override
    public int getTitleString() {
        return R.string.dialog_level_done_title;
    }
}

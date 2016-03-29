package ru.mishaignatov.touristquiz.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.Utils;
import ru.mishaignatov.touristquiz.database.Level;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.ui.fragments.LevelListFragment;

/***
 * Created by Mike on 24.02.2016.
 */
public class BuyTicketDialog extends BaseDialogFragment implements View.OnClickListener {

    private Level mLevel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        int id = args.getInt("KEY_ID");
        mLevel = App.getDbHelper().getLevelDao().getLevelById(id);

        final TextView text = new TextView(getContext());
        text.setGravity(Gravity.CENTER);
        text.setPadding(0,0,0, Utils.dpToPx(16));
        text.setTextAppearance(getContext(), R.style.DialogTextView);
        text.setTextSize(Utils.spToPx(10));
        text.setText(getString(R.string.dialog_buy_ticket_text, mLevel.cost));

        addView(text);

        addCancelAndOkButtons(R.string.buy, this);
    }

    @Override
    public void onClick(View v) {
        String tag = (String)v.getTag();
        switch (tag){
            case "cancel":
                dismiss();
                break;
            case "send":
                sendBuyResult();
                break;
        }
    }

    private void sendBuyResult(){
        LevelListFragment frag = (LevelListFragment)getTargetFragment();
        frag.buyLevel(mLevel.id);
        dismiss();
    }

    @Override
    public int getTitleColor() {
        return R.drawable.dialog_title_green;
    }

    @Override
    public int getTitleString() {
        return R.string.open_level;
    }
}

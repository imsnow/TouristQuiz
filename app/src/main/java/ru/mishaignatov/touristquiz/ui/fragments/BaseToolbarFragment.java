package ru.mishaignatov.touristquiz.ui.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.ui.dialogs.AddMillisDialog;

/***
 * Created by Leva on 03.04.2016.
 */
public class BaseToolbarFragment extends Fragment {

    private TextView mHeaderTitle;
    private TextView mScoresText;
    private TextView mMilesText;

    protected void initHeader(View parent){

        mHeaderTitle = (TextView)parent.findViewById(R.id.header_title);
        mScoresText  = (TextView)parent.findViewById(R.id.header_scores);
        mMilesText  = (TextView)parent.findViewById(R.id.header_miles);

        ImageView mHomeButton = (ImageView) parent.findViewById(R.id.button_back);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ImageView plusMillisButton = (ImageView)parent.findViewById(R.id.button_plus);
        plusMillisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMillisDialog dialog = new AddMillisDialog();
                dialog.show(getChildFragmentManager(), "add_millis");
            }
        });
    }

    protected void updateHeader(String title){
        if (mHeaderTitle != null && mScoresText != null && mMilesText != null) {
            mHeaderTitle.setText(title);
            GameManager gameManager = GameManager.getInstance(getActivity());
            mScoresText.setText(String.valueOf(gameManager.getUser().getScores()));
            mMilesText.setText(String.valueOf(gameManager.getUser().getMillis()));
        }
    }

}

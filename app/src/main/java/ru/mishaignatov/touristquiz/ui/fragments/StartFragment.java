package ru.mishaignatov.touristquiz.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.MainActivity;
import ru.mishaignatov.touristquiz.ui.dialogs.UserQuestionDialog;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartFragment extends Fragment implements View.OnClickListener {

    private ActivityInterface activityInterface;

    public StartFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity)
            activityInterface = (MainActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_start, container, false);

        v.findViewById(R.id.button_start).setOnClickListener(this);
        v.findViewById(R.id.button_my_quiz).setOnClickListener(this);
        v.findViewById(R.id.button_leaderboard).setOnClickListener(this);
        v.findViewById(R.id.button_tips).setOnClickListener(this);
        v.findViewById(R.id.button_settings).setOnClickListener(this);
        TextView ver = (TextView) v.findViewById(R.id.version_text);
        ver.setText(App.getVersion());
        //ver.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button_start:
                activityInterface.onLevelListFragment();
                break;
            case R.id.button_my_quiz:
                UserQuestionDialog dialog = new UserQuestionDialog();
                activityInterface.showDialog(dialog, "user_question");
                break;
            case R.id.button_leaderboard:
                activityInterface.onLeaderBoardFragment();
                break;
            case R.id.button_tips:
                LoginManager.getInstance().logOut();
                activityInterface.onTipsFragment();
                break;
            case R.id.button_settings:
                activityInterface.onSettingsFragment();
                break;
            case R.id.version_text:
                Toast.makeText(getActivity(), GameManager.getInstance(getContext()).getUser().toString(), Toast.LENGTH_LONG).show();
                break;
        }
    }

}

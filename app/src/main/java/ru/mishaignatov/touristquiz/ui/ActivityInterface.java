package ru.mishaignatov.touristquiz.ui;

import android.support.v4.app.DialogFragment;

import ru.mishaignatov.touristquiz.database.Achievement;
import ru.mishaignatov.touristquiz.game.Bonus;

/**
 * Created by Leva on 15.01.2016.
 **/
public interface ActivityInterface {

    void onShowBonus(Bonus bonus);

    void onShowAchievement(Achievement achievement);

    void onShowHiddenTip(String s);
    void onShowTip(String s);

    void onLoadFragment();
    void onStartFragment();
    void onLevelListFragment();
    void onLeaderBoardFragment();
    void onTipsFragment();
    void onAchievementFragment();
    void onSettingsFragment();

    void showDialog(DialogFragment dialog, String tag);
}



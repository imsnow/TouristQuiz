package ru.mishaignatov.touristquiz.ui;

import android.support.v4.app.DialogFragment;

/**
 * Created by Leva on 15.01.2016.
 **/
public interface ActivityInterface {

    void onShowAchievement();

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



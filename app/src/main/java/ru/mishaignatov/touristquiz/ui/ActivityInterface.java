package ru.mishaignatov.touristquiz.ui;

import android.support.v4.app.DialogFragment;

/**
 * Created by Leva on 15.01.2016.
 **/
public interface ActivityInterface {

    void showHeader();
    void onUpdateHeader(String country);

    void onShowHiddenTip(String s);
    void onShowTip(String s);

    void onLoadFragment();
    void onStartFragment();
    void onCountryListFragment(String country);

    void showFragmentDialog(DialogFragment dialog, String tag);
}



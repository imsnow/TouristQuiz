package ru.mishaignatov.touristquiz.ui;

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
}



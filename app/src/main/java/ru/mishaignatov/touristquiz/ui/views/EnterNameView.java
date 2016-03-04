package ru.mishaignatov.touristquiz.ui.views;

/**
 * Created by Leva on 23.02.2016.
 **/
public interface EnterNameView {

    void onWaitingUser();
    void onCheckingName();
    void onBusyName();
    void onAcceptingName();
    void onNameOk();
    void closeDialog();
}

package ru.mishaignatov.touristquiz.presenters;

import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;

/**
 * Created by Leva on 23.02.2016.
 **/
public interface EnterNamePresenter extends BasePresenter{

    void checkName(String name);
    void sendName();

    FacebookCallback<LoginResult> getCallback();
}

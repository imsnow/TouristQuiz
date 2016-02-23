package ru.mishaignatov.touristquiz.presenters;

/**
 * Created by Leva on 23.02.2016.
 **/
public interface EnterNamePresenter extends BasePresenter{

    void checkName(String name);
    void sendName();
}

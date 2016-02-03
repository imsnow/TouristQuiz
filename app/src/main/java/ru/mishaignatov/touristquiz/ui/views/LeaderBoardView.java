package ru.mishaignatov.touristquiz.ui.views;

import java.util.List;

import ru.mishaignatov.touristquiz.game.LeaderBoardItem;

/**
 * Created by Mike on 03.02.2016.
 **/
public interface LeaderBoardView {

    void showProgressBar();
    void hideProgressBar();
    void onUpdateTable(List<LeaderBoardItem> list);

    void showError();
}

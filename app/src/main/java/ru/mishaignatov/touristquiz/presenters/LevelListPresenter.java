package ru.mishaignatov.touristquiz.presenters;

import java.util.List;

import ru.mishaignatov.touristquiz.database.Level;

/**
 * Created by Mike on 16.02.2016.
 **/
public interface LevelListPresenter extends BasePresenter {

    List<Level> getLevelList();
    Level getLevel(int position);
    void onListItemClick(int position);
    void onBuyLevel(int position);
    void updateLevels();
}

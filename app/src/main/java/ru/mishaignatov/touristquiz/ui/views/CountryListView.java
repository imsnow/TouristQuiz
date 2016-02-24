package ru.mishaignatov.touristquiz.ui.views;

/***
 * Created by Mike on 16.02.2016.
 */
public interface CountryListView {

    void showClosedCountry(int position);
    void showNotEnoughMillis();
    void showDialogLevelFinished();
    void startLevel(int position);
    void update();
}

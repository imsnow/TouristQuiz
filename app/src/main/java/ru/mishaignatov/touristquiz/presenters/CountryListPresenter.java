package ru.mishaignatov.touristquiz.presenters;

import java.util.List;

import ru.mishaignatov.touristquiz.orm.Country;

/**
 * Created by Mike on 16.02.2016.
 **/
public interface CountryListPresenter extends BasePresenter {

    List<Country> getCountryList();
    Country getCountry(int position);
    void onListItemClick(int position);
    void updateCountries();
}

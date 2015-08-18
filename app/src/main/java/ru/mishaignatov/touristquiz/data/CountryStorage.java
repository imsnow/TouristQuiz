package ru.mishaignatov.touristquiz.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ignatov on 13.08.2015.
 * Singletone
 */
public class CountryStorage {

    private static CountryStorage storage;
    private List<Country> list;

    private CountryStorage(){
        list = new ArrayList<>();
    }

    public static CountryStorage getStorage(){
        if(storage == null)
            storage = new CountryStorage();
        return storage;
    }

    public void addCountry(Country country){
        list.add(country);
    }

    public List<Country> getCountryList(){
        return list;
    }
}

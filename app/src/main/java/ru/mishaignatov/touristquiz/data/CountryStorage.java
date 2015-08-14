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
/*
        // test only
        Country item = new Country("Египет", 15);
        list.add(item);
        item = new Country("Франция", 12);
        list.add(item);
        item = new Country("Россия", 25);
        list.add(item); */
    }

    public static CountryStorage getStorage(){
        if(storage == null)
            storage = new CountryStorage();
        return storage;
    }

    public void addCountry(Country country){
        list.add(country);
    }

    public Country getCountry(int position){
        if(list.size() >= position )
            return list.get(position);
        return null;
    }

    public List<Country> getCountryList(){
        return list;
    }
}

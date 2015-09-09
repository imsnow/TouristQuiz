package ru.mishaignatov.touristquiz;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ignatov on 09.09.2015.
 */
public class GameManager {

    List<String> listCountries = new ArrayList<>();

    private GameManager(Context context){
        String[] array = context.getResources().getStringArray(R.array.countries);
        listCountries = Arrays.asList(array);
    }
}

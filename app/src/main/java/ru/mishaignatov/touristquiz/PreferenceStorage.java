package ru.mishaignatov.touristquiz;

import android.content.SharedPreferences;

/**
 * Created by Leva on 13.12.2015.
 */
public class PreferenceStorage {

    private static PreferenceStorage INSTANCE = new PreferenceStorage();

    private static SharedPreferences preferences;

    public static PreferenceStorage getInstance(){
        return INSTANCE;
    }
    /*
    private void loadPreference(Context context){
        preferences = context.getSharedPreferences("quiz", Context.MODE_PRIVATE);
        total_size_file = prefs.getInt(KEY_TOTAL, 0);
        answered_size   = prefs.getInt(KEY_ANSWERED, 0);
        score           = prefs.getInt(KEY_SCORE,    0);
    }
    */
}

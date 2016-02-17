package ru.mishaignatov.touristquiz.game;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Leva on 13.12.2015.
 *
 */
public class PreferenceStorage {

    private static final String PREFS_NAME       = "user";

    private static final String KEY_DISPLAY_NAME = "display_name";
    private static final String KEY_TOKEN        = "token";
    //private static final String KEY_ANSWERED = "answered";
    private static final String KEY_MILES        = "miles";
    private static final String KEY_SCORE        = "score";
    private static final String KEY_REGISTER     = "register";

    private static PreferenceStorage INSTANCE;

    private SharedPreferences preferences;

    private PreferenceStorage(Context context){
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static PreferenceStorage getInstance(Context context){
        if(INSTANCE == null) INSTANCE = new PreferenceStorage(context);
        return INSTANCE;
    }

    public void saveUser(User user){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_DISPLAY_NAME, user.getDisplayName());
        editor.putBoolean(KEY_REGISTER, user.isRegistered());
        editor.putInt(KEY_MILES, user.getMillis());
        editor.putInt(KEY_SCORE, user.getScores());
        editor.putString(KEY_TOKEN, user.getToken());
        editor.apply();
    }

    public void loadUser(User user){
        user.setIsRegistration(preferences.getBoolean(KEY_REGISTER, false));
        user.setMillis(preferences.getInt(KEY_MILES, 100));
        user.setScores(preferences.getInt(KEY_SCORE, 0));
        user.setToken(preferences.getString(KEY_TOKEN, ""));
        user.setDisplayName(preferences.getString(KEY_DISPLAY_NAME, ""));
    }
}

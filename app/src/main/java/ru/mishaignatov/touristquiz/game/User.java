package ru.mishaignatov.touristquiz.game;

import android.content.Context;

/**
 * Created by Leva on 26.12.2015.
 *
 */
public class User {

    private String token;

    private String email;
    private String imei;   // _id
    private String deviceName;
    private String androidApi;

    private int miles;               // Зработанные мили пользователя
    private int scores;               // Заработанные очки пользователя

    private Context mContext;

    private boolean isRegistered = false;

    private static User instance;

    protected static User getUser(Context context){
        if(instance == null) instance = new User(context);
        return instance;
    }

    private User(Context context){

        mContext = context;

        email      = UserUtils.getGoogleEmail(context);
        imei       = UserUtils.getIMEI(context);
        deviceName = UserUtils.getDeviceName();
        androidApi = UserUtils.getAndroidAPI();
        // load from preference
        PreferenceStorage.getInstance(context).loadUser(this);
    }

    protected void saveUser(){
        PreferenceStorage.getInstance(mContext).saveUser(this);
    }

    protected void addResult(int progressScore, int progressMiles) {
        scores += progressScore;
        miles += progressMiles;
    }

    // Getters
    public String getToken()      { return token;      }
    public String getEmail()      { return email;      }
    public String getImei()       { return imei;       }
    public String getDevice()     { return deviceName; }
    public String getAndroidApi() { return androidApi; }

    public int getMiles()           {  return miles; }
    public int getScores()           {  return scores; }

    public void setToken(String token){ this.token = token; }
    public void setMiles(int miles)   { this.miles = miles; }
    public void setScores(int scores) { this.scores = scores; }

    public void setIsRegistration(boolean is) { isRegistered = is; }
    public boolean isRegistered() { return isRegistered; }
    public void confirmRegistration()   { isRegistered = true;}

    @Override
    public String toString() {
        return "User{" +
                "androidApi='" + androidApi + '\'' +
                ", token='" + token + '\'' +
                ", email='" + email + '\'' +
                ", imei='" + imei + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", miles=" + miles +
                ", scores=" + scores +
                ", mContext=" + mContext +
                ", isRegistered=" + isRegistered +
                '}';
    }
}

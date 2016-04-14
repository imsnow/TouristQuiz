package ru.mishaignatov.touristquiz.game;

import android.content.Context;

import com.facebook.AccessToken;

/**
 * Created by Leva on 26.12.2015.
 *
 */
public class User {

    private String token;
    private AccessToken fbAccessToken; // facebook access token

    private String displayName;
    private String email;
    private String imei;   // _id
    private String deviceName;
    private String androidApi;

    private int millis;               // Зработанные мили пользователя
    private int scores;               // Заработанные очки пользователя

    private int countLevelsDone = 0;  // Количество пройденных уровней

    private Context mContext;

    private boolean isRegistered = false;

    private static User instance;

    public enum TypeName { ENTERED, FACEBOOK }

    protected static User getUser(Context context){
        if(instance == null) instance = new User(context);
        return instance;
    }

    private User(Context context){

        mContext = context;

        email      = UserUtils.getGoogleEmail(context);
        imei       = UserUtils.getIMEI(context);
        deviceName = UserUtils.getDeviceName() != null ? UserUtils.getDeviceName() : "null";
        androidApi = UserUtils.getAndroidAPI();
        // load from preference
        PreferenceStorage.getInstance(context).loadUser(this);
    }

    protected void saveUser(){
        PreferenceStorage.getInstance(mContext).saveUser(this);
    }

    public void addResult(int progressScore, int progressMiles) {
        scores += progressScore;
        millis += progressMiles;
    }

    public void removeMillis(int progressMillis){
        if(millis != 0) millis -= progressMillis;
    }

    public void buyCountry(int cost){
        millis -= cost;
    }

    // Getters
    public String getToken()      { return token;      }
    public String getDisplayName(){ return displayName;}
    public String getEmail()      { return email;      }
    public String getImei()       { return imei;       }
    public String getDevice()     { return deviceName; }
    public String getAndroidApi() { return androidApi; }

    public int getMillis()           {  return millis; }
    public int getScores()           {  return scores; }
    public int getCountLevelsDone()  {  return countLevelsDone; }

    public void setDisplayName(String name) { displayName = name; }
    public void setToken(String token){ this.token = token; }
    public void setMillis(int millis)   { this.millis = millis; }
    public void setScores(int scores) { this.scores = scores; }
    public void setCountLevelsDone(int count) { countLevelsDone = count; }

    public void setIsRegistration(boolean is) { isRegistered = is; }
    public boolean isRegistered() { return isRegistered; }
    public boolean isEnterName()  { return (displayName.length() != 0); }
    public void confirmRegistration()   { isRegistered = true;}

    public AccessToken getFbAccessToken() {
        return fbAccessToken;
    }

    public void setFbAccessToken(AccessToken fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "androidApi='" + androidApi + '\'' +
                ", token='" + token + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", imei='" + imei + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", millis=" + millis +
                ", scores=" + scores +
                ", mContext=" + mContext +
                ", isRegistered=" + isRegistered +
                '}';
    }
}

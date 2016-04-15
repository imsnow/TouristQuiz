package ru.mishaignatov.touristquiz.game;

import android.content.Context;

import com.facebook.AccessToken;

import ru.mishaignatov.touristquiz.database.Achievement;
import ru.mishaignatov.touristquiz.database.AchievementDao;

/**
 * Created by Leva on 26.12.2015.
 *
 */
public class User {

    public interface ResultInterface {
        void onFiveAnsweredTrue();
        void onTenAnsweredTrue();
        void onShowLevelsAchievement(Achievement achievement);
    }

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
    private int countRightQuestionsAnswered = 0; // кол-во правильно отвеченных вопросов подряд

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

    public void addMiles(int miles) {
        millis += miles;
    }

    // invoke when user answer true
    public void addResult(int progressScore, int progressMiles, boolean isFirstAttempt, ResultInterface callback) {

        scores += progressScore;
        millis += progressMiles;

        if (isFirstAttempt) {
            // the first time
            countRightQuestionsAnswered++;

            if (countRightQuestionsAnswered == 5) {
                callback.onFiveAnsweredTrue();
            }

            if (countRightQuestionsAnswered == 10) {
                callback.onTenAnsweredTrue();
            }
        }
        else
            if (countRightQuestionsAnswered != 0) {
                countRightQuestionsAnswered = 0;
                // забираем оба достижения
                AchievementDao dao = App.getDbHelper().getAchievementDao();
                Achievement five = dao.getAchievementById(0);
                Achievement ten  = dao.getAchievementById(1);
                dao.setAchieved(five, false);
                dao.setAchieved(ten, false);
            }

    }

    public void addLevelDone(ResultInterface callback) {

        countLevelsDone++;

        switch (countLevelsDone) {
            case 1:
                callback.onShowLevelsAchievement(App.getDbHelper().getAchievementDao().getAchievementById(2));
                break;
            case 5:
                callback.onShowLevelsAchievement(App.getDbHelper().getAchievementDao().getAchievementById(3));
                break;
            case 10:
                callback.onShowLevelsAchievement(App.getDbHelper().getAchievementDao().getAchievementById(4));
                break;
            case 20:
                callback.onShowLevelsAchievement(App.getDbHelper().getAchievementDao().getAchievementById(5));
                break;
            case 30:
                callback.onShowLevelsAchievement(App.getDbHelper().getAchievementDao().getAchievementById(6));
                break;
        }
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
    public int getCountRightQuestionsAnswered() { return countRightQuestionsAnswered; }

    public void setDisplayName(String name) { displayName = name; }
    public void setToken(String token){ this.token = token; }
    public void setMillis(int millis)   { this.millis = millis; }
    public void setScores(int scores) { this.scores = scores; }
    public void setCountLevelsDone(int count) { countLevelsDone = count; }
    public void setCountRightQuestionsAnswered(int count) { countRightQuestionsAnswered = count; }

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

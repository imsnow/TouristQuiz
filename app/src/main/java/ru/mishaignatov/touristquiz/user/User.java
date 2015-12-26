package ru.mishaignatov.touristquiz.user;

import android.content.Context;

/**
 * Created by Leva on 26.12.2015.
 *
 */
public class User {

    private String email;
    private String imei;   // _id
    private String deviceName;
    private String androidApi;

    private boolean isRegistered = false;

    private static User instance;

    public static User getUser(Context context){
        if(instance == null) instance = new User(context);
        return instance;
    }

    private User(Context context){
        email      = UserUtils.getGoogleEmail(context);
        imei       = UserUtils.getIMEI(context);
        deviceName = UserUtils.getDeviceName();
        androidApi = UserUtils.getAndroidAPI();
    }

    // Getters
    public String getEmail()      { return email;      }
    public String getImei()       { return imei;       }
    public String getDevice()     { return deviceName; }
    public String getAndroidApi() { return androidApi; }

    public boolean isRegistered() { return isRegistered; }
    public void confirmRegistration()   { isRegistered = true;}


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", imei='" + imei + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", androidApi='" + androidApi + '\'' +
                '}';
    }
}

package ru.mishaignatov.touristquiz;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by Leva on 26.12.2015.
 *
 */
public class User {

    private String email;
    private String imei;   // _id
    private String deviceName;
    private String androidApi;
    private static User instance;

    public static User getUser(Context context){
        if(instance == null) instance = new User(context);
        return instance;
    }

    private User(Context context){
        email = getGoogleEmail(context);
        imei  = getIMEI(context);
        deviceName = getDeviceName();
        androidApi = getAndroidAPI();
    }
    /*
    // Getter and setter
    public String getEmail() {
        return email;
    }
    */
    // get account email for google service
    private String getGoogleEmail(Context context){

        final String TYPE = "com.google";

        String e = null;

        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context).getAccounts();
        for(Account account : accounts)
            if(emailPattern.matcher(account.name).matches()) {
                if(account.type.equals(TYPE)) {
                    e = account.name;
                }
            }
        return e;
    }

    private String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public String getAndroidAPI(){
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

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

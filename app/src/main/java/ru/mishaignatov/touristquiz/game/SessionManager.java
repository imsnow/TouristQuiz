package ru.mishaignatov.touristquiz.game;

import android.app.Activity;

import ru.mishaignatov.touristquiz.server.ApiHelper;

/***
 * Created by Leva on 15.01.2016.
 */
public class SessionManager {

    private long start;
    private Activity mActivity;

    public SessionManager(Activity activity){
        mActivity = activity;
    }

    public void start(){
        start = System.currentTimeMillis();
    }

    public void end(){
        long end = System.currentTimeMillis();
        long session = end - start;
        ApiHelper.getHelper(mActivity).userSession(GameManager.getInstance(mActivity).getUser(), session, null, null);
    }
}

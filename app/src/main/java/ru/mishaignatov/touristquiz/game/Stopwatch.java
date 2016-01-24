package ru.mishaignatov.touristquiz.game;

import android.util.Log;

/**
 * Created by Leva on 23.01.2016.
 **/
public class Stopwatch {

    // question time in millis
    private static final int MAX = 30*1000;

    private long mStartTime;
    private long mStopTime;
    private boolean isStop = false;

    public interface Callback {
        void onFinished();
    }

    private Callback callback;

    public Stopwatch(Callback callback){
        this.callback = callback;
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {

            while(!isStop){
                long currentTime = System.currentTimeMillis();
                if( (mStopTime = currentTime - mStartTime) > MAX) break;
            }
            callback.onFinished();
            Log.d("TAG", "onFinished");
        }
    };

    public void start(){
        Log.d("TAG", "onStart");
        isStop = false;
        mStartTime = System.currentTimeMillis();
        Thread thread = new Thread(r);
        thread.start();
    }

    public void stop(){
        isStop = true;
        Log.d("TAG", "onStop. count = " + mStopTime);
    }
}

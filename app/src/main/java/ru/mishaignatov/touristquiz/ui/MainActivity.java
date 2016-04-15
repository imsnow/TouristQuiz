package ru.mishaignatov.touristquiz.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.database.Achievement;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.SessionManager;
import ru.mishaignatov.touristquiz.ui.fragments.AchievementsFragment;
import ru.mishaignatov.touristquiz.ui.fragments.LeaderBoardFragment;
import ru.mishaignatov.touristquiz.ui.fragments.LevelListFragment;
import ru.mishaignatov.touristquiz.ui.fragments.LoadFragment;
import ru.mishaignatov.touristquiz.ui.fragments.SettingsFragment;
import ru.mishaignatov.touristquiz.ui.fragments.StartFragment;
import ru.mishaignatov.touristquiz.ui.fragments.TipsFragment;

public class MainActivity extends AppCompatActivity implements ActivityInterface, Animation.AnimationListener {

    // achievement views
    private LinearLayout mAchievementLayout;
    private ImageView mAchievementIcon;
    private TextView mAchievemnetText;

    // tips views
    private LinearLayout mTipsLayout;
    private TextView mTipsText;

    private Handler mHandler = new Handler();

    private GameManager mGameManager;
    private SessionManager mSessionManager;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create GameManager
        mGameManager = GameManager.getInstance(this);
        mGameManager.makeSnapshot();

        mSessionManager = new SessionManager(this);

        mTipsLayout = (LinearLayout)findViewById(R.id.tips_view);
        mTipsText   = (TextView)findViewById(R.id.tips_text);

        mAchievementLayout = (LinearLayout)findViewById(R.id.achiev_layout);
        mAchievementIcon = (ImageView) findViewById(R.id.achiev_image);
        mAchievemnetText = (TextView) findViewById(R.id.achiev_text);

        mFragmentManager = getSupportFragmentManager();

        //mGameManager.startGame();
        if( mGameManager.getUser().isRegistered() && mGameManager.getUser().getToken().length() > 0)
            onStartFragment();
        else
            onLoadFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSessionManager.start();
        // facebook event logger
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameManager.makeSnapshot();
        mSessionManager.end();
        AppEventsLogger.deactivateApp(this);
    }

    public void replaceFragment(final Fragment frag) {
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment, frag).commit();
    }

    public void addFragment(final Fragment frag, String tag){
        mFragmentManager.beginTransaction().add(R.id.fragment, frag, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Fragment levels = mFragmentManager.findFragmentByTag("LevelListFragment");
        if (levels instanceof LevelListFragment && levels.isVisible()) {
            // update methods
            Log.d("TAG", "update");
            ((LevelListFragment) levels).update();
        }
    }

    @Override
    public void onShowAchievement(Achievement achievement) {
        showAchievement(achievement);
    }

    @Override
    public void onShowHiddenTip(String s) {
        onShowTip(s);
        mHandler.postDelayed(mHideTipRunnable, 3000);
    }

    @Override
    public void onShowTip(String s){
        mTipsText.setText(s);
        showTip();
    }


    private void showAchievement(Achievement achievement) {
        mAchievementLayout.bringToFront();

        mAchievementIcon.setImageResource(achievement.draw_resource);
        mAchievemnetText.setText(achievement.content);

        App.getDbHelper().getAchievementDao().setAchieved(achievement, true);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.achievement);
        anim.setStartOffset(3000);
        //anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.setAnimationListener(this);
        mAchievementLayout.startAnimation(anim);
        mAchievementLayout.setVisibility(View.VISIBLE);
    }

    private Runnable mHideTipRunnable = new Runnable() {
        @Override
        public void run() {
            hideTip();
        }
    };

    private void showTip(){
        mTipsLayout.bringToFront();
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.bottom_in);
        anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.setDuration(300L);
        mTipsLayout.startAnimation(anim);
        mTipsLayout.setVisibility(View.VISIBLE);
    }

    private void hideTip(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.bottom_out);
        anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.setDuration(300L);
        mTipsLayout.startAnimation(anim);
        mTipsLayout.setVisibility(View.GONE);
    }

    @Override
    public void onLoadFragment() {
        replaceFragment(new LoadFragment());
    }

    @Override
    public void onStartFragment() {
        replaceFragment(new StartFragment());
    }

    @Override
    public void onLevelListFragment() {
        addFragment(new LevelListFragment(), "LevelListFragment");
    }

    @Override
    public void onLeaderBoardFragment() {
        addFragment(new LeaderBoardFragment(), "LeaderBoard");
    }

    @Override
    public void onTipsFragment() {
        addFragment(new TipsFragment(), "Tips");
    }

    @Override
    public void onAchievementFragment() {
        addFragment(new AchievementsFragment(), "Achievement");
    }

    @Override
    public void onSettingsFragment() {
        addFragment(new SettingsFragment(), "Settings");
        //showHeader();
        //onUpdateHeader(getString(R.string.action_settings));
    }

    @Override
    public void showDialog(DialogFragment dialog, String tag) {
        if(mFragmentManager != null)
            dialog.show(mFragmentManager, tag);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mAchievementLayout.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

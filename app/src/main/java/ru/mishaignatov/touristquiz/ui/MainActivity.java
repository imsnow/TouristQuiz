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
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.SessionManager;
import ru.mishaignatov.touristquiz.ui.dialogs.AddMillisDialog;
import ru.mishaignatov.touristquiz.ui.fragments.CountryListFragment;
import ru.mishaignatov.touristquiz.ui.fragments.LeaderBoardFragment;
import ru.mishaignatov.touristquiz.ui.fragments.LoadFragment;
import ru.mishaignatov.touristquiz.ui.fragments.SettingsFragment;
import ru.mishaignatov.touristquiz.ui.fragments.StartFragment;
import ru.mishaignatov.touristquiz.ui.fragments.TipsFragment;

public class MainActivity extends AppCompatActivity implements ActivityInterface {

    // header views
    private LinearLayout mHeaderLayout;
    private ImageView mHomeButton;
    private TextView mHeaderTitle;
    private TextView mScoresText;
    private TextView mMilesText;

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

        mHeaderLayout = (LinearLayout)findViewById(R.id.header_layout);
        mHeaderTitle = (TextView)findViewById(R.id.header_title);
        mScoresText  = (TextView)findViewById(R.id.header_scores);
        mMilesText  = (TextView)findViewById(R.id.header_miles);

        mHomeButton = (ImageView)findViewById(R.id.button_back);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView plusMillisButton = (ImageView)findViewById(R.id.button_plus);
        plusMillisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMillisDialog dialog = new AddMillisDialog();
                dialog.show(mFragmentManager, "add_millis");
            }
        });

        mTipsLayout = (LinearLayout)findViewById(R.id.tips_view);
        mTipsText   = (TextView)findViewById(R.id.tips_text);

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

        if(mFragmentManager.getBackStackEntryCount() == 0) // Start Fragment
            mHeaderLayout.setVisibility(View.GONE);

        Fragment frag = mFragmentManager.findFragmentByTag("CountryListFragment");
        if(frag instanceof CountryListFragment && frag.isVisible()) {
            // update methods
            Log.d("TAG", "update");
            ((CountryListFragment)frag).update();

        }
    }

    @Override
    public void showHeader() {
        mHeaderLayout.setVisibility(View.VISIBLE);
        mHomeButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUpdateHeader(String title) {
        mHeaderTitle.setText(title);
        mScoresText.setText(String.valueOf(mGameManager.getUser().getScores()));
        mMilesText.setText(String.valueOf(mGameManager.getUser().getMillis()));
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
    public void onCountryListFragment() {
        addFragment(new CountryListFragment(), "CountryListFragment");
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
    public void onSettingsFragment() {
        addFragment(new SettingsFragment(), "Settings");
        showHeader();
        onUpdateHeader(getString(R.string.action_settings));
    }

    @Override
    public void showFragmentDialog(DialogFragment dialog, String tag) {
        dialog.show(mFragmentManager, tag);
    }
}

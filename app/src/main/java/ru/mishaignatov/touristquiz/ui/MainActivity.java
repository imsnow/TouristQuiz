package ru.mishaignatov.touristquiz.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.HeaderInterface;
import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.User;

public class MainActivity extends AppCompatActivity implements HeaderInterface, TipsInterface {

    // header views
    private LinearLayout mHeaderLayout;
    private ImageView mHomeButton;
    private TextView mCountryText;
    private TextView mScoresText;
    private TextView mMilesText;

    // tips views
    private LinearLayout mTipsLayout;
    private TextView mTipsText;

    private Handler mHandler = new Handler();

    private GameManager gameManager;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create GameManager
        gameManager = GameManager.getInstance(this);
        gameManager.makeSnapshot();

        mHeaderLayout = (LinearLayout)findViewById(R.id.header_layout);
        mCountryText = (TextView)findViewById(R.id.header_country);
        mScoresText  = (TextView)findViewById(R.id.header_scores);
        mMilesText  = (TextView)findViewById(R.id.header_miles);

        mHomeButton = (ImageView)findViewById(R.id.button_back);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTipsLayout = (LinearLayout)findViewById(R.id.tips_view);
        mTipsText   = (TextView)findViewById(R.id.tips_text);

        fragmentManager = getSupportFragmentManager();

        User user = gameManager.getUser();
        if(!user.isRegistered())
            replaceFragment(new LoadFragment());   // регистрируем полльзователя
        else
            replaceFragment(new StartFragment());  // запускаем
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameManager.makeSnapshot();
    }

    public GameManager getGameManager(){
        return gameManager;
    }

    public void replaceFragment(final Fragment frag) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, frag).commit();
    }

    public void addFragment(final Fragment frag, String tag){
        fragmentManager.beginTransaction().add(R.id.fragment, frag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fragmentManager.getBackStackEntryCount() == 0)
            mHomeButton.setVisibility(View.INVISIBLE);

        onShowHiddenTip(GameManager.getInstance(this).getStatusTip());
    }

    @Override
    public void showHeader() {
        mHeaderLayout.setVisibility(View.VISIBLE);
        mHomeButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUpdateHeader(String country) {
        mCountryText.setText(country);
        mScoresText.setText(String.valueOf(gameManager.getUser().getScores()));
        mMilesText.setText(String.valueOf(gameManager.getUser().getMiles()));
    }

    @Override
    public void onShowHiddenTip(String s) {
        onShowTip(s);
        mHandler.postDelayed(mHideTipRunnable, 4000);
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
}

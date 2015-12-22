package ru.mishaignatov.touristquiz.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.GameManager;
import ru.mishaignatov.touristquiz.HeaderInterface;
import ru.mishaignatov.touristquiz.R;

public class MainActivity extends AppCompatActivity implements HeaderInterface {

    // header views
    private ImageView mHomeButton;
    private TextView mCountryText;
    private TextView mScoresText;
    private TextView mMilesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCountryText = (TextView)findViewById(R.id.header_country);
        mScoresText  = (TextView)findViewById(R.id.header_scores);
        mMilesText  = (TextView)findViewById(R.id.header_miles);

        mHomeButton = (ImageView)findViewById(R.id.button_back);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHomePressed();
            }
        });

        changeFragment(new LoadFragment());
    }

    public void changeFragment(final Fragment frag){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, frag).commit();
    }

    @Override
    public void onHomePressed() {
        Log.d("TAG", "onHomePressed = " + getSupportFragmentManager().getFragments().size());
    }

    @Override
    public void onUpdateHeader(String country) {
        mCountryText.setText(country);
        mScoresText.setText(String.valueOf(GameManager.getInstance(this).getScore()));
        mMilesText.setText(String.valueOf(GameManager.getInstance(this).getMiles()));
    }
}

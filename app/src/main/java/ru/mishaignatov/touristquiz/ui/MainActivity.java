package ru.mishaignatov.touristquiz.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.HeaderInterface;
import ru.mishaignatov.touristquiz.R;

public class MainActivity extends AppCompatActivity implements HeaderInterface {

    // header views
    private TextView mCountryText;
    private TextView mScoresText;
    private TextView mMilesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCountryText = (TextView)findViewById(R.id.header_country);
        mScoresText  = (TextView)findViewById(R.id.header_scores);
        mScoresText  = (TextView)findViewById(R.id.header_miles);

        changeFragment(new LoadFragment());
    }


    public void changeFragment(final Fragment frag){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, frag).commit();
    }

    @Override
    public void onChange(String country, int score, int miles) {
        mCountryText.setText(country);
        mScoresText.setText(String.valueOf(score));
        mMilesText.setText(String.valueOf(score));
    }
}

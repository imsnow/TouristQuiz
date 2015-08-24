package ru.mishaignatov.touristquiz.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.mishaignatov.touristquiz.R;

/**
 * Created by Ignatov on 24.08.2015.
 * Temporary class
 */
public class ActivityListFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new CountryListFragment()).commit();
    }
}

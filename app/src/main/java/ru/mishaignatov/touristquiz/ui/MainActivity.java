package ru.mishaignatov.touristquiz.ui;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ru.mishaignatov.touristquiz.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, new MainActivityFragment()).commit();
        //changeFragment(new MainActivityFragment());
    }

/*
    public void changeFragment(final Fragment frag){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, frag).commit();
    }
    */
}

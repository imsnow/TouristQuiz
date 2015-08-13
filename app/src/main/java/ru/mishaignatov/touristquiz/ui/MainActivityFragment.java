package ru.mishaignatov.touristquiz.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import ru.mishaignatov.touristquiz.App;
import ru.mishaignatov.touristquiz.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private TextView stat;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        Button start = (Button)v.findViewById(R.id.button_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity().getApplicationContext(), QuizActivity.class));
            }
        });

        stat = (TextView)v.findViewById(R.id.main_stat);

        //ImageView gif = (ImageView)v.findViewById(R.id.imageView);
        //Ion.with(gif).load()

        updateFragment();

        return v;
    }

    private void updateFragment(){
        stat.setText("Total = " + App.getTotalQuizzes() + ", answered = " + App.getAnsweredQuizzes());
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFragment();
    }
}

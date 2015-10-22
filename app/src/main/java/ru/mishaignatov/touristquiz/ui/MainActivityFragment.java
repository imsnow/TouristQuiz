package ru.mishaignatov.touristquiz.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;

import org.json.JSONObject;

import ru.mishaignatov.touristquiz.App;
import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.data.Quiz;
import ru.mishaignatov.touristquiz.database.DBHelper;
import ru.mishaignatov.touristquiz.server.Requests;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements Response.Listener<String>  {


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

                startActivity(new Intent(getActivity().getApplicationContext(), ActivityListFragment.class));
                //((MainActivity)getActivity()).changeFragment(new CountryListFragment());
            }
        });

        stat = (TextView)v.findViewById(R.id.main_stat);

        Button my_quiz = (Button)v.findViewById(R.id.button_my_quiz);
        my_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send();
                Toast.makeText(getActivity(), "You can send message in next version", Toast.LENGTH_LONG).show();
            }
        });

        updateFragment();

        return v;
    }

    private void send(){
        Requests.sendUsersQuiz(getActivity(), new Quiz("My question", "My answers", "lions"), "Russia", this);
    }

    private void updateFragment(){
        stat.setText("Total = " + App.getTotalQuizzes() + ", answered = " + App.getAnsweredQuizzes());
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFragment();
    }

    @Override
    public void onResponse(String response) {
        Log.d("TAG", "JSON response = " + response );
    }
}

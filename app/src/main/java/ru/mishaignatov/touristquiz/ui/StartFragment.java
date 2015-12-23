package ru.mishaignatov.touristquiz.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ru.mishaignatov.touristquiz.GameManager;
import ru.mishaignatov.touristquiz.HeaderInterface;
import ru.mishaignatov.touristquiz.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartFragment extends Fragment implements View.OnClickListener {


    private TextView stat;

    private HeaderInterface headerInterface;

    public StartFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        headerInterface = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        v.findViewById(R.id.button_start).setOnClickListener(this);
        v.findViewById(R.id.button_my_quiz).setOnClickListener(this);

        stat = (TextView)v.findViewById(R.id.main_stat);

        updateFragment();

        return v;
    }

    private void send(){
    //    Requests.sendUsersQuiz(getActivity(), new Quiz("My question", "My answers", "lions"), "Russia", this);
    }

    private void updateFragment(){
        Log.d("TAG", "update start fragment");
        stat.setText("Total = " + GameManager.getInstance(getActivity()).getTotal()
                + ", answered = " + GameManager.getInstance(getActivity()).getAnswered());
        headerInterface.onUpdateHeader("");
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFragment();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button_start:
                ((MainActivity)getActivity()).addFragment(new CountryListFragment(), "Country");
                break;
            case R.id.button_my_quiz:
                send();
                Toast.makeText(getActivity(), "You can send message in next version", Toast.LENGTH_LONG).show();
                break;
        }
    }
}

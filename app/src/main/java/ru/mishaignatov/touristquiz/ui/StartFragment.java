package ru.mishaignatov.touristquiz.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.ui.dialogs.UserQuestionDialog;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartFragment extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {

    private ActivityInterface activityInterface;

    public StartFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityInterface = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_start, container, false);

        v.findViewById(R.id.button_start).setOnClickListener(this);
        v.findViewById(R.id.button_my_quiz).setOnClickListener(this);

        updateFragment();

        return v;
    }

    private void send(){
    //    Requests.sendUsersQuiz(getActivity(), new Quiz("My question", "My answers", "lions"), "Russia", this);
    }

    private void updateFragment(){
        Log.d("TAG", "update start fragment");
        //stat.setText("Total = " + GameManager.getInstance(getActivity()).getTotal()
        //        + ", answered = " + GameManager.getInstance(getActivity()).getAnswered());
        activityInterface.onUpdateHeader("");
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
                activityInterface.onCountryListFragment("Country");
                break;
            case R.id.button_my_quiz:
                UserQuestionDialog dialog = new UserQuestionDialog();
                activityInterface.showFragmentDialog(dialog, "user_question");
                //DialogHelper.showUserQuestion(getActivity(), this);
                //Toast.makeText(getActivity(), "You can send message in next version", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        //dialog.
    }
}

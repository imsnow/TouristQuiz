package ru.mishaignatov.touristquiz.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.MainActivity;

/** *
 * Created by Mike on 09.02.2016.
 */
public class AchievementsFragment extends Fragment {

    private ListView mListView;

    private ActivityInterface headerInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        headerInterface = (MainActivity)activity;
        headerInterface.onUpdateHeader("Достижения");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_achievements, null);

        mListView = (ListView) rootView.findViewById(R.id.achievements_list_view);

        return rootView;
    }
}

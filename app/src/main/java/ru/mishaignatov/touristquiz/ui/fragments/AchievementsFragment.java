package ru.mishaignatov.touristquiz.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.database.Achievement;
import ru.mishaignatov.touristquiz.database.DbHelper;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.server.ApiHelper;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.MainActivity;

/** *
 * Created by Mike on 09.02.2016.
 */
public class AchievementsFragment extends BaseToolbarFragment {

    /*private ActivityInterface headerInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity)
            headerInterface = (MainActivity)context;
    }
    */
    @Override
    public void onResume() {
        super.onResume();
        updateHeader("Достижения");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_achievements, container, false);

        initHeader(rootView);

        ListView mListView = (ListView) rootView.findViewById(R.id.achievements_list_view);
        mListView.setAdapter(new AchievementAdapter(getContext(), R.layout.item_achievement, App.getDbHelper().getAchievementDao().getAllList()));

        return rootView;
    }

    private class AchievementAdapter extends ArrayAdapter<Achievement> {

        List<Achievement> achievementList;

        public AchievementAdapter(Context context, int resource, List<Achievement> objects) {
            super(context, resource, objects);
            achievementList = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.item_achievement, parent, false);
            }

            Achievement item = App.getDbHelper().getAchievementDao().getAchievementById(position);
            if (item != null) {
                ImageView icon = (ImageView)v.findViewById(R.id.item_achievement_image);
                icon.setImageResource(item.draw_resource);

                TextView title = (TextView)v.findViewById(R.id.item_achievement_text);
                title.setText(item.content);

                if (!item.isAchieved) {
                    v.setBackgroundResource(R.drawable.item_close);
                }
            }

            return v;
        }
    }
}

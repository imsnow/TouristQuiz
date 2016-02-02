package ru.mishaignatov.touristquiz.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.mishaignatov.touristquiz.R;

/**
 * Created by Mike on 01.02.2016.
 **/
public class LeaderBoardFragment extends Fragment {

    private LeaderBoardAdapter mAdapter;

    public interface Callback {
        void updateBoard();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View createHeader(){
        View header = getLayoutInflater(Bundle.EMPTY).inflate(R.layout.item_leaderboard, null);
        ((TextView)header.findViewById(R.id.leader_board_place)).setText("#");
        ((TextView)header.findViewById(R.id.leader_board_name)).setText("Name");
        ((TextView)header.findViewById(R.id.leader_board_scores)).setText("Scores");
        return header;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        List<String> list = new ArrayList<>();
        list.add("dvaseka");
        list.add("miha_mai");
        list.add("anton");

        mAdapter = new LeaderBoardAdapter(getActivity(), R.layout.item_leaderboard, list);

        View rootView = inflater.inflate(R.layout.fragment_leaderboard, null);
        ListView listView = (ListView) rootView.findViewById(R.id.leader_board_listview);
        listView.setAdapter(mAdapter);
        listView.addHeaderView(createHeader());

        return rootView;
    }

    private class LeaderBoardAdapter extends ArrayAdapter<String> {

        List<String> mList;

        public LeaderBoardAdapter(Context context, int res, List<String> objects) {
            super(context, res, objects);
            mList = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null){
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.item_leaderboard, null);
            }

            TextView place = (TextView)v.findViewById(R.id.leader_board_place);
            TextView name = (TextView)v.findViewById(R.id.leader_board_name);
            TextView scores = (TextView)v.findViewById(R.id.leader_board_scores);

            place.setText("" + (position+1));
            name.setText(mList.get(position));
            scores.setText(String.valueOf(new Random().nextInt(10000)));
            return v;
        }
    }
}

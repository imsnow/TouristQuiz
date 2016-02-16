package ru.mishaignatov.touristquiz.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.LeaderBoardItem;
import ru.mishaignatov.touristquiz.presenters.LeaderBoardPresenterImpl;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.MainActivity;
import ru.mishaignatov.touristquiz.ui.views.LeaderBoardView;

/**
 * Created by Mike on 01.02.2016.
 **/
public class LeaderBoardFragment extends Fragment implements LeaderBoardView {

    private ActivityInterface headerInterface;
    private LeaderBoardPresenterImpl mPresenter;

    private ListView mListView;
    private LeaderBoardAdapter mAdapter;
    private ProgressBar mProgressBar;

    private List<LeaderBoardItem> mListParent;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        headerInterface = (MainActivity)activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        headerInterface.showHeader();
        headerInterface.onUpdateHeader(getActivity().getString(R.string.header_leaderboard));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View createTableHeader(){
        View header = getLayoutInflater(Bundle.EMPTY).inflate(R.layout.item_leaderboard, null);
        TextView place = (TextView)header.findViewById(R.id.leader_board_place);
        place.setText(R.string.leaderboard_place);
        place.setGravity(Gravity.CENTER);
        TextView name = (TextView)header.findViewById(R.id.leader_board_name);
        name.setText(R.string.leaderboard_name);
        name.setGravity(Gravity.CENTER);
        TextView scores = (TextView)header.findViewById(R.id.leader_board_scores);
        scores.setText(R.string.leaderboard_scores);
        scores.setGravity(Gravity.RIGHT);
        return header;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_leaderboard, null);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.leader_board_progressbar);

        mListParent = new ArrayList<>();

        mListView = (ListView) rootView.findViewById(R.id.leader_board_listview);
        mListView.addHeaderView(createTableHeader());

        mPresenter = new LeaderBoardPresenterImpl(this);
        mPresenter.sendRequestTable();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onUpdateTable(List<LeaderBoardItem> list) {
        mListParent = list;
        mAdapter = new LeaderBoardAdapter(getActivity(), R.layout.item_leaderboard, mListParent);
        mListView.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        // TODO
    }

    private class LeaderBoardAdapter extends ArrayAdapter<LeaderBoardItem> {

        List<LeaderBoardItem> mList;

        public LeaderBoardAdapter(Context context, int res, List<LeaderBoardItem> objects) {
            super(context, res, objects);
            Collections.sort(objects);
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

            LeaderBoardItem item = mList.get(position);
            place.setText("" + (position+1));
            name.setText(item.getName());
            scores.setText(String.valueOf(item.getScores()));

            return v;
        }
    }
}

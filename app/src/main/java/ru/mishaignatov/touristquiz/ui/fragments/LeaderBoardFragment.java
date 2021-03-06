package ru.mishaignatov.touristquiz.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.LeaderBoardItem;
import ru.mishaignatov.touristquiz.presenters.LeaderBoardPresenterImpl;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.MainActivity;
import ru.mishaignatov.touristquiz.ui.dialogs.EnterNameDialog;
import ru.mishaignatov.touristquiz.ui.views.LeaderBoardView;

/**
 * Created by Mike on 01.02.2016.
 **/
public class LeaderBoardFragment extends BaseToolbarFragment implements LeaderBoardView {

    private ActivityInterface activityInterface;
    private LeaderBoardPresenterImpl mPresenter;

    private ListView mListView;
    private ProgressBar mProgressBar;

    private List<LeaderBoardItem> mListParent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity)
            activityInterface = (MainActivity)context;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateHeader(getString(R.string.header_leaderboard));
        //activityInterface.showHeader();
        //activityInterface.onUpdateHeader(getActivity().getString(R.string.header_leaderboard));
    }

    private View createTableHeader(){
        View header = getLayoutInflater(Bundle.EMPTY).inflate(R.layout.item_leaderboard, null, false);
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

        View rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        initHeader(rootView);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.leader_board_progressbar);

        mListParent = new ArrayList<>();

        mListView = (ListView) rootView.findViewById(R.id.leader_board_listview);
        mListView.addHeaderView(createTableHeader());

        mPresenter = new LeaderBoardPresenterImpl(this);
        mPresenter.checkName();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void showProgressBar() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onUpdateTable(List<LeaderBoardItem> list) {
        mListParent = list;
        LeaderBoardAdapter mAdapter = new LeaderBoardAdapter(getActivity(), R.layout.item_leaderboard, mListParent);
        mListView.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEnterNameDialog() {
        EnterNameDialog enterNameDialog = new EnterNameDialog();
        enterNameDialog.setTargetFragment(this, 0x23);
        activityInterface.showDialog(enterNameDialog, "enter_name");
        //enterNameDialog.show(getChildFragmentManager(), "enter_name");
    }

    @Override
    public void onResultDialog() {
        mPresenter.sendRequestTable();
    }

    @Override
    public void showError() {
        // TODO
    }

    private class LeaderBoardAdapter extends ArrayAdapter<LeaderBoardItem> {

        List<LeaderBoardItem> mList;
        private String mName;

        public LeaderBoardAdapter(Context context, int res, List<LeaderBoardItem> objects) {
            super(context, res, objects);
            Collections.sort(objects);
            mList = objects;
            mName = GameManager.getInstance(context).getUser().getDisplayName();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null){
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.item_leaderboard, parent, false);
            }

            TextView place = (TextView)v.findViewById(R.id.leader_board_place);
            TextView name = (TextView)v.findViewById(R.id.leader_board_name);
            TextView scores = (TextView)v.findViewById(R.id.leader_board_scores);

            LeaderBoardItem item = mList.get(position);

            if (item.getName().equals(mName))
                v.setBackgroundResource(R.drawable.item_accent);
            else
                v.setBackgroundResource(R.drawable.item);

            final float scale = getResources().getDisplayMetrics().density;
            int dpInPx = (int) (4 * scale + 0.5f);
            v.setPadding(dpInPx, dpInPx, dpInPx, dpInPx);

            place.setText(String.valueOf(position+1));
            name.setText(item.getName());
            scores.setText(String.valueOf(item.getScores()));

            return v;
        }
    }
}

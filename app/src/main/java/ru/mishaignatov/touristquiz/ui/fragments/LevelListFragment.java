package ru.mishaignatov.touristquiz.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.database.Level;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.presenters.LevelListPresenter;
import ru.mishaignatov.touristquiz.presenters.LevelListPresenterImpl;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.DialogHelper;
import ru.mishaignatov.touristquiz.ui.MainActivity;
import ru.mishaignatov.touristquiz.ui.dialogs.BuyTicketDialog;
import ru.mishaignatov.touristquiz.ui.views.LevelListView;

/**
 * Created by Ignatov on 13.08.2015.
 * Display all counties - levels
 */
public class LevelListFragment extends ListFragment implements LevelListView {

    private LevelAdapter adapter;
    private ActivityInterface headerInterface;

    private LevelListPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity)
            headerInterface = (MainActivity)context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new LevelAdapter(getActivity(), mPresenter.getLevelList());
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void showClosedLevel(int position) {
        //headerInterface.onShowHiddenTip("Для полета необходимо купить билет");
        BuyTicketDialog dialog = new BuyTicketDialog();
        Bundle args = new Bundle();
        args.putInt("KEY_ID", position);
        dialog.setArguments(args);
        dialog.setTargetFragment(this, 0x24);
        dialog.show(getFragmentManager(), "buy_dialog");
    }

    @Override
    public void showNotEnoughMillis() {
        headerInterface.onShowHiddenTip("Недостаточно милей для покупки билета");
    }

    @Override
    public void showDialogLevelFinished() {
        // Пользователь уже попытался ответить на все вопросы
        DialogHelper.showDialogLevelFinished(getActivity());
    }

    @Override
    public void startLevel(int position) {
        Bundle args = new Bundle();
        args.putInt("COUNTRY_ID", position);
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);

        ((MainActivity) getActivity()).addFragment(fragment, "Question");
    }

    @Override
    public void update(){
        mPresenter.updateLevels();
        adapter.notifyDataSetChanged();

        headerInterface.showHeader();
        headerInterface.onUpdateHeader("Куда отправимся?");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mPresenter = new LevelListPresenterImpl(App.getContext(), this);

        return inflater.inflate(R.layout.fragment_country_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        //GameManager.getInstance(getActivity()).setCurrentCountryId(position);
        mPresenter.onListItemClick(position);
    }

    private class LevelAdapter extends ArrayAdapter<Level> {

        private List<Level> mList;

        public LevelAdapter(Context context, List<Level> objects) {
            super(context, 0, objects);
            mList = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view;

            Level item = mPresenter.getLevel(position);
            Log.d("TAG", "pos = " + position + " id = " + item.id);
            //if(view == null)
            if (item.is_opened) {
                view = inflater.inflate(R.layout.item_level_open, parent, false);

                TextView name = (TextView)view.findViewById(R.id.level_name);
                name.setText(item.name);

                TextView result = (TextView)view.findViewById(R.id.level_result);
                result.setText("" + item.questions_answered + "/" + item.questions_total);

                if (item.is_ended){
                    view.findViewById(R.id.level_thumb).setVisibility(View.VISIBLE);
                }
            }
            else {
                view = inflater.inflate(R.layout.item_level_closed, parent, false);

                TextView name = (TextView)view.findViewById(R.id.item_level_name);
                name.setText(item.name);

                TextView cost = (TextView)view.findViewById(R.id.item_level_cost);
                cost.setText(String.valueOf(item.cost));

                ImageView buy = (ImageView)view.findViewById(R.id.item_level_buy);
                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buyLevel(position);
                    }
                });
            }

            return view;
        }
    }

    public void buyLevel(int position){
        mPresenter.onBuyLevel(position);
    }
}

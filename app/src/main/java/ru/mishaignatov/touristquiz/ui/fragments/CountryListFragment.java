package ru.mishaignatov.touristquiz.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.orm.Country;
import ru.mishaignatov.touristquiz.presenters.CountryListPresenter;
import ru.mishaignatov.touristquiz.presenters.CountryListPresenterImpl;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.DialogHelper;
import ru.mishaignatov.touristquiz.ui.MainActivity;
import ru.mishaignatov.touristquiz.ui.views.CountryListView;

/**
 * Created by Ignatov on 13.08.2015.
 * Display all counties - levels
 */
public class CountryListFragment extends ListFragment implements CountryListView {

    private CountryAdapter adapter;
    private ActivityInterface headerInterface;

    private CountryListPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity)
            headerInterface = (MainActivity)context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new CountryAdapter(getActivity(), mPresenter.getCountryList());
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void showClosedCountry() {
        headerInterface.onShowHiddenTip("Для полета необходимо купить билет");
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
        mPresenter.updateCountries();
        adapter.notifyDataSetChanged();

        headerInterface.showHeader();
        headerInterface.onUpdateHeader("Куда отправимся?");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mPresenter = new CountryListPresenterImpl(App.getContext(), this);

        return inflater.inflate(R.layout.fragment_country_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        //GameManager.getInstance(getActivity()).setCurrentCountryId(position);
        mPresenter.onListItemClick(position);
    }

    private class CountryAdapter extends ArrayAdapter<Country> {

        private List<Country> mList;

        public CountryAdapter(Context context, List<Country> objects) {
            super(context, 0, objects);
            mList = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view;
            Country item = mList.get(position);

            //if(view == null)
            if (item.opened) {
                view = inflater.inflate(R.layout.item_country, parent, false);

                TextView name = (TextView)view.findViewById(R.id.country_name);
                name.setText(item.value);

                TextView result = (TextView)view.findViewById(R.id.country_result);
                result.setText("" + item.answered + "/" + item.total);

                if (item.ended){
                    view.findViewById(R.id.country_thumb).setVisibility(View.VISIBLE);
                }
            }
            else {
                view = inflater.inflate(R.layout.item_country_closed, parent, false);

                TextView name = (TextView)view.findViewById(R.id.item_country_name);
                name.setText(item.value);

                TextView cost = (TextView)view.findViewById(R.id.item_country_cost);
                cost.setText(String.valueOf(item.cost));

                ImageView buy = (ImageView)view.findViewById(R.id.item_country_buy);
                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.onBuyCountry(position);
                    }
                });
            }

            return view;
        }
    }
}

package ru.mishaignatov.touristquiz.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.CountryManager;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.orm.Country;
import ru.mishaignatov.touristquiz.orm.OrmDao;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.DialogHelper;
import ru.mishaignatov.touristquiz.ui.MainActivity;

/**
 * Created by Ignatov on 13.08.2015.
 * Display all counties - levels
 */
public class CountryListFragment extends ListFragment {

    private CountryManager mCountryManager;
    private CountryAdapter adapter;
    private ActivityInterface headerInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        headerInterface = (MainActivity)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCountryManager = CountryManager.getInstance(App.getContext());

        adapter = new CountryAdapter();
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
        headerInterface.showHeader();
        headerInterface.onUpdateHeader("Список стран");
    }

    public void update(){
        mCountryManager.updateCountries();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_list, null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        GameManager.getInstance(getActivity()).setCurrentCountryId(position);

        if(OrmDao.getInstance(getActivity()).isCountryShown(position)){
            // Пользователь уже попытался ответить на все вопросы
            DialogHelper.showDialogLevelFinished(getActivity());
        }
        else {
            ((MainActivity) getActivity()).addFragment(new QuestionFragment(), "Question");
            //mCurrentCountry = countriesList.get(position);
        }
    }

    private class CountryAdapter extends BaseAdapter {

        //private List<Country> countriesList;

        public CountryAdapter() {
            //super(context);
            //countriesList = mCountryManager.getList();
        }

        @Override
        public boolean areAllItemsEnabled() {
            return super.areAllItemsEnabled();
        }

        @Override
        public int getCount() {
            return mCountryManager.getList().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view;
            Country item = mCountryManager.getList().get(position);

            //if(view == null)
            if (item.opened) {
                view = inflater.inflate(R.layout.item_country, null);

                TextView name = (TextView)view.findViewById(R.id.country_name);
                name.setText(item.value);

                TextView result = (TextView)view.findViewById(R.id.country_result);
                result.setText("" + item.answered + "/" + item.total);
            }
            else {
                view = inflater.inflate(R.layout.item_country_closed, null);

                TextView name = (TextView)view.findViewById(R.id.item_country_name);
                name.setText(item.value);

                TextView cost = (TextView)view.findViewById(R.id.item_country_cost);
                cost.setText(String.valueOf(item.cost));
            }

            return view;
        }
    }
}

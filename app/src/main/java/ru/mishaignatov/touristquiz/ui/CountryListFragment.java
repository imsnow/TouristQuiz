package ru.mishaignatov.touristquiz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.orm.Country;
import ru.mishaignatov.touristquiz.orm.OrmDao;

/**
 * Created by Ignatov on 13.08.2015.
 * Display all counties
 */
public class CountryListFragment extends ListFragment {

    private List<Country> countriesList = new ArrayList<>();
    private CountryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OrmDao ormDao = OrmDao.getInstance(getActivity());
        countriesList = ormDao.getCountryList();
        Log.d("TAG", "size " + countriesList.size());
        //String[] array = getResources().getStringArray(R.array.countries);
        //countriesList = Arrays.asList(array);
        adapter = new CountryAdapter();
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.d("TAG", "Resume to CountryList");
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_list, null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Intent i = new Intent(getActivity(), QuizActivity.class);
        i.putExtra("country_id", position);
        startActivity(i);

        /*
        Country country = countriesList.get(position);
        Log.d("TAG", "country = " + country);

        List<Question> list = OrmDao.getInstance(getActivity()).getQuestionsList(country);
        Log.d("TAG", "list size = " + list.size());
        */
        /*
        String country = countriesList.get(position);
        Log.d("TAG", "position = " + position + " country = " + country);

        Country checkedCountry = Queries.loadCountry(App.getDataBase(), country);
        if( checkedCountry.isFinished() ) { // Вопросы уже все отгаданы
            DialogHelper.showDialogLevelFinished(getActivity());
            return;
        }

        Intent i = new Intent(getActivity(), QuizActivity.class);
        //i.putParcelableArrayListExtra("QUIZZES", quizzes);
        i.putExtra("COUNTRY", country);

        startActivity(i);
        */
    }


    private class CountryAdapter extends BaseAdapter {

        public CountryAdapter() {
            //super(context);
        }

        @Override
        public int getCount() {
            return countriesList.size();
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
            if(convertView == null)
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_country, null);

            Country item = countriesList.get(position);

            TextView name = (TextView)convertView.findViewById(R.id.country_name);
            name.setText(item.value);
            /*
            String countryName = countriesList.get(position);
            Country country = Queries.loadCountry(App.getDataBase(), countryName);

            TextView name = (TextView)convertView.findViewById(R.id.country_name);
            String rusValue = CountryTranslator.getRusValue(position);
            name.setText(rusValue);
            */
            TextView result = (TextView)convertView.findViewById(R.id.country_result);
            result.setText("" + item.total);

            return convertView;
        }
    }
}

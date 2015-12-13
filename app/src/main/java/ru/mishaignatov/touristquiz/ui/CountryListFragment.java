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

import ru.mishaignatov.touristquiz.App;
import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.data.Country;
import ru.mishaignatov.touristquiz.database.Queries;

/**
 * Created by Ignatov on 13.08.2015.
 * Display all counties
 */
public class CountryListFragment extends ListFragment {

    private List<String> countriesNameList = new ArrayList<>();
    private CountryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        countriesNameList = Queries.getCountryList(App.getDataBase());
        Log.d("TAG", "size " + countriesNameList.size());
        //String[] array = getResources().getStringArray(R.array.countries);
        //countriesNameList = Arrays.asList(array);
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

        String country = countriesNameList.get(position);
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
    }


    private class CountryAdapter extends BaseAdapter {

        public CountryAdapter() {
            //super(context);
        }


        @Override
        public int getCount() {
            return countriesNameList.size();
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

            //Log.d("TAG", "getView position = " + position);

            String countryName = countriesNameList.get(position);
            Country country = Queries.loadCountry(App.getDataBase(), countryName);

            TextView name = (TextView)convertView.findViewById(R.id.country_name);
            String rusValue = CountryTranslator.getRusValue(position);
            name.setText(rusValue);

            TextView result = (TextView)convertView.findViewById(R.id.country_result);
            result.setText("" + country.getAnswered() + "/" + country.getTotal());

            return convertView;
        }
    }
}

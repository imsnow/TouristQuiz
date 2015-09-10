package ru.mishaignatov.touristquiz.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.mishaignatov.touristquiz.App;
import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.data.Country;
import ru.mishaignatov.touristquiz.data.Quiz;
import ru.mishaignatov.touristquiz.database.Queries;

/**
 * Created by Ignatov on 13.08.2015.
 * Display all counties
 */
public class CountryListFragment extends ListFragment {

    private List<String> countriesList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] array = getResources().getStringArray(R.array.countries);
        countriesList = Arrays.asList(array);

        setListAdapter(new CountryAdapter(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_list, null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        String country = countriesList.get(position);
        Log.d("TAG", "position = " + position + " country = " + country);
        //ArrayList<Quiz> quizzes = App.getDBHelper().getQuizzesList(App.getDataBase(), list.get(position));


        Intent i = new Intent(getActivity(), QuizActivity.class);
        //i.putParcelableArrayListExtra("QUIZZES", quizzes);
        i.putExtra("COUNTRY", country);

        startActivity(i);
    }

    private class CountryAdapter extends ArrayAdapter<String> {

        public CountryAdapter(Context context) {
            super(context, 0, countriesList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null)
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_country, null);

            String currentCountryName = countriesList.get(position);
            Country country = Queries.loadCountry(App.getDataBase(), currentCountryName);


            TextView name = (TextView)convertView.findViewById(R.id.country_name);
            name.setText(country.getName());

            TextView result = (TextView)convertView.findViewById(R.id.country_result);
            result.setText("" + country.getNoAnswered() + "/" + country.getTotal());

            return convertView;
        }
    }
}

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
import java.util.List;

import ru.mishaignatov.touristquiz.App;
import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.data.Quiz;

/**
 * Created by Ignatov on 13.08.2015.
 * Display all counties
 */
public class CountryListFragment extends ListFragment {

    //private List<Country> list;
    private List<String> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new CountryAdapter(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_list, null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Log.d("TAG", "position = " + position + " country = " + list.get(position));
        ArrayList<Quiz> quizzes = App.getDBHelper().getQuizzesList(App.getDataBase(), list.get(position));


        Intent i = new Intent(getActivity(), QuizActivity.class);
        i.putParcelableArrayListExtra("QUIZZES", quizzes);

        startActivity(i);
    }

    private class CountryAdapter extends ArrayAdapter<String> {
    //private class CountryAdapter extends ArrayAdapter<Country> {

        //private List<Country> list;
        public CountryAdapter(Context context) {
            //super(context, 0, CountryStorage.getStorage().getCountryList());
            //list =  CountryStorage.getStorage().getCountryList();
            super(context, 0, App.getCountriesList());
            list =  App.getCountriesList();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null)
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_country, null);

            ///Country item = list.get(position);
            TextView name = (TextView)convertView.findViewById(R.id.country_name);
            //name.setText(item.getName());
            name.setText(list.get(position));

            //TextView result = (TextView)convertView.findViewById(R.id.country_result);
            //result.setText("" + item.getAnswered() + "/" + item.getTotal());

            return convertView;
        }
    }
}

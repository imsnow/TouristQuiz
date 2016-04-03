package ru.mishaignatov.touristquiz.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.database.Tip;
import ru.mishaignatov.touristquiz.game.App;

/***
 * Created by Mike on 16.02.2016.
 */
public class TipsFragment extends BaseToolbarFragment {
    /*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ActivityInterface headerInterface = (MainActivity)activity;
        //headerInterface.showHeader();
        //headerInterface.onUpdateHeader(activity.getString(R.string.header_tips));
    }
    */
    @Override
    public void onResume() {
        super.onResume();
        updateHeader(getString(R.string.header_tips));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tips, null);

        initHeader(rootView);

        ListView listView = (ListView)rootView.findViewById(R.id.tips_listview);

        List<Tip> tipList = App.getDbHelper().getTipDao().getTipsList();

        listView.setAdapter(new TipsAdapter(getActivity(), tipList));

        return rootView;
    }

    private class TipsAdapter extends ArrayAdapter<Tip> {

        List<Tip> mObjects;

        public TipsAdapter(Context context, List<Tip> objects) {
            super(context, R.layout.item_tip, objects);
            mObjects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null){
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.item_tip, null);
            }

            TextView text = (TextView) v.findViewById(R.id.item_tip_text);
            text.setText(mObjects.get(position).text);

            return v;
        }
    }
}

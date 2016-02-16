package ru.mishaignatov.touristquiz.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.orm.OrmDao;
import ru.mishaignatov.touristquiz.orm.Tip;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.MainActivity;

/***
 * Created by Mike on 16.02.2016.
 */
public class TipsFragment extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ActivityInterface headerInterface = (MainActivity)activity;
        headerInterface.showHeader();
        headerInterface.onUpdateHeader(activity.getString(R.string.header_tips));
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tips, null);

        ListView listView = (ListView)rootView.findViewById(R.id.tips_listview);

        List<Tip> tipList = OrmDao.getInstance(App.getContext()).getTipsList();

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

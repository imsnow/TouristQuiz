package ru.mishaignatov.touristquiz.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.App;

/***
 * Created by Leva on 27.02.2016.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView version = (TextView) v.findViewById(R.id.settings_item_version);
        version.setText(getString(R.string.version, App.version));

        v.findViewById(R.id.settings_item_reset).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_item_reset:
                break;
        }
    }
}

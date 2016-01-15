package ru.mishaignatov.touristquiz.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.server.APIStrings;
import ru.mishaignatov.touristquiz.server.ApiHelper;

/**
 * Created by Leva on 22.12.2015.
 * This fragment registers user, load bd and another resources
 */
public class LoadFragment extends Fragment implements Response.Listener<String> {

    private ActivityInterface tipsInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tipsInterface = (MainActivity)activity;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_load, container, false);

        //new ImitationAsyncTask().execute();
        Log.d("TAG", "loadFragment");
        ApiHelper.getHelper(getActivity()).userRegister(
                GameManager.getInstance(getActivity()).getUser(), this);

        return v;
    }

    @Override
    public void onResponse(String response) {

        Log.d("TAG", "response = " + response);

        try {
            JSONObject json = new JSONObject(response);
            String status = json.optString(APIStrings.STATUS);
            if(status.equals(APIStrings.OK)) {
                GameManager.getInstance(getActivity()).getUser().confirmRegistration();
                String token = json.optString(APIStrings.TOKEN);
                GameManager.getInstance(getActivity()).getUser().setToken(token);
                //((MainActivity)getActivity()).replaceFragment(new StartFragment());
            }
            else // что-то полшло не так
                tipsInterface.onShowTip(response);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // все равно запускаем игру
        ((MainActivity)getActivity()).replaceFragment(new StartFragment());
    }
}

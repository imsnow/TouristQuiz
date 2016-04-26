package ru.mishaignatov.touristquiz.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.server.APIStrings;
import ru.mishaignatov.touristquiz.server.ApiHelper;
import ru.mishaignatov.touristquiz.ui.ActivityInterface;
import ru.mishaignatov.touristquiz.ui.MainActivity;

/**
 * Created by Leva on 22.12.2015.
 * This fragment registers user, load bd and another resources
 */
public class LoadFragment extends Fragment implements
        Response.Listener<String>, Response.ErrorListener {

    private ActivityInterface tipsInterface;

    private ProgressBar mProgressBar;
    private Button mRetryButton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tipsInterface = (MainActivity)activity;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApiHelper.getHelper(App.getContext()).userRegister(
                GameManager.getInstance(App.getContext()).getUser(), this, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_load, container, false);
        mProgressBar = (ProgressBar) v.findViewById(R.id.load_progress_bar);
        mRetryButton = (Button) v.findViewById(R.id.load_retry_button);
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryRegistration();
            }
        });
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
                tipsInterface.onShowHiddenTip(response);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // все равно запускаем игру
        tipsInterface.onStartFragment();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        tipsInterface.onShowHiddenTip("Error message = " + error.getMessage() + " time = " + error.getNetworkTimeMs());
        showButtonRetry();
    }

    private void showButtonRetry(){
        mProgressBar.setVisibility(View.GONE);
        mRetryButton.setVisibility(View.VISIBLE);
    }

    private void tryRegistration(){
        mProgressBar.setVisibility(View.VISIBLE);
        mRetryButton.setVisibility(View.GONE);
        ApiHelper.getHelper(App.getContext()).userRegister(
                GameManager.getInstance(App.getContext()).getUser(), this, this);
    }
}

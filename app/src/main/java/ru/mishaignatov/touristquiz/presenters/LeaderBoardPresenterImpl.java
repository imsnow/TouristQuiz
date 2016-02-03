package ru.mishaignatov.touristquiz.presenters;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.LeaderBoardItem;
import ru.mishaignatov.touristquiz.server.APIStrings;
import ru.mishaignatov.touristquiz.server.ApiHelper;
import ru.mishaignatov.touristquiz.ui.views.LeaderBoardView;

/**
 * Created by Mike on 03.02.2016.
 **/
public class LeaderBoardPresenterImpl implements LeaderBoardPresenter, Response.Listener<String>, Response.ErrorListener {

    private LeaderBoardView iView;

    public LeaderBoardPresenterImpl(LeaderBoardView iView){
        this.iView = iView;
    }

    @Override
    public void sendRequestTable() {

        iView.showProgressBar();

        ApiHelper.getHelper(App.getContext()).leaderBoard(GameManager.getInstance(App.getContext()).getUser(), this, this);
    }

    @Override
    public void onDestroy() {
        iView = null;
    }

    @Override
    public void onResponse(String response) {

        List<LeaderBoardItem> list = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(response);
            String status = json.getString(APIStrings.STATUS);
            if(status.equals(APIStrings.OK)){
                JSONArray arr = json.getJSONArray(APIStrings.ITEMS);
                for(int i=0; i<arr.length(); i++){
                    JSONObject jsonItem = arr.getJSONObject(i);
                    LeaderBoardItem item = new LeaderBoardItem(jsonItem.getString(APIStrings.NAME),
                                    Integer.parseInt(jsonItem.getString(APIStrings.PLACE)),
                                    Integer.parseInt(jsonItem.getString(APIStrings.SCORES)));
                    list.add(item);
                }
            }
            else iView.showError();
            //Log.d("TAG", json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            iView.showError();
        }

        iView.onUpdateTable(list);
        iView.hideProgressBar();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        iView.showError();
        iView.hideProgressBar();
    }
}

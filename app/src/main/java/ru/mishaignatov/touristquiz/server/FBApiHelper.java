package ru.mishaignatov.touristquiz.server;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

import ru.mishaignatov.touristquiz.game.User;

/**
 * Created by Mike on 25.02.2016.
 **/
public class FBApiHelper {

    public void sendProfileRequest(User user){

        GraphRequest request = GraphRequest.newMeRequest(
                user.getFbAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        Log.d("TAG", object.toString());
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }
}

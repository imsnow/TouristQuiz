package ru.mishaignatov.touristquiz.server;

import android.os.Bundle;

import com.facebook.GraphRequest;

import ru.mishaignatov.touristquiz.game.User;

/**
 * Created by Mike on 25.02.2016.
 **/
public class FBApiHelper {

    public static final String FIRST_NAME_TAG = "first_name";
    public static final String LAST_NAME_TAG = "last_name";

    public void sendProfileRequest(User user, GraphRequest.GraphJSONObjectCallback callback){

        GraphRequest request = GraphRequest.newMeRequest(user.getFbAccessToken(), callback);

        Bundle parameters = new Bundle();
        parameters.putString("fields", FIRST_NAME_TAG + "," + LAST_NAME_TAG);
        request.setParameters(parameters);
        request.executeAsync();
    }
}

package ru.mishaignatov.touristquiz.server;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

/**
 * Created by Mike on 25.02.2016.
 **/
public class FBApiHelper {

    public void sendProfileRequest(){
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{user-id}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        Log.d("TAG", "response = " + response.toString());
                    }
                }
        ).executeAsync();
    }
}

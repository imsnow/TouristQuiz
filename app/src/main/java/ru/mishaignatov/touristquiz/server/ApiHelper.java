package ru.mishaignatov.touristquiz.server;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.User;

/**
 * Created by Leva on 26.12.2015.
 *
 */
public class ApiHelper {

    private Context context;

    private static ApiHelper helper;

    private RequestQueue queue;

    public static ApiHelper getHelper(Context context){
        if(helper == null) helper = new ApiHelper(context);
        return helper;
    }

    private ApiHelper(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    // MAIN method
    private void sendRequest(String param,
                             Response.Listener<String> listener,
                             Response.ErrorListener errorListener){

        Log.d("TAG", "request param = " + param);

        StringRequest request = new StringRequest(Request.Method.GET, APIStrings.URL + param, listener, errorListener);
        queue.add(request);
    }

    //=================================================
    public void userRegister(User user, Response.Listener<String> listener, Response.ErrorListener errorListener){

        String param = APIStrings.USER_REGISTER +
                        addParam(APIStrings.IMEI, encode(user.getImei())) +
                        addParam(APIStrings.EMAIL, encode(user.getEmail())) +
                        addParam(APIStrings.DEVICE, encode(user.getDevice())) +
                        addParam(APIStrings.ANDROID, encode(user.getAndroidApi())) +
                        addParam(APIStrings.VERSION, encode(App.getVersion()));

        sendRequest(param, listener, errorListener);
    }

    public void userResult(User user, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String param = APIStrings.USER_RESULT +
                        addParam(APIStrings.TOKEN, encode(user.getToken())) +
                        addParam(APIStrings.SCORES, user.getScores()) +
                        addParam(APIStrings.MILLIS, user.getMillis()) +
                        addParam(APIStrings.QUESTIONS, GameManager.getInstance(context).getAnswered());

        sendRequest(param, listener, errorListener);
    }

    public void userSession(User user, long session, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String param = APIStrings.USER_SESSION +
                addParam(APIStrings.TOKEN, encode(user.getToken())) +
                addParam(APIStrings.SESSION, encode(String.valueOf(session)));
        sendRequest(param, listener, errorListener);
    }

    public void leaderBoard(User user, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String param = APIStrings.LEADER_BOARD +
                addParam(APIStrings.TOKEN, encode(user.getToken()));
        sendRequest(param, listener, errorListener);
    }

    public void userQuestion(User user, String text, String answer, String country, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String param = APIStrings.USER_QUESTION + addParam(APIStrings.TOKEN, encode(user.getToken())) +
                addParam(APIStrings.TEXT, encode(text)) + addParam(APIStrings.ANSWERS, encode(answer)) + addParam(APIStrings.COUNTRY, encode(country));

        sendRequest(param, listener, errorListener);
    }

    public void userNameCheck(User user, String name, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String param = APIStrings.USER_NAME_CHECK + addParam(APIStrings.TOKEN, encode(user.getToken())) +
                addParam(APIStrings.NAME, encode(name));

        sendRequest(param, listener, errorListener);
    }

    public void userNameSet(User user, String name, User.TypeName type, Response.Listener<String> listener, Response.ErrorListener errorListener){
        String param = APIStrings.USER_NAME_SET + addParam(APIStrings.TOKEN, encode(user.getToken())) +
                addParam(APIStrings.NAME, encode(name)) + addParam(APIStrings.TYPE, type);

        sendRequest(param, listener, errorListener);
    }


    private static String addParam(String key, String value){
        return "&" + key + "=" + value;
    }

    private static String addParam(String key, int value){
        return "&" + key + "=" + value;
    }

    private static String addParam(String key, User.TypeName value){
        return "&" + key + "=" + value.toString().toLowerCase();
    }

    private static String encode(String str){
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

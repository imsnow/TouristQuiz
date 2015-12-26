package ru.mishaignatov.touristquiz.server;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ru.mishaignatov.touristquiz.user.User;
import ru.mishaignatov.touristquiz.ui.MainActivity;
import ru.mishaignatov.touristquiz.ui.TipsInterface;

/**
 * Created by Leva on 26.12.2015.
 *
 */
public class ApiHelper implements Response.ErrorListener {

    private Activity activity;

    private static ApiHelper helper;

    public static ApiHelper getHelper(Activity activity){
        if(helper == null) helper = new ApiHelper(activity);
        return helper;
    }

    private ApiHelper(Activity activity){
        this.activity = activity;
    }

    private void sendRequest(String param, Response.Listener<String> listener){

        RequestQueue queue = Volley.newRequestQueue(activity);

        StringRequest request = new StringRequest(Request.Method.GET, APIStrings.URL + param, listener, this);

        queue.add(request);
    }

    public void userRegister(User user, Response.Listener<String> listener){

        String param = APIStrings.USER_REGISTER +
                        addParam(APIStrings.IMEI, encode(user.getImei())) +
                        addParam(APIStrings.EMAIL, encode(user.getEmail())) +
                        addParam(APIStrings.DEVICE, encode(user.getDevice())) +
                        addParam(APIStrings.ANDROID, encode(user.getAndroidApi()));

        sendRequest(param, listener);
    }


    private static String addParam(String key, String value){
        return "&" + key + "=" + value;
    }

    private static String encode(String str){
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        TipsInterface tipsInterface = (MainActivity)activity;
        tipsInterface.onShowTip(error.getMessage());
    }
}

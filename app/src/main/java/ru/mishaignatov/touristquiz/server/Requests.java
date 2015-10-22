package ru.mishaignatov.touristquiz.server;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ru.mishaignatov.touristquiz.data.Quiz;

/**
 * Created by Ignatov on 22.10.2015.
 */
public class Requests {

    private static final String URL = "http://tourist-quiz.appspot.com/hello?method=";

    private static final String USER_QUIZ = "user.quiz";
    private static final String ERROR = "error";
    private static final String UPDATE_BASE = "db.update";
    private static final String RATING = "user.rating";

    public static void sendUsersQuiz(Context context, Quiz quiz, String country, Response.Listener<String> listener){

        RequestQueue queue = Volley.newRequestQueue(context);

        String param = USER_QUIZ + "&text=" + encode(quiz.getText()) +
                "&answers=" + encode(quiz.getStringAnswers()) +
                "&country=" + encode(country);

        //JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL + USER_QUIZ, null, listener, null);
        StringRequest request = new StringRequest(Request.Method.GET, URL+param, listener, null);

        queue.add(request);
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

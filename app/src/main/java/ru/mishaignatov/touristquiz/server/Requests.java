package ru.mishaignatov.touristquiz.server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Ignatov on 22.10.2015.
 *
 */
public class Requests {

    /*
    public static void sendUsersQuiz(Context context, Quiz quiz, String country, Response.Listener<String> listener){

        RequestQueue queue = Volley.newRequestQueue(context);

        String param = USER_QUIZ + "&text=" + encode(quiz.getText()) +
                "&answers=" + encode(quiz.getStringAnswers()) +
                "&country=" + encode(country);

        //JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL + USER_QUIZ, null, listener, null);
        StringRequest request = new StringRequest(Request.Method.GET, URL+param, listener, null);

        queue.add(request);
    }
    */
    private static String encode(String str){
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

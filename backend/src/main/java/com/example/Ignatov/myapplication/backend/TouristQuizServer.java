package com.example.Ignatov.myapplication.backend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ignatov on 20.10.2015.
 *
 */
public class TouristQuizServer {

    protected static final String TYPE = "application/json; charset=UTF-8";

    private static final SimpleDateFormat sdm = new SimpleDateFormat("dd MMM YYYY, HH:mm", Locale.getDefault());

    protected static void processUserQuiz(HttpServletRequest req, DatastoreService database){

        String text = req.getParameter("text");
        String answers = req.getParameter("answers");
        String country = req.getParameter("country");

        Entity entity = new Entity(DBStrings.USER_QUESTIONS);
        entity.setProperty("text", text);
        entity.setProperty("answers", answers);
        entity.setProperty("country", country);
        database.put(entity);

    }

    // return error message - if value equal "null" no error
    protected static String processUserRegister(HttpServletRequest req, DatastoreService database){

        String imei  = req.getParameter(APIStrings.IMEI);

        Query query = new Query(DBStrings.USERS);
        Query.Filter filter = new Query.FilterPredicate(APIStrings.IMEI, Query.FilterOperator.EQUAL, imei);
        query.setFilter(filter);
        PreparedQuery preparedQuery = database.prepare(query);
        int size = preparedQuery.countEntities();
        if (size != 0)
                return "user already exists";

        String email = req.getParameter(APIStrings.EMAIL);
        String device = req.getParameter(APIStrings.DEVICE);
        String api    = req.getParameter(APIStrings.ANDROID);
        String date  = sdm.format(new Date());

        Entity entity = new Entity(DBStrings.USERS);
        entity.setProperty(APIStrings.IMEI,  imei);
        entity.setProperty(APIStrings.EMAIL, email);
        entity.setProperty(APIStrings.DEVICE, device);
        entity.setProperty(APIStrings.ANDROID, api);
        entity.setProperty(APIStrings.REG_TIME, date);
        database.put(entity);

        return APIStrings.OK;
    }
}

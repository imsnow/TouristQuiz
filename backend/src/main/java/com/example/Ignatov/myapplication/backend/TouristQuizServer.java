package com.example.Ignatov.myapplication.backend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ignatov on 20.10.2015.
 *
 */
public class TouristQuizServer {

    protected static final String TYPE = "application/json; charset=UTF-8";

    private static final SimpleDateFormat sdm = new SimpleDateFormat("dd MMM YYYY, HH:mm", Locale.getDefault());

    protected static String processUserQuiz(HttpServletRequest req, DatastoreService database){

        String token = req.getParameter(APIStrings.TOKEN);

        // Check that user existes
        Query query = new Query(DBStrings.USERS);
        Query.Filter filter = new Query.FilterPredicate(APIStrings.TOKEN, Query.FilterOperator.EQUAL, token);
        query.setFilter(filter);
        PreparedQuery preparedQuery = database.prepare(query);
        int size = preparedQuery.countEntities();
        if (size == 0)
            return "user doesn't exist";

        String text = req.getParameter(APIStrings.TEXT);
        String answers = req.getParameter(APIStrings.ANSWERS);
        String country = req.getParameter(APIStrings.COUNTRY);
        String type = req.getParameter(APIStrings.TYPE);

        // change db name
        Entity entity = new Entity(DBStrings.USER_QUESTIONS);
        entity.setProperty(APIStrings.TOKEN, token);
        entity.setProperty(APIStrings.TEXT, text);
        entity.setProperty(APIStrings.ANSWERS, answers);
        entity.setProperty(APIStrings.COUNTRY, country);
        entity.setProperty(APIStrings.TYPE, type);
        database.put(entity);

        return APIStrings.OK;
    }

    // return error message - if value equal "null" no error
    protected static String processUserRegister(HttpServletRequest req, DatastoreService database){

        String imei  = req.getParameter(APIStrings.IMEI);
        String email = req.getParameter(APIStrings.EMAIL);
        /*
        Query query = new Query(DBStrings.USERS);
        Query.Filter filter1 = new Query.FilterPredicate(APIStrings.IMEI, Query.FilterOperator.EQUAL, imei);
        Query.Filter filter2 = new Query.FilterPredicate(APIStrings.EMAIL, Query.FilterOperator.EQUAL,email);
        Query.Filter filter  = Query.CompositeFilterOperator.and(filter1, filter2);
        query.setFilter(filter);
        PreparedQuery preparedQuery = database.prepare(query);
        int size = preparedQuery.countEntities();
        if (size != 0)
                return "user already exists";
        */
        String device = req.getParameter(APIStrings.DEVICE);
        String api    = req.getParameter(APIStrings.ANDROID);
        String date  = sdm.format(new Date());
        String token = UUID.randomUUID().toString();

        Entity entity = new Entity(DBStrings.USERS);
        entity.setProperty(APIStrings.TOKEN, token);
        entity.setProperty(APIStrings.IMEI,  imei);
        entity.setProperty(APIStrings.EMAIL, email);
        entity.setProperty(APIStrings.DEVICE, device);
        entity.setProperty(APIStrings.ANDROID, api);
        entity.setProperty(APIStrings.REG_TIME, date);
        database.put(entity);

        return token;
        //return APIStrings.OK;
    }
}

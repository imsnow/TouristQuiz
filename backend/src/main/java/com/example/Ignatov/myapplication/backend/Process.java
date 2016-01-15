package com.example.Ignatov.myapplication.backend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Leva on 14.01.2016.
 *
 */
public class Process {

    private HttpServletRequest request;
    private DatastoreService database;

    private static final SimpleDateFormat sessionSDM = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private static final SimpleDateFormat regSDM = new SimpleDateFormat("dd MMM YYYY, HH:mm", Locale.getDefault());

    public Process(HttpServletRequest request) {
        this.request = request;
        database = DatastoreServiceFactory.getDatastoreService();
    }

    protected boolean userRegister(JSONObject json) {

        String imei = request.getParameter(APIStrings.IMEI);
        if(imei == null) return false;
        String email = request.getParameter(APIStrings.EMAIL);
        if(email == null) return false;

        String device = request.getParameter(APIStrings.DEVICE);
        if(device == null) return false;
        String api = request.getParameter(APIStrings.ANDROID);
        if(api == null) return false;

        String date = regSDM.format(new Date());

        Entity entity = searchUser(imei, email);
        if (entity == null) {
            // new user
            String token = UUID.randomUUID().toString();

            entity = new Entity(DBStrings.USERS);
            entity.setProperty(APIStrings.TOKEN, token);
            entity.setProperty(APIStrings.IMEI, imei);
            entity.setProperty(APIStrings.EMAIL, email);
            entity.setProperty(APIStrings.DEVICE, device);
            entity.setProperty(APIStrings.ANDROID, api);
            entity.setProperty(APIStrings.REG_TIME, date);
            database.put(entity);
        } else {
            // update user
            entity.setProperty(APIStrings.DEVICE, device);
            entity.setProperty(APIStrings.ANDROID, api);
            entity.setProperty(APIStrings.REG_TIME, date);
            database.put(entity);
        }

        json.put(APIStrings.TOKEN, entity.getProperty(APIStrings.TOKEN));
        return true;
    }

    //
    protected boolean userSession(JSONObject json){

        String token = getParameter(request, APIStrings.TOKEN, json);
        if(token == null) return false;

        Entity entity = searchUser(token);
        if(entity == null){
            json.put(APIStrings.MESSAGE, "User doesn't exits");
            return false;
        }

        String session = getParameter(request, APIStrings.SESSION, json);
        if(session == null) return false;

        long sessionTime = Long.parseLong(session);
        Date date = new Date(sessionTime);
        String sSessionTime = sessionSDM.format(date);

        // DBStrings String SESSIONS = "user_sessions";
        entity = new Entity(DBStrings.SESSIONS);
        entity.setProperty(APIStrings.TOKEN, token);
        entity.setProperty(APIStrings.DATE, sSessionTime);
        entity.setProperty(APIStrings.LONG, sessionTime);
        database.put(entity);

        return true;
    }

    protected boolean userQuestion(JSONObject json){

        String token = getParameter(request, APIStrings.TOKEN, json);
        if(token == null) return false;

        Entity entity = searchUser(token);
        if(entity == null){
            json.put(APIStrings.MESSAGE, "User doesn't exits");
            return false;
        }

        String text = getParameter(request, APIStrings.TEXT, json);
        if(text == null) return false;

        String answers = request.getParameter(APIStrings.ANSWERS);
        String country = request.getParameter(APIStrings.COUNTRY);
        String type    = request.getParameter(APIStrings.TYPE);

        entity = new Entity(DBStrings.USER_QUESTIONS);
        entity.setProperty(APIStrings.TOKEN, token);
        entity.setProperty(APIStrings.TEXT, text);
        entity.setProperty(APIStrings.ANSWERS, answers);
        entity.setProperty(APIStrings.COUNTRY, country);
        entity.setProperty(APIStrings.TYPE, type);
        database.put(entity);

        // all ok
        return true;
        //RespondBuilder.makeSuccess(String method, resp.getWriter());
    }


    private String getParameter(HttpServletRequest req, String name, JSONObject json){
        String param = req.getParameter(name);
        if(param == null || param.length() == 0){
            // param error
            json.put(APIStrings.MESSAGE, "There is error in parameter " + name);
            //RespondBuilder.makeError(method, resp.getWriter(), "There is error in parameter " + name);
            return null;
        }

        return param;
    }

    // get user entity from db
    private Entity searchUser(String token){

        if(token == null || token.length() == 0)
            return null;

        Query.Filter filter = new Query.FilterPredicate(APIStrings.TOKEN,
                Query.FilterOperator.EQUAL,
                token);

        Query q = new Query(DBStrings.USERS);
        q.setFilter(filter);
        PreparedQuery pq = database.prepare(q);

        return pq.asSingleEntity();
    }

    private Entity searchUser(String imei, String email){

        if(imei == null || imei.length() == 0 ||
                email == null || email.length() == 0)
            return null;

        Query query = new Query(DBStrings.USERS);
        Query.Filter filter1 = new Query.FilterPredicate(APIStrings.IMEI, Query.FilterOperator.EQUAL, imei);
        Query.Filter filter2 = new Query.FilterPredicate(APIStrings.EMAIL, Query.FilterOperator.EQUAL,email);
        Query.Filter filter  = Query.CompositeFilterOperator.and(filter1, filter2);
        query.setFilter(filter);
        PreparedQuery preparedQuery = database.prepare(query);

        return preparedQuery.asSingleEntity();
    }
}

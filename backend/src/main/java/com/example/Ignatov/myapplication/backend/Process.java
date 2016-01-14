package com.example.Ignatov.myapplication.backend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Leva on 14.01.2016.
 *
 */
public class Process {

    private HttpServletRequest request;
    private DatastoreService database;

    public Process(HttpServletRequest request) {
        this.request = request;
        database = DatastoreServiceFactory.getDatastoreService();
    }

    protected boolean processUserQuestion(JSONObject json){

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
}

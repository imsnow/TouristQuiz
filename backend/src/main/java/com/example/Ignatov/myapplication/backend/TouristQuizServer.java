package com.example.Ignatov.myapplication.backend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ignatov on 20.10.2015.
 */
public class TouristQuizServer {

    protected static final String UNTREATED_QUIZZES = "UntreatedQuizzes";

    protected static final String TYPE = "application/json; charset=UTF-8";

    protected static void processUserQuiz(HttpServletRequest req){

        DatastoreService database = DatastoreServiceFactory.getDatastoreService();

        String text = req.getParameter("text");
        String answers = req.getParameter("answers");
        String country = req.getParameter("country");

        Entity entity = new Entity(UNTREATED_QUIZZES);
        entity.setProperty("text", text);
        entity.setProperty("answers", answers);
        entity.setProperty("country", country);
        database.put(entity);

    }
}

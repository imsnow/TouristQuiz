package com.example.Ignatov.myapplication.backend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ignatov on 20.10.2015.
 *
 */
public class TouristQuizServer {

    protected static final String UNTREATED_QUIZZES = "UntreatedQuizzes";

    protected static final String TYPE = "application/json; charset=UTF-8";

    private static final SimpleDateFormat sdm = new SimpleDateFormat("dd MMM YYYY, HH:mm");

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

    // TODO check that user don't exist
    protected static void processUserRegister(HttpServletRequest req){

        DatastoreService database = DatastoreServiceFactory.getDatastoreService();

        String email = req.getParameter(APIStrings.EMAIL);
        String date  = sdm.format(new Date(System.currentTimeMillis()));

        Entity entity = new Entity(DBStrings.USERS);
        entity.setProperty(APIStrings.EMAIL, email);
        entity.setProperty(APIStrings.REG_TIME, date);
        database.put(entity);
    }
}

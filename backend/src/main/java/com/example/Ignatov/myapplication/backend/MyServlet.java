/*
   Project: Tourist Quiz
   Author: Ignatov Mike
   e-mail: ruwinmike@gmail.com
*/

package com.example.Ignatov.myapplication.backend;


import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {

    //private static final String USER_QUIZ = "user.quiz";
    //private static final String UPDATE_BASE = "db.update";
    //private static final String RATING = "user.rating";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String method = req.getParameter(APIStrings.METHOD);
        resp.setContentType(TouristQuizServer.TYPE);

        // if method == null that ignore request
        if(method != null){

            RespondBuilder respondBuilder = new RespondBuilder(method, resp.getWriter());
            Process process = new Process(req);
            JSONObject json = new JSONObject();

            if(method.equals(APIStrings.USER_QUESTION)){
                if(process.processUserQuestion(json))
                    respondBuilder.makeSuccess();
                else respondBuilder.makeError(json);
                //TouristQuizServer.processUserQuiz(req, database);
                //RespondBuilder.makeSuccess(method, resp.getWriter());
                return;
            }
            /*
            if(method.equals(APIStrings.USER_REGISTER)) {
                String token = TouristQuizServer.processUserRegister(req, database);
                RespondBuilder.makeSuccessToken(method, resp.getWriter(), token);
                /*String result = TouristQuizServer.processUserRegister(req, database);
                if (result.equals(APIStrings.OK))
                    RespondBuilder.makeSuccess(method, resp.getWriter());
                else
                    RespondBuilder.makeError(method, resp.getWriter(), result);

                return;
            }
            if(method == UPDATE_BASE) {
                // TODO
                return;
            }
            if(method == RATING) {
                // TODO
                return;
            }
            */
            // This way when method is unknown or null
            respondBuilder.makeUnknownMethod();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {
            resp.getWriter().println("Please enter a name");
        }
        resp.getWriter().println("Hello " + name);
    }
}

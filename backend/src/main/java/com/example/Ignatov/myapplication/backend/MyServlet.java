/*
   Project: Tourist Quiz
   Author: Ignatov Mike
   e-mail: ruwinmike@gmail.com
*/

package com.example.Ignatov.myapplication.backend;


import java.io.IOException;

import javax.servlet.http.*;

public class MyServlet extends HttpServlet {

    private static final String USER_QUIZ = "user.quiz";
    private static final String ERROR = "error";
    private static final String UPDATE_BASE = "db.update";
    private static final String RATING = "user.rating";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String method = req.getParameter("method");
        resp.setContentType(TouristQuizServer.TYPE);

        if(method != null){
            if(method.equals(USER_QUIZ)){
                TouristQuizServer.processUserQuiz(req);
                RespondBuilder.makeSuccess(method, resp.getWriter());
                return;
            }
            if(method == ERROR) {
                // TODO
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

        }
        // This way when method is unknown or null
        RespondBuilder.makeFailure(method, resp.getWriter());
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

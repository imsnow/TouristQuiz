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

    protected static final String TYPE = "application/json; charset=UTF-8";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String method = req.getParameter(APIStrings.METHOD);
        resp.setContentType(TYPE);

        // if method == null that ignore request
        if(method != null){

            RespondBuilder respondBuilder = new RespondBuilder(method, resp.getWriter());
            Process process = new Process(req);
            JSONObject json = new JSONObject();
            boolean status = false; // success or fail

            if(method.equals(APIStrings.USER_QUESTION)){
                status = process.userQuestion(json);
            }

            if(method.equals(APIStrings.USER_REGISTER)) {
                status = process.userRegister(json);
            }

            if(method.equals(APIStrings.USER_SESSION)) {
                status = process.userSession(json);
            }

            if(method.equals(APIStrings.USER_RESULT)){
                status = process.userResult(json);
            }
            /*
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
            respondBuilder.make(json, status);
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

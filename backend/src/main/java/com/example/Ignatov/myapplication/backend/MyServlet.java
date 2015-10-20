/*
   Project: Tourist Quiz
   Author: Ignatov Mike
   e-mail: ruwinmike@gmail.com
*/

package com.example.Ignatov.myapplication.backend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import java.io.IOException;

import javax.servlet.http.*;

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        String method = req.getParameter("method");

        if(method != null){
            resp.setContentType(TouristQuizServer.TYPE);
            RespondBuilder.make().ok(method).write(resp.getWriter());
            resp.getWriter().println("Method = " + method);
        }
        else {
            resp.setContentType(TouristQuizServer.TYPE);
            resp.getWriter().println("Error Method = " + method);
        }


        //resp.setContentType(TouristQuizServer.TYPE);
        //resp.getWriter().println("Please use the form to POST to this url");
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

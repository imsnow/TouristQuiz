package com.example.Ignatov.myapplication.backend;

import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by Ignatov on 20.10.2015.
 */
public class RespondBuilder {

    private static final String STATUS = "status";
    private static final String METHOD = "method";

    protected static void makeFailure(String method, PrintWriter writer){
        JSONObject json = new JSONObject();
        json.put(STATUS, "fail");
        json.put(METHOD, "Unknown method = " + method);
        json.write(writer);
    }

    protected static void makeSuccess(String method,PrintWriter writer){
        JSONObject json = new JSONObject();
        json.put("status", "ok");
        json.put("method", method);
        json.write(writer);
    }
}

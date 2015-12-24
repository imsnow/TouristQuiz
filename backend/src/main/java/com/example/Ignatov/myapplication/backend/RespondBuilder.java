package com.example.Ignatov.myapplication.backend;

import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by Ignatov on 20.10.2015.
 *
 */
public class RespondBuilder {

    protected static void makeFailure(String method, PrintWriter writer){
        JSONObject json = new JSONObject();
        json.put(APIStrings.STATUS, "fail");
        json.put(APIStrings.METHOD, "Unknown method = " + method);
        json.write(writer);
    }

    protected static void makeSuccess(String method,PrintWriter writer){
        JSONObject json = new JSONObject();
        json.put("status", "ok");
        json.put("method", method);
        json.write(writer);
    }
}

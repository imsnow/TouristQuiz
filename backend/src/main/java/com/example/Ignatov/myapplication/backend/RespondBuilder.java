package com.example.Ignatov.myapplication.backend;

import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by Ignatov on 20.10.2015.
 *
 */
public class RespondBuilder {

    protected static void makeError(String method, PrintWriter writer, String message){
        JSONObject json = new JSONObject();
        json.put(APIStrings.STATUS, APIStrings.ERROR);
        json.put(APIStrings.METHOD, method);
        json.put(APIStrings.MESSAGE, message);
        json.write(writer);
    }

    protected static void makeSuccess(String method,PrintWriter writer){
        JSONObject json = new JSONObject();
        json.put(APIStrings.STATUS, APIStrings.OK);
        json.put(APIStrings.METHOD, method);
        json.write(writer);
    }

    protected static void makeUnkownMethod(String method, PrintWriter writer){
        makeError(method, writer, "Unknown method");
    }
}

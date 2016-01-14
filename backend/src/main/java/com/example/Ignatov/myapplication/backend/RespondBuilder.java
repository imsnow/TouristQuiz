package com.example.Ignatov.myapplication.backend;

import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by Ignatov on 20.10.2015.
 *
 */
public class RespondBuilder {

    private String method;
    private PrintWriter writer;

    public RespondBuilder(String method, PrintWriter writer){
        this.method = method;
        this.writer = writer;
    }

    // json object already has message
    protected void makeError(JSONObject json){
        json.put(APIStrings.STATUS, APIStrings.ERROR);
        json.put(APIStrings.METHOD, method);
        json.write(writer);
    }

    protected void makeError(String message){
        JSONObject json = new JSONObject();
        json.put(APIStrings.STATUS, APIStrings.ERROR);
        json.put(APIStrings.METHOD, method);
        json.put(APIStrings.MESSAGE, message);
        json.write(writer);
    }

    protected void makeSuccess(){
        JSONObject json = new JSONObject();
        json.put(APIStrings.STATUS, APIStrings.OK);
        json.put(APIStrings.METHOD, method);
        json.write(writer);
    }

    protected void makeSuccessToken(String token){
        JSONObject json = new JSONObject();
        json.put(APIStrings.STATUS, APIStrings.OK);
        json.put(APIStrings.METHOD, method);
        json.put(APIStrings.TOKEN, token);
        json.write(writer);
    }

    protected void makeUnknownMethod(){
        makeError("Unknown method");
    }
}

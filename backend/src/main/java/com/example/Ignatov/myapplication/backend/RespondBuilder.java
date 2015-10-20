package com.example.Ignatov.myapplication.backend;

import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by Ignatov on 20.10.2015.
 */
public class RespondBuilder {
    /*
    private JSONObject json;

    private String header =
            "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n";
    private String footer = "</rsp>";

    private RespondBuilder(){
        //json = new JSONObject();
    }

    // This method must be first
    protected static RespondBuilder make(){
        RespondBuilder builder = new RespondBuilder();
        return builder;
    }

    protected RespondBuilder ok(String method) {
        this.header += "<rsp status=\"ok\">\n";
        this.header += "\t<method>" + method + "</method>\n";
        //json.put("status", "ok");
        //json.put("method", method);
        return this;
    }

    protected void write(PrintWriter writer){
        json.write(writer);
    }
    */

    protected static void makeSuccess(String method,PrintWriter writer){
        JSONObject json = new JSONObject();
        json.put("status", "ok");
        json.put("method", method);
        json.write(writer);
    }
}

package ru.mishaignatov.touristquiz.data;

import org.json.JSONObject;

/**
 * Created by Ignatov on 13.08.2015.
 * Storage for specific country
 */
public class Country {

    private String name;
    private int answered;
    private int total;
    //private boolean isClosed = true;

    public Country(String name, int answered, int total){
        this.name = name;
        this.answered = answered;
        this.total = total;
    }

    public String getName() { return name; }
    public int getAnswered(){ return answered; }
    public int getTotal()   { return total; }

    public boolean isFinished(){
        return answered == total;
    }
}

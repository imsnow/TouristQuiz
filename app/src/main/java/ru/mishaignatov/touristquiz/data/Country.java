package ru.mishaignatov.touristquiz.data;

/**
 * Created by Ignatov on 13.08.2015.
 */
public class Country {

    private String name;
    private int answered;
    private int total;
    private boolean isClosed = true;

    public Country(String name, int total){
        this.name = name;
        this.total = total;
    }

    public String getName() { return name; }
    public int getAnswered(){ return answered; }
    public int getTotal()   { return total; }
}

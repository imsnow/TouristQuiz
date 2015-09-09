package ru.mishaignatov.touristquiz.data;

/**
 * Created by Ignatov on 13.08.2015.
 */
public class Country {

    private String name;
    private int no_answered;
    private int total;
    private boolean isClosed = true;

    public Country(String name, int no_answered, int total){
        this.name = name;
        this.no_answered = no_answered;
        this.total = total;
    }

    public String getName() { return name; }
    public int getNoAnswered(){ return no_answered; }
    public int getTotal()   { return total; }

    public boolean isFinished(){
        return no_answered==0;
    }
}

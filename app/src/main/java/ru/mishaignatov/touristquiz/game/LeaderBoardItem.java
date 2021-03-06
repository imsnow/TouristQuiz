package ru.mishaignatov.touristquiz.game;

/**
 * Created by Mike on 03.02.2016.
 **/
public class LeaderBoardItem implements Comparable<LeaderBoardItem> {

    //private int place;
    private String name;
    private int scores;

    public String getName() {
        return name;
    }

    //public int getPlace() {
    //    return place;
    //}

    public int getScores() {
        return scores;
    }

    public LeaderBoardItem(String name, /*int place,*/ int scores) {
        this.name = name;
        //this.place = place;
        this.scores = scores;
    }


    @Override
    public int compareTo(LeaderBoardItem another) {
        if (scores >= another.getScores())
            return -1;
        else
            return 1;
    }
}

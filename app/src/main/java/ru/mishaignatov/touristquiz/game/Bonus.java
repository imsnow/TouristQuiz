package ru.mishaignatov.touristquiz.game;

import ru.mishaignatov.touristquiz.R;

/***
 * Created by Mike on 22.04.2016.
 */
public class Bonus  {

    public enum Type { BY_TWO, BY_THREE}

    private Type type;

    private String title;

    private int resource_color;

    public Bonus(Type type) {
        this.type = type;
        switch (type) {
            case BY_TWO:
                title = "x2";
                resource_color = R.color.by_two_achieve;
                break;
            case BY_THREE:
                title = "x3";
                resource_color = R.color.by_three_achieve;
                break;
        }
    }

    public String getTitle() {return title;}
    public int getResourceColor() { return resource_color; }
    public Type getType() { return type; }
}

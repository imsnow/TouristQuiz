package ru.mishaignatov.touristquiz.game;

import ru.mishaignatov.touristquiz.R;

/***
 * Created by Mike on 22.04.2016.
 */
public class Bonus  {

    public enum Type { BY_TWO, BY_THREE}

    private Type type;

    private String title;

    private int drawable;
    private int text_color = R.color.by_three_achieve;

    public Bonus(Type type) {
        this.type = type;
        switch (type) {
            case BY_TWO:
                title = "x2";
                drawable = R.drawable.dialog_title_yellow;
                break;
            case BY_THREE:
                title = "x3";
                drawable = R.drawable.dialog_title_orange;
                break;
        }
    }

    public String getTitle() {return title;}
    public int getResourceColor() { return drawable; }
    public int getTextColor() { return text_color; }
    public Type getType() { return type; }
}

package ru.mishaignatov.touristquiz.data;

/**
 * Created by Ignatov Misha on 02.08.15.
 *
 *
 */
public class Quiz {

    private String quizText;
    private String strAnswers;
    private int type;

    public Quiz(final String text, final String answers, int type){
        quizText = text;
        strAnswers = answers;
        this.type = type;
    }

    public String getText()                   {   return quizText;   }
    public int getType()                      {   return type;       }
    public String getStringAnswers()          {   return strAnswers; }
    //public boolean isAnswered()               {   return is_answered;}

    public String[] getListAnswers(){
        return strAnswers.trim().split(",");
    }
}

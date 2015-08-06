package ru.mishaignatov.touristquiz.data;

import ru.mishaignatov.touristquiz.Utils;

/**
 * Created by Ignatov Misha on 02.08.15.
 *
 *
 */
public class Quiz {

    private String quizText;
    private String strAnswers;
    private String type;

    public Quiz(final String text, final String answers, final String type){
        /*
        int type = 0;

        switch (arr[3]){
            case "lions": type = QuizStorage.SIGHT; break;
            case "kitchen": type = QuizStorage.KITCHEN; break;
            case "geo": type = QuizStorage.GEO; break;
            case "history": type = QuizStorage.HISTORY; break;
        }
        */
        quizText = text;
        strAnswers = answers;
        this.type = type;
    }

    public Quiz(final String[] arr){
        this(arr[0], arr[1], arr[2]);
    }

    public String getText()                   {   return quizText;   }
    public String getType()                   {   return type;       }
    public String getStringAnswers()          {   return strAnswers; }
    //public boolean isAnswered()               {   return is_answered;}

    public String[] getListAnswers(){
        return strAnswers.split(",");
    }

    public String[] getRandomListAnswers(){
        return Utils.shuffleStringArray(getListAnswers());
    }

    public static boolean isAnswer(final Quiz quiz, final String choice){
        String answer = quiz.getListAnswers()[0].trim();
        return answer.equals(choice);
    }
}

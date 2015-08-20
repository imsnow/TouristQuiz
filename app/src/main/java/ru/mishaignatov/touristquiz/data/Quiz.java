package ru.mishaignatov.touristquiz.data;

import android.os.Parcel;
import android.os.Parcelable;

import ru.mishaignatov.touristquiz.Utils;

/**
 * Created by Ignatov Misha on 02.08.15.
 *
 *
 */
public class Quiz implements Parcelable {

    private String quizText;
    private String strAnswers;
    private String type;

    public Quiz(final String text, final String answers, final String type){

        quizText = text;
        strAnswers = answers;
        this.type = type;
    }

    public Quiz(final String[] arr){
        this(arr[0], arr[1], arr[2]);
    }

    public Quiz(Parcel parcel){
        this(parcel.readString(), parcel.readString(), parcel.readString());
    }

    public String getText()                   {   return quizText;   }
    public int getType()                   {
        switch (type) {
            case "lions":
                return 0;
            case "kitchen":
                return 1;
            case "geo":
                return 2;
            case "history":
                return 3;
        }
        return 0;
    }
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

    public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {

        @Override
        public Quiz createFromParcel(Parcel source) {
            return new Quiz(source);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quizText);
        dest.writeString(strAnswers);
        dest.writeString(type);
    }
}

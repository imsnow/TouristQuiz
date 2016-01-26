package ru.mishaignatov.touristquiz;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import java.util.Collections;
import java.util.List;

/**
 * Created by Ignatov Work on 06.08.2015.
 **/
public class Utils {
    /*
    // Алгоритм перемешивания Фишера–Йетса
    // https://ru.wikipedia.org/wiki/%D0%A2%D0%B0%D1%81%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5_%D0%A4%D0%B8%D1%88%D0%B5%D1%80%D0%B0%E2%80%93%D0%99%D0%B5%D1%82%D1%81%D0%B0
    // source http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
    public static String[] shuffleStringArray(String[] arr){
        Random r = new Random();
        for(int i=arr.length-1; i>0; i--){
            int index = r.nextInt(i+1);
            String s = arr[index];
            arr[index] = arr[i];
            arr[i] = s;
        }
        return arr;
    }
    */
    public static List<String> shuffleList(List<String> list){
        Collections.shuffle(list);
        return list;
    }

    public static String doAnswerString(final String str) {
        String trim = str.trim();
        return trim.substring(0,1).toUpperCase() + trim.substring(1).toLowerCase();
    }

    public static void setBackground(View view, Drawable drawable){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            view.setBackgroundDrawable(drawable);
        else
            view.setBackground(drawable);
    }
}

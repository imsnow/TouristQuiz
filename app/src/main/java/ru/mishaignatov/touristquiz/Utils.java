package ru.mishaignatov.touristquiz;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ignatov Work on 06.08.2015.
 **/
public class Utils {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float spToPx(float sp) {
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int)(sp * scaledDensity);
    }

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
        if(str.length()>1)
            return trim.substring(0,1).toUpperCase() + trim.substring(1);
        else
            return trim;
    }

    public static void setBackground(View view, Drawable drawable){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            view.setBackgroundDrawable(drawable);
        else
            view.setBackground(drawable);
    }

    public static Drawable loadBitmapFromAssets(Context context, String file) {
        InputStream is = null;
        //Bitmap bitmap = null;
        try {
            is = context.getAssets().open(file);
            //bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
            e.printStackTrace();
        }
        return new BitmapDrawable(context.getResources(), is);
    }
}

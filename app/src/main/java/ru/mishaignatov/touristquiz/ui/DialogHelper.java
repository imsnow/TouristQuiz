package ru.mishaignatov.touristquiz.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Ignatov on 26.08.2015.
 * Singleton
 * This class store all possible dialogs
 * All methods is static
 */
public class DialogHelper {

    public static void showDialogSuccess(Activity activity, DialogInterface.OnClickListener listener){

        new AlertDialog.Builder(activity)
                //.setTitle("Поздравляю!")
                .setCancelable(false)
                .setMessage("Поздравляю! Вы ответили правильно! Вы получаете 30 миль")
                .setPositiveButton("Отлично", listener)
                .show();
    }

    public static void showDialogFailure(Activity activity, DialogInterface.OnClickListener listener){

        new AlertDialog.Builder(activity)
                //.setTitle("Жаль")
                .setCancelable(false)
                .setMessage("Жаль, но это не верный ответ. Попробуй еще раз!")
                .setPositiveButton("Продолжить", listener)
                .show();
    }

    public static void showDialogNextLevel(Activity activity, DialogInterface.OnClickListener listener){

        new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setMessage("Обалдеть! Вы прошли весь уровень! Попробуй свои силы в других странах!")
                .setNegativeButton("Попробовать", listener)
                .show();
    }

    public static void showDialogLevelFinished(Activity activity) {

        new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setMessage("Вы уже ответили на все вопросы этого уровня")
                .setPositiveButton("Хорошо", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}

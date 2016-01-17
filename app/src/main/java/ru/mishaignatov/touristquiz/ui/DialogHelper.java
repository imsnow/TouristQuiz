package ru.mishaignatov.touristquiz.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import ru.mishaignatov.touristquiz.R;

/**
 * Created by Ignatov on 26.08.2015.
 * Singleton
 * This class store all possible dialogs
 * All methods is static
 */
public class DialogHelper {

    public static void showUserQuestion(Activity activity, DialogInterface.OnClickListener listener){

        if(activity != null && listener != null){
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity)
                    .setTitle("Мой вопрос")
                    .setMessage("Загадай свой вопрос всему миру")
                    .setPositiveButton("Отправить", listener)
                    .setNegativeButton("Отмена", listener);

            final EditText question = new EditText(dialog.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            question.setLayoutParams(params);
            dialog.setView(question);
            dialog.show();
        }
    }


    public static void showDialogSuccess(Activity activity, int time, int score, int mile, DialogInterface.OnClickListener listener){

        if(activity != null && listener != null)
            new AlertDialog.Builder(activity)
                    //.setTitle("Поздравляю!")
                    .setCancelable(false)
                    .setMessage("Поздравляю! Вы ответили правильно за " + time
                            + " сек! Вы получаете " + score + " очков и " + mile + " миль")
                    .setPositiveButton("Отлично", listener)
                    .show();
    }

    public static void showDialogFailure(Activity activity, DialogInterface.OnClickListener listener){
        if(activity != null && listener != null)
            new AlertDialog.Builder(activity)
                    //.setTitle("Жаль")
                    .setCancelable(false)
                    .setMessage("Жаль, но это не верный ответ. Попробуй еще раз!")
                    .setPositiveButton("Продолжить", listener)
                    .show();
    }

    public static void showDialogNextLevel(Activity activity, DialogInterface.OnClickListener listener){
        if(activity != null && listener != null)
            new AlertDialog.Builder(activity)
                    .setCancelable(false)
                    .setMessage("Обалдеть! Вы прошли весь уровень! Попробуй свои силы в других странах!")
                    .setNegativeButton("Попробовать", listener)
                    .show();
    }

    public static void showDialogLevelFinished(Activity activity) {
        if(activity != null)
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

    public static void showDialogErrorInQuestion(Activity activity, DialogInterface.OnClickListener listener){
        if(activity != null && listener != null)
            new AlertDialog.Builder(activity)
                    .setCancelable(false)
                            .setMessage(R.string.dialog_error_text)
                    //.setView(R.layout.dialog_send_error)
                    .setPositiveButton(R.string.dialog_error_send, listener)
                    .setNegativeButton(R.string.dialog_error_cancel, listener)
                    .show();
    }
}

package ru.mishaignatov.touristquiz.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.data.Quiz;
import ru.mishaignatov.touristquiz.data.QuizStorage;

/**
 * Created by Ignatov Work on 05.08.2015.
 *
 * Display quiz
 */
public class QuizActivity extends Activity implements View.OnClickListener {

    private TextView quizText;
    private Button button1, button2, button3, button4;

    private Quiz currentQuiz = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizText = (TextView)findViewById(R.id.quiz_text);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        updateQuiz();
    }

    private void updateQuiz(){

        currentQuiz = QuizStorage.getQuiz();
        String[] list = currentQuiz.getRandomListAnswers();

        quizText.setText(currentQuiz.getText());
        button1.setText(list[0].trim());
        button2.setText(list[1].trim());
        button3.setText(list[2].trim());
        button4.setText(list[3].trim());
    }

    @Override
    public void onClick(View v) {

        String s = ((Button)v).getText().toString();

        boolean result = Quiz.isAnswer(currentQuiz, s);
        makeToast(result);

        updateQuiz();
    }

    private void makeToast(boolean isTrue){
        String s;
        if(isTrue) s = "Поздравляю! В выбрали правильный вариант =)";
        else s = "Жаль, но это не правильный ответ! =(";

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}

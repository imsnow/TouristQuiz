package ru.mishaignatov.touristquiz.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ru.mishaignatov.touristquiz.GameManager;
import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.orm.OrmDao;
import ru.mishaignatov.touristquiz.orm.Question;

/**
 * Created by Ignatov Work on 05.08.2015.
 *
 * Display quiz
 */
public class QuizActivity extends Activity implements View.OnClickListener, DialogInterface.OnClickListener {

    private TextView scoreText, quizText;
    private Button button1, button2, button3, button4;
    private RelativeLayout layout;

    private Question mCurrentQuestion = null;
    private int mCountryId;

    private int drawables[] = {R.drawable.lime100, R.drawable.deep_orange, R.drawable.green100, R.drawable.blue100};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        layout = (RelativeLayout)findViewById(R.id.quiz_layout);
        scoreText = (TextView)findViewById(R.id.score_text);
        TextView error = (TextView)findViewById(R.id.error_text);
        error.setOnClickListener(this);

        quizText = (TextView)findViewById(R.id.quiz_text);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        mCountryId = getIntent().getIntExtra("country_id", -1);

        update();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.error_text) { // send error
            DialogHelper.showDialogErrorInQuestion(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == -1) {// Send
                        //sendErrorEmail(currentQuiz);
                        Toast.makeText(getApplicationContext(), "You can send message in next version", Toast.LENGTH_LONG).show();
                    }
                    if(which == -2) { // cancel
                        dialog.dismiss();
                    }
                }
            });
        }
        else { // One of fourth answers buttons
            String s = ((Button) v).getText().toString();

            boolean result = Question.isAnswer(mCurrentQuestion, s);
            if (result) {
                //App.userAnsweredTrue();
                DialogHelper.showDialogSuccess(this, this);
                OrmDao.getInstance(this).setQuestionAnswered(mCurrentQuestion);
            } else
                DialogHelper.showDialogFailure(this, this);
        }
    }

    private void updateQuiz(){

        mCurrentQuestion = GameManager.getInstance(this).getQuestion(mCountryId);

        if(mCurrentQuestion == null) { // Вопросы по этой стране закончились
            DialogHelper.showDialogNextLevel(this, this);
            return;
        }

        String[] list = mCurrentQuestion.getRandomListAnswers();

        quizText.setText(mCurrentQuestion.quiz);
        button1.setText(list[0].trim());
        button2.setText(list[1].trim());
        button3.setText(list[2].trim());
        button4.setText(list[3].trim());

        layout.setBackgroundResource(drawables[mCurrentQuestion.getType()]);
    }

    public void update(){
        updateQuiz();
        // scoreText.setText(String.valueOf(App.getScore()));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d("TAG", "DialogInterface = " + which);
        if(which == -1) // Success and failure dialog
            update();
        if(which == -2) // Next Level Dialog
            finish();
    }
    /*
    private void sendErrorEmail(final Quiz quiz){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"ruwinmike@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Tourist Quiz");
        i.putExtra(Intent.EXTRA_TEXT, "Quiz = " + quiz.getText() + "\n"
                                    + "Answers = " + quiz.getStringAnswers() + "\n");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
    */
}

package ru.mishaignatov.touristquiz.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import ru.mishaignatov.touristquiz.GameManager;
import ru.mishaignatov.touristquiz.HeaderInterface;
import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.orm.OrmDao;
import ru.mishaignatov.touristquiz.orm.Question;

/**
 * Created by Leva on 22.12.2015.
 *
 */
public class QuestionFagment extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {

    private TextView questionText;
    private Button button1, button2, button3, button4;
    private FrameLayout layout;

    private Question mCurrentQuestion;

    private HeaderInterface headerInterface;

    // temp
    private int drawables[] = {R.drawable.lime100, R.drawable.deep_orange, R.drawable.green100, R.drawable.blue100};

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        headerInterface = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_question, container, false);

        TextView error = (TextView)v.findViewById(R.id.error_text);
        error.setOnClickListener(this);

        layout = (FrameLayout)v.findViewById(R.id.layout);

        questionText = (TextView)v.findViewById(R.id.quiz_text);
        button1      = (Button)v.findViewById(R.id.button1);
        button2      = (Button)v.findViewById(R.id.button2);
        button3      = (Button)v.findViewById(R.id.button3);
        button4      = (Button)v.findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        update();

        return v;
    }

    public void update(){
        updateQuestion();
        headerInterface.onUpdateHeader("");
    }

    private void updateQuestion(){

        mCurrentQuestion = GameManager.getInstance(getActivity()).getQuestion();

        if(mCurrentQuestion == null) { // Вопросы по этой стране закончились
            DialogHelper.showDialogNextLevel(getActivity(), this);
            return;
        }

        String[] list = mCurrentQuestion.getRandomListAnswers();

        questionText.setText(mCurrentQuestion.quiz);
        button1.setText(list[0].trim());
        button2.setText(list[1].trim());
        button3.setText(list[2].trim());
        button4.setText(list[3].trim());

        layout.setBackgroundResource(drawables[mCurrentQuestion.getType()]);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.error_text) { // send error
            DialogHelper.showDialogErrorInQuestion(getActivity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == -1) {// Send
                        //sendErrorEmail(currentQuiz);
                        Toast.makeText(getActivity(), "You can send message in next version", Toast.LENGTH_LONG).show();
                    }
                    if(which == -2) { // cancel
                        dialog.dismiss();
                    }
                }
            });
        }
        else if (v instanceof Button){ // One of fourth answers buttons
            String s = ((Button) v).getText().toString();

            boolean result = Question.isAnswer(mCurrentQuestion, s);
            if (result) {
                //App.userAnsweredTrue();
                DialogHelper.showDialogSuccess(getActivity(), this);
                OrmDao.getInstance(getActivity()).setQuestionAnswered(mCurrentQuestion);
            } else
                DialogHelper.showDialogFailure(getActivity(), this);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d("TAG", "DialogInterface = " + which);
        if(which == -1) // Success and failure dialog
            update();
        if(which == -2) // Next Level Dialog
            //finish()
            ;
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

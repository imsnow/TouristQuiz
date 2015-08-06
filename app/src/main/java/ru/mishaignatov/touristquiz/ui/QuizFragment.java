package ru.mishaignatov.touristquiz.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mishaignatov.touristquiz.R;

/**
 * Created by Ignatov on 05.08.2015.
 */
public class QuizFragment extends Fragment {

    public QuizFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }
}

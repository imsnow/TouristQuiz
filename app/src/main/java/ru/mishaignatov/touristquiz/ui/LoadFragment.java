package ru.mishaignatov.touristquiz.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import ru.mishaignatov.touristquiz.R;

/**
 * Created by Leva on 22.12.2015.
 * This fragment load bd and another resources
 */
public class LoadFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_load, container, false);

        new ImitationAsyncTask().execute();

        return v;
    }


    private class ImitationAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Imitation of db loading
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ((MainActivity)getActivity()).changeFragment(new StartFragment());
        }
    }
}

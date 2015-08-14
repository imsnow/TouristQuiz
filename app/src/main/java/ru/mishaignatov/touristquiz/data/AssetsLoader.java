package ru.mishaignatov.touristquiz.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ignatov on 14.08.2015.
 * Singletone
 */
public class AssetsLoader {

    private static final String TAG = "AssetsLoader";

    private static AssetsLoader loader;

    private static final String COUNTRY_DIR = "countries";

    private AssetsLoader(Context context, CountryStorage storage){
        // TODO it must doing in another thread
        AssetManager assetManager = context.getAssets();
        load(assetManager, COUNTRY_DIR, storage);
    }

    public static AssetsLoader getLoader(Context context, CountryStorage storage){
        if(loader == null) loader = new AssetsLoader(context, storage);
        return loader;
    }


    private void load(AssetManager assetManager, String path, CountryStorage storage){
        String[] list;
        try {
            list = assetManager.list(path);
            if(list.length > 0){
                for(String name : list)
                    //Log.d(TAG, s);
                    loadCountries(assetManager, path + "/" + name, storage);
            }
        } catch (IOException e) {
            Log.d(TAG, "Error read list of files");
            e.printStackTrace();
        }
    }

    private void loadCountries(AssetManager manager, String filename, CountryStorage storage){
        String title = filename; //.split('.'))[0]; // name is filename till '.'
        int cnt = 0;
        try {
            InputStream is = manager.open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            while((reader.readLine()) != null) {
                cnt++;
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Country country = new Country(title, cnt);
        storage.addCountry(country);

        Log.d(TAG, "Title = " + title + " total = " + cnt);
    }
}

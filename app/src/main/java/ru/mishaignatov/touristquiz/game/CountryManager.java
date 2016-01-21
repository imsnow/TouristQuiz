package ru.mishaignatov.touristquiz.game;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.mishaignatov.touristquiz.orm.Country;
import ru.mishaignatov.touristquiz.orm.OrmDao;

/**
 * Created by Leva on 21.01.2016.
 **/
public class CountryManager {

    private static CountryManager instance;

    private List<Country> mCountryList;
    private Context mContext;

    private CountryManager(Context context){
        mCountryList = new ArrayList<>();
        mContext = context;
        fillCountryList();
    }

    public static CountryManager getInstance(Context context){
        if(instance == null) instance = new CountryManager(context);
        return instance;
    }

    public List<Country> getList() {
        return mCountryList;
    }

    private void fillCountryList(){
        mCountryList = OrmDao.getInstance(mContext).getCountryList();
    }

    public Country getCountry(int position) {
        return mCountryList.get(position);
    }

    public void updateCountry(int position){
        Log.d("TAG", "pre size = " + mCountryList.size());
        Country item = mCountryList.get(position);
        item = OrmDao.getInstance(mContext).updateCountry(item);
        mCountryList.add(position,item);
        Log.d("TAG", "post size = " + mCountryList.size());
    }
}

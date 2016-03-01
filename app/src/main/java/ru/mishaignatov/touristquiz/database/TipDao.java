package ru.mishaignatov.touristquiz.database;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import ru.mishaignatov.touristquiz.R;

/**
 * Created by Mike on 01.03.2016.
 *
 */
public class TipDao {

    private static TipDao instance = null;
    private Context mContext;

    private RuntimeExceptionDao<Tip, Integer> mTipDao;

    public static TipDao getInstance(DbHelper helper, Context context){
        if(instance == null) instance = new TipDao(helper, context);
        return instance;
    }
    private TipDao(DbHelper helper, Context context){
        mTipDao = helper.getRuntimeExceptionDao(Tip.class);
        mContext = context;
    }

    // TIPS
    public int createTipsEntries(){
        int count = 0;

        String[] tipsArr = mContext.getResources().getStringArray(R.array.tips);

        for(int i = 0; i<tipsArr.length; i++){
            Tip tip = new Tip();
            tip.id   = i;
            tip.text = tipsArr[i];
            mTipDao.create(tip);
            count++;
        }
        return count;
    }

    public List<Tip> getTipsList(){
        return mTipDao.queryForAll();
    }
}

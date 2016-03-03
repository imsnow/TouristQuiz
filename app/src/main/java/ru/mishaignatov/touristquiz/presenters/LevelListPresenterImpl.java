package ru.mishaignatov.touristquiz.presenters;

import android.content.Context;

import java.util.List;

import ru.mishaignatov.touristquiz.database.Level;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.User;
import ru.mishaignatov.touristquiz.ui.views.LevelListView;

/***
 * Created by Mike on 16.02.2016.
 */
public class LevelListPresenterImpl implements LevelListPresenter {

    private LevelListView view;
    private List<Level> mList;
    private Context mContext;

    public LevelListPresenterImpl(Context context, LevelListView view){
        this.view = view;
        mList = App.getDbHelper().getLevelDao().getLevelList();
        mContext = context;
    }

    @Override
    public List<Level> getLevelList() {
        return mList;
    }

    @Override
    public Level getLevel(int position) {
        return mList.get(position);
    }

    @Override
    public void onListItemClick(int position) {

        Level level = mList.get(position);

        if (level.is_opened) {
            //if (OrmDao.getInstance(mContext).isCountryShown(position))
            if (level.is_ended)
                view.showDialogLevelFinished();
            else
                view.startLevel(position);
        }
        else view.showClosedLevel(position);
    }

    @Override
    public void onBuyLevel(int position) {

        User user = GameManager.getInstance(mContext).getUser();
        Level level = mList.get(position);

        if (user.getMillis() < level.cost)
            // show message
            view.showNotEnoughMillis();
        else {
            // buy ticket
            user.buyCountry(level.cost);
            App.getDbHelper().getLevelDao().openLevel(level);
            view.startLevel(position);
        }
    }

    @Override
    public void updateLevels() {
        for(int i=0; i<mList.size(); i++)
            updateLevel(i);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    public void updateLevel(int position){
        Level item = mList.remove(position);
        item = App.getDbHelper().getLevelDao().updateLevel(item);
        mList.add(position, item);
    }

}

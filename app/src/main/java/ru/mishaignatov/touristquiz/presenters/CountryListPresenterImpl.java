package ru.mishaignatov.touristquiz.presenters;

import android.content.Context;

import java.util.List;

import ru.mishaignatov.touristquiz.game.CountryManager;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.User;
import ru.mishaignatov.touristquiz.orm.Country;
import ru.mishaignatov.touristquiz.ui.views.CountryListView;

/***
 * Created by Mike on 16.02.2016.
 */
public class CountryListPresenterImpl implements CountryListPresenter {

    private CountryListView view;
    private CountryManager mCountryManager;
    private Context mContext;

    public CountryListPresenterImpl(Context context, CountryListView view){
        this.view = view;
        mCountryManager = CountryManager.getInstance(context);
        mContext = context;
    }

    @Override
    public List<Country> getCountryList() {
        return mCountryManager.getList();
    }

    @Override
    public Country getCountry(int position) {
        return mCountryManager.getCountry(position);
    }

    @Override
    public void onListItemClick(int position) {

        Country country = mCountryManager.getCountry(position);

        if (country.opened) {
            //if (OrmDao.getInstance(mContext).isCountryShown(position))
            if (country.ended)
                view.showDialogLevelFinished();
            else
                view.startLevel(position);
        }
        else view.showClosedCountry(position);
    }

    @Override
    public void onBuyCountry(int position) {

        User user = GameManager.getInstance(mContext).getUser();
        Country country = mCountryManager.getCountry(position);

        if (user.getMillis() < country.cost)
            // show message
            view.showNotEnoughMillis();
        else {
            // buy ticket
            user.buyCountry(country.cost);
            mCountryManager.openCountry(country);
            view.startLevel(position);
        }
    }

    @Override
    public void updateCountries() {
        mCountryManager.updateCountries();
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}

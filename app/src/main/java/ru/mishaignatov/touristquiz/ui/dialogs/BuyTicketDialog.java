package ru.mishaignatov.touristquiz.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.orm.Country;
import ru.mishaignatov.touristquiz.orm.OrmDao;
import ru.mishaignatov.touristquiz.ui.fragments.CountryListFragment;

/***
 * Created by Mike on 24.02.2016.
 */
public class BuyTicketDialog extends BaseDialogFragment implements View.OnClickListener {

    private Country mCountry;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_buy_ticket, container, false);

        Bundle args = getArguments();
        int id = args.getInt("KEY_ID");
        mCountry = OrmDao.getInstance(getContext()).getCountryById(id);

        TextView text = (TextView)v.findViewById(R.id.buy_ticket_text);
        text.setText(getString(R.string.dialog_buy_ticket_text, mCountry.cost));

        Button buyBtn = (Button)v.findViewById(R.id.button_send);
        buyBtn.setOnClickListener(this);
        buyBtn.setText(getString(R.string.buy));

        v.findViewById(R.id.button_cancel).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button_cancel:
                dismiss();
                break;
            case R.id.button_send:
                sendBuyResult();
                break;
        }
    }

    private void sendBuyResult(){
        CountryListFragment frag = (CountryListFragment)getTargetFragment();
        frag.buyCountry(mCountry.id);
        dismiss();
    }

    @Override
    public int getTitleColor() {
        return R.color.success_title;
    }

    @Override
    public int getTitleString() {
        return R.string.open_level;
    }
}

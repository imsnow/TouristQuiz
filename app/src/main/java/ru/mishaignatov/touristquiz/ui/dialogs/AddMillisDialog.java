package ru.mishaignatov.touristquiz.ui.dialogs;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.Utils;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;

/***
 * Created by Mike on 19.02.2016.
 */
public class AddMillisDialog extends BaseDialogFragment implements View.OnClickListener {

    private InterstitialAd mInterstitialAd;
    private Button mShowAdButton;

    private String mDeviceId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mShowAdButton = new Button(getContext());
        mShowAdButton.setText(getResources().getString(R.string.dialog_add_millis_ad));
        mShowAdButton.setOnClickListener(this);
        mShowAdButton.setBackgroundResource(R.drawable.sel_button_answer);
        mShowAdButton.setTextAppearance(getContext(), R.style.AnswerButtonStyle);
        mShowAdButton.setTag("showAd");
        //mShowAdButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_play), null, null, null);
        mShowAdButton.setPadding(0, Utils.dpToPx(8), 0, Utils.dpToPx(8));
        addView(mShowAdButton);

        addCancelButton(this);

        String android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        mDeviceId = md5(android_id).toUpperCase();
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        switch (tag){
            case "cancel":
                dismiss();
                break;
            case "showAd":
                mShowAdButton.setText(R.string.load_ad);
                initAd();
                break;
        }
    }

    private void initAd(){
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id_test));  // TEST MODE
        //mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));       // WORK MODE
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.d("TAG", "ad closed");
                Toast.makeText(getActivity(), R.string.toast_plus_millis, Toast.LENGTH_LONG ).show();
                GameManager.getInstance(App.getContext()).getUser().addResult(0, 10);
                mShowAdButton.setText(getString(R.string.dialog_add_millis_ad));
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("TAG", "ad loaded");
                mInterstitialAd.show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.d("TAG", "ad opened");
            }
        });
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder()
                    //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice(mDeviceId)   // TEST MODE
                    .build();
            mInterstitialAd.loadAd(adRequest);
        }
    }

    private static String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int getTitleColor() {
        return R.drawable.dialog_title_green;
    }

    @Override
    public int getTitleString() {
        return R.string.dialog_add_millis_title;
    }
}

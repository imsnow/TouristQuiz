package ru.mishaignatov.touristquiz.ui.dialogs;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;

/***
 * Created by Mike on 19.02.2016.
 */
public class AddMillisDialog extends BaseDialogFragment implements View.OnClickListener {

    private InterstitialAd mInterstitialAd;
    private Button mShowAdButton;

    private String mDeviceId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_add_millis, container, false);

        mShowAdButton = (Button)v.findViewById(R.id.dialog_show_ad);
        mShowAdButton.setOnClickListener(this);

        String android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        mDeviceId = md5(android_id).toUpperCase();

        v.findViewById(R.id.button_cancel).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dialog_show_ad:
                mShowAdButton.setText(R.string.load_ad);
                initAd();
                break;
            case R.id.button_cancel:
                dismiss();
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
                GameManager.getInstance(App.getContext()).getUser().addResult(0, 10);
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
        return R.color.success_title;
    }

    @Override
    public int getTitleString() {
        return R.string.dialog_add_millis_title;
    }
}

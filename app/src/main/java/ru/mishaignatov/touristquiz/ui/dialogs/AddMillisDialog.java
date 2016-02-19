package ru.mishaignatov.touristquiz.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.mishaignatov.touristquiz.R;

/***
 * Created by Mike on 19.02.2016.
 */
public class AddMillisDialog extends DialogFragment implements View.OnClickListener {

    private InterstitialAd mInterstitialAd;
    private Button mShowAdButton;

    private String mDeviceId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.dialog_add_millis, container, false);

        mShowAdButton = (Button)v.findViewById(R.id.dialog_show_ad);
        mShowAdButton.setOnClickListener(this);

        String android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);;
        mDeviceId = md5(android_id).toUpperCase();

        v.findViewById(R.id.button_cancel).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dialog_show_ad:
                mInterstitialAd = new InterstitialAd(getActivity());
                mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id_test));  // TEST MODE
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        Log.d("TAG", "ad closed");
                        mShowAdButton.setText("+10 миль!!!");
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
                break;
            case R.id.button_cancel:
                dismiss();
                break;
        }
    }

    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {

        }
        return "";
    }
}

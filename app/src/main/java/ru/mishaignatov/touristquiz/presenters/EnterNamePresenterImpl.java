package ru.mishaignatov.touristquiz.presenters;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.User;
import ru.mishaignatov.touristquiz.server.APIStrings;
import ru.mishaignatov.touristquiz.server.ApiHelper;
import ru.mishaignatov.touristquiz.server.FBApiHelper;
import ru.mishaignatov.touristquiz.ui.views.EnterNameView;

/**
 * Created by Leva on 23.02.2016.
 **/
public class EnterNamePresenterImpl implements EnterNamePresenter, Response.Listener<String>, FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback {

    private EnterNameView view;
    private Context mContext;

    private String mName;
    private User.TypeName mType;

    public EnterNamePresenterImpl(EnterNameView view, Context context){
        this.view = view;
        mContext = context;
    }

    @Override
    public void checkName(String name) {

        mName = name;
        view.onCheckingName();
        ApiHelper.getHelper(mContext).userNameCheck(
                GameManager.getInstance(mContext).getUser(),
                mName,
                this,
                null);
    }

    @Override
    public void sendName() {
        ApiHelper.getHelper(mContext).userNameSet(
                GameManager.getInstance(mContext).getUser(),
                mName,
                mType,
                this,
                null);
    }

    @Override
    public FacebookCallback<LoginResult> getCallback() {
        return this;
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);

            if (json.get(APIStrings.METHOD).equals(APIStrings.USER_NAME_CHECK)) {
                if (json.get(APIStrings.STATUS).equals(APIStrings.OK)) {
                    // show accept
                    view.onAcceptingName();

                    mType = User.TypeName.ENTERED;
                }
                else {
                    view.onBusyName();
                }
            }

            if(json.get(APIStrings.METHOD).equals(APIStrings.USER_NAME_SET)){

                if (json.get(APIStrings.STATUS).equals(APIStrings.OK)) {
                    view.onNameOk();
                    GameManager.getInstance(mContext).getUser().setDisplayName(mName);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.d("TAG", "here");
        if(loginResult != null ) {
            GameManager.getInstance(mContext).getUser().setFbAccessToken(loginResult.getAccessToken());
            new FBApiHelper().sendProfileRequest(GameManager.getInstance(mContext).getUser(), this);
            view.closeDialog();
        }
    }

    @Override
    public void onCancel() {
        Log.d("TAG", "cancel");
    }

    @Override
    public void onError(FacebookException error) {
        Log.d("TAG", "error");
    }

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        Log.d("TAG", object.toString());
    }
}

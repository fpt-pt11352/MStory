package vn.edu.poly.mstory.object.handle.social;

import android.app.Activity;
import android.content.Intent;
import android.telecom.Call;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;

/**
 * Created by lucius on 30/11/2016.
 */

public class FacebookAPI {
    private Activity activity;
    private CallbackManager callbackManager;

    public FacebookAPI(Activity activity) {
        this.activity = activity;
    }

    public void init() {
        FacebookSdk.sdkInitialize(activity);
        AppEventsLogger.activateApp(activity);
        callbackManager = CallbackManager.Factory.create();
    }

    public void createLoginButton(LoginButton loginButton, FacebookCallback<LoginResult> facebookCallback) {
        loginButton.registerCallback(callbackManager, facebookCallback);
    }

    public void createLoginButton(LoginButton loginButton, ArrayList<String> permissionArray, FacebookCallback<LoginResult> facebookCallback) {
        loginButton.setReadPermissions(permissionArray);
        loginButton.registerCallback(callbackManager, facebookCallback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

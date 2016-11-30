package vn.edu.poly.mstory.object.handle.social;

import android.app.Activity;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

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

    public void createLoginButton(LoginButton loginButton, String [] readPermission, String [] publicPermission, FacebookCallback<LoginResult> facebookCallback) {
        if(readPermission!= null){
            loginButton.setReadPermissions(Arrays.asList(readPermission));
        }
        if(publicPermission!= null){
            loginButton.setPublishPermissions(Arrays.asList(publicPermission));
        }
        loginButton.registerCallback(callbackManager, facebookCallback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

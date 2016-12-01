package vn.edu.poly.mcomics.object.handle.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.LikeView;

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

    public void buttonLike(LikeView likeView, String url){
        likeView.setLikeViewStyle(LikeView.Style.BOX_COUNT);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
        likeView.setObjectIdAndType(url, LikeView.ObjectType.PAGE);
        likeView.setOnErrorListener(new LikeView.OnErrorListener() {
            @Override
            public void onError(FacebookException error) {
                Log.e("error", error.toString());
            }
        });
    }

    public void likeByRequest(){
        Bundle params = new Bundle();
        params.putString("object", "https://www.facebook.com/permalink.php?story_fbid=252253451857769&id=252252888524492");
        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/og.likes",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e("Error", response.toString());
                    }
                }
        ).executeAsync();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

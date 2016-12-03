package vn.edu.poly.mcomics.object.handle.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONObject;

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

    public void createLoginButton(LoginButton loginButton, String[] readPermission, String[] publicPermission, FacebookCallback<LoginResult> facebookCallback) {
        if (readPermission != null) {
            loginButton.setReadPermissions(Arrays.asList(readPermission));
        }
        if (publicPermission != null) {
            loginButton.setPublishPermissions(Arrays.asList(publicPermission));
        }
        loginButton.registerCallback(callbackManager, facebookCallback);
    }

    public void like(String objectId) {
        String access_token;
        if((access_token = getToken()) == null){
            return;
        }
        Bundle params = new Bundle();
        params.putString("access_token", access_token);
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+objectId+"/likes",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e("response", response.toString());
                    }
                }
        ).executeAsync();
    }

    public String getToken() {
        final String[] access_token = new String[1];
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/accounts",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e("response", response.toString());
                        try {
                            if(response.getError() == null){
                                access_token[0] = response.getJSONObject().getJSONArray("data").getJSONObject(0).getString("access_token");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
        return access_token[0];
    }

    public void comment(String id, String comment) {
        Bundle params = new Bundle();
        params.putString("message", comment);
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + id + "/comments",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            Toast.makeText(activity, response.getError() == null ? "Done" : "Fail", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("send", response.toString());
                        }
                    }
                }
        ).executeAsync();
    }

    public void share(String status) {
        GraphRequest request = GraphRequest.newPostRequest(AccessToken.getCurrentAccessToken(), "me/feed", null, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Toast.makeText(activity, response.getError() == null ? "your status is updated" : "Couldn't update your status", Toast.LENGTH_SHORT).show();
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("message", status);
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

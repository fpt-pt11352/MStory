package vn.edu.poly.mcomics.object.handle.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.handle.eventlistener.DownloadEvent;
import vn.edu.poly.mcomics.object.handle.other.Show;

/**
 * Created by lucius on 30/11/2016.
 */

public class FacebookAPI {
    private Activity activity;
    private CallbackManager callbackManager;
    private FacebookHandle fbHandle;
    private String access_token = "EAAYLZBLbKcAkBACwlduPZAa84THE85FjmXukcGpSKWFASLQ5KeeSChABcmLsCvESFMvH2wCZBTZCkLPkyGnGxdu1ArnjyJApebjZAXFtkTZCV3XjgDHat6tINiBZBmfxYNczeOlKIpy8as9JWQD1wVweYApm3xrjSjieR8Ihz6ZCpAZDZD";

    public FacebookAPI(Activity activity) {
        this.activity = activity;
        fbHandle = new FacebookHandle();
    }

    public void init() {
        FacebookSdk.sdkInitialize(activity);
        AppEventsLogger.activateApp(activity);
        callbackManager = CallbackManager.Factory.create();
    }

    public void createLoginButton(LoginButton loginButton, FacebookCallback<LoginResult> facebookCallback) {
        loginButton.setPublishPermissions(Arrays.asList(new String[]{
                        "user_posts" ,
                        "email" ,
                        "manage_pages" ,
                        "publish_pages" ,
                        "business_management" ,
                        "pages_messaging" ,
                        "pages_messaging_payments" ,
                        "public_profile"}));
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

    public void like(final String objectId) {
        Bundle params = new Bundle();
        params.putString("access_token", access_token);
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + objectId + "/likes", params, HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Show.log("response", response.toString());
                        if (response.toString().indexOf("\"success\":true") != -1) {
                            fbHandle.getCount(objectId, FacebookHandle.LIKES, new DownloadEvent() {
                                @Override
                                public void onLoadFinish(String string) {
                                    ((TextView) activity.findViewById(R.id.like)).setText("Liked (" + string + ")");
                                }
                            });
                        } else {
                            Show.toastSHORT(activity, "Fail");
                        }
                    }
                }
        ).executeAsync();
    }

    public void comment(String id, String comment) {
        Bundle params = new Bundle();
        params.putString("message", comment);
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + id + "/comments", params, HttpMethod.POST,
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

    public void share(final String objectId, String status) {
        GraphRequest request = GraphRequest.newPostRequest(AccessToken.getCurrentAccessToken(), "me/feed", null, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                fbHandle.getCount(objectId, FacebookHandle.LIKES, new DownloadEvent() {
                    @Override
                    public void onLoadFinish(String string) {
                        ((TextView) activity.findViewById(R.id.share)).setText("Shared (" + string + ")");
                    }
                });
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

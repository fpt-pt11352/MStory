package vn.edu.poly.mcomics.object.handle.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Arrays;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.handle.eventlistener.DownloadEvent;
import vn.edu.poly.mcomics.object.handle.other.Show;
import vn.edu.poly.mcomics.object.variable.FaceBookComment;

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
                "user_posts",
                "email",
                "manage_pages",
                "publish_pages",
                "business_management",
                "pages_messaging",
                "pages_messaging_payments",
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

    public void showCount(String objectId, final String action, final TextView textView) {
        fbHandle.getCount(objectId, action, new DownloadEvent() {
            @Override
            public void onLoadFinish(String string) {
                if (Integer.valueOf(string) == 0) {
                    return;
                }
                String name;
                if (action.equals(FacebookHandle.LIKES)) {
                    name = "liked";
                } else {
                    name = "commented";
                }
                textView.setText(name + " (" + string + ")");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
        });
    }

    public void like(final String objectId) {
        Bundle params = new Bundle();
        params.putString("access_token", access_token);
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + objectId + "/likes", params, HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Show.log("response", response.toString());
                        if (response.toString().indexOf("\"success\":true") != -1) {
                            showCount(objectId, FacebookHandle.LIKES, (TextView) activity.findViewById(R.id.like));
                        } else {
                            Show.toastSHORT(activity, "Fail");
                        }
                    }
                }
        ).executeAsync();
    }

    public void comment(final String id, String comment) {
        Bundle params = new Bundle();
        params.putString("message", comment);
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + id + "/comments", params, HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            showCount(id, FacebookHandle.COMMENTS, (TextView) activity.findViewById(R.id.comment));
                            showFbCommentList(id, R.id.ll_commentList);
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
                Show.log("share.réponse", response.toString());
                if (response.getError() == null) {
                    Show.toastSHORT(activity, "Chia sẻ thành công");
                }
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

    public void showFbCommentList(String objectId, final int view) {
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + objectId + "/comments", null, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            ArrayList<FaceBookComment> list = fbHandle.getFbCommentList(response.getJSONObject().getJSONArray("data"));
                            ViewGroup parent = (ViewGroup) activity.findViewById(view);

                            for (int x = 0; x < list.size(); x++) {
                                FaceBookComment temp  = list.get(x);
                                View cmt = ((LayoutInflater.from(activity)).inflate(R.layout.comment_view, parent, false));
                                ((TextView)cmt.findViewById(R.id.name)).setText(temp.getUserName());
                                ((TextView)cmt.findViewById(R.id.txv_comment)).setText(temp.getUserMessage());
                                ((TextView)cmt.findViewById(R.id.txv_time)).setText(temp.getTime().substring(0,temp.getTime().indexOf("+")));
                                parent.addView(cmt, x);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    public boolean isLogined() {
        return AccessToken.getCurrentAccessToken() != null;
    }

}

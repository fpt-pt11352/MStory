package vn.edu.poly.mcomics.object.handle.social;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import vn.edu.poly.mcomics.object.handle.eventlistener.DownloadEvent;
import vn.edu.poly.mcomics.object.handle.other.Show;

/**
 * Created by lucius on 03/12/2016.
 */

public class FacebookHandle {
    public static final String COMMENTS = "comments";
    public static final String LIKES = "likes";
    public static final String SHAREDPOSTS = "sharedposts";
    private String access_token;

    public void getCount(String id, String objectName, final DownloadEvent downloadEvent) {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + id + "/" + objectName,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            int count = response.getJSONObject().getJSONArray("data").length();
                            Show.log("getCount", count + "");
                            downloadEvent.onLoadFinish(response.getJSONObject().getJSONArray("data").length() + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    public void getTokenFirst() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/accounts",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Show.log("getToken_response", response.toString());
                        try {
                            if (response.getError() == null) {
                                access_token = response.getJSONObject().getJSONArray("data").getJSONObject(0).getString("access_token");
                            } else {
                                Show.log("getToken", "erroe");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    public String getToken() {
        return access_token;
    }
}

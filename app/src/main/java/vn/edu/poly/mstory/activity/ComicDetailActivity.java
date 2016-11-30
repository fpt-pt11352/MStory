package vn.edu.poly.mstory.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.ArrayList;

import vn.edu.poly.mstory.R;
import vn.edu.poly.mstory.object.handle.social.FacebookAPI;

public class ComicDetailActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {
    ImageView imgComicsDetail;
    Button btnDoctruyen, btnshare, btnviews, btnComments;
    TextView txtComicsName, txtContent;
    private CallbackManager callbackManager;
    private FacebookAPI facebookAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookAPI = new FacebookAPI(this);
        facebookAPI.init();
        setContentView(R.layout.activity_comic_detail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        facebookAPI.createLoginButton((LoginButton) findViewById(R.id.login_button), null, new String[]{"publish_actions"}, this);

        LikeView likeView = (LikeView) findViewById(R.id.likeView);
        likeView.setLikeViewStyle(LikeView.Style.BOX_COUNT);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
        //https://www.facebook.com/permalink.php?story_fbid=252253451857769&id=252252888524492
        //likeView.setObjectIdAndType("https://www.facebook.com/MComics-252252888524492/", LikeView.ObjectType.PAGE);
        likeView.setObjectIdAndType("https://www.facebook.com/permalink.php?story_fbid=252253451857769&id=252252888524492", LikeView.ObjectType.PAGE);
        likeView.setOnErrorListener(new LikeView.OnErrorListener() {
            @Override
            public void onError(FacebookException error) {
                Log.e("error", error.toString());
            }
        });

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


        final Intent intent = getIntent();
//        imgComicsDetail = (ImageView) findViewById(R.id.imgComicsDetail);
//
//        btnshare = (Button) findViewById(R.id.btnshare);
//
//        btnComments = (Button) findViewById(R.id.btncomments);
//        txtComicsName = (TextView) findViewById(R.id.txtComicsName);
//        txtContent = (TextView) findViewById(R.id.txtContent);
//        btnviews= (Button) findViewById(R.id.btnviews);
//        Picasso.with(this).load(intent.getStringExtra("thumbnail")).resize(400, 500).error(R.mipmap.bia).into(imgComicsDetail);
//        txtComicsName.setText(intent.getStringExtra("comicsName"));
//        txtContent.setText(intent.getStringExtra("content"));
//        btnviews.setText(intent.getStringExtra("view"));

        btnDoctruyen = (Button) findViewById(R.id.btnDoctruyen);
        btnDoctruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getBaseContext(), ComicChaptersActivity.class);
                intent2.putExtra("id", intent.getStringExtra("id"));
                startActivity(intent2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }
}

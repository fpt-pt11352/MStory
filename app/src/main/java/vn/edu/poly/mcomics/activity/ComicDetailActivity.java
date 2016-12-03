package vn.edu.poly.mcomics.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.LikeView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.handle.backgroundtask.LoadJsonInBackground;
import vn.edu.poly.mcomics.object.handle.eventlistener.DownloadEvent;
import vn.edu.poly.mcomics.object.handle.json.ParserJSON;
import vn.edu.poly.mcomics.object.handle.other.NavigationDrawer;
import vn.edu.poly.mcomics.object.handle.social.FacebookAPI;
import vn.edu.poly.mcomics.object.variable.Comics;

public class ComicDetailActivity extends AppCompatActivity implements DownloadEvent, FacebookCallback<LoginResult> {
    private Button btn_openComics;
    private FacebookAPI facebookAPI;
    private boolean isShow;
    private TextView txv_readMoreTop, txv_readMoreBottom, txv_review;
    private NavigationDrawer navigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookAPI = new FacebookAPI(this);
        facebookAPI.init();
        setContentView(R.layout.navigation_view);
        navigationDrawer = new NavigationDrawer(this, R.layout.activity_comics_detail, (ViewGroup) findViewById(R.id.root).getParent());
        getView();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
//        facebookAPI.createLikeButton(
//                ,
//                "https://www.facebook.com/permalink.php?story_fbid=252253451857769&id=252252888524492");
        LikeView likeView = (LikeView) findViewById(R.id.likeView);
        facebookAPI.createLikeButton(likeView, "https://www.facebook.com/permalink.php?story_fbid=253441638405617&id=252252888524492/");

        ((TextView)findViewById(R.id.comment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookAPI.TestLike();
            }
        });

        final Intent intent = getIntent();
        LoadJsonInBackground loadJson = new LoadJsonInBackground();
        loadJson.setOnFinishEvent(this);
        loadJson.execute("http://grayguy.xyz/?kind=comics_detail&id=" + intent.getExtras().
                getString("id")
        );

        btn_openComics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getBaseContext(), ComicChaptersActivity.class);
                intent2.putExtra("id", intent.getStringExtra("id"));
                startActivity(intent2);
            }
        });

        txv_readMoreBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideReview();
            }
        });
    }

    public void getView() {
        btn_openComics = (Button) findViewById(R.id.btnDoctruyen);
        txv_review = (TextView) findViewById(R.id.txv_review);
        txv_readMoreTop = (TextView) findViewById(R.id.txv_readMoreTop);
        txv_readMoreBottom = (TextView) findViewById(R.id.txv_readMoreBottom);
    }

    public void showHideReview() {
        if (isShow = !isShow) {
            show();
        } else {
            hide();
        }
    }

    public void show() {
        txv_review.setMaxLines(Integer.MAX_VALUE);
        txv_readMoreBottom.setText("Ẩn");
    }

    public void hide() {
        txv_review.setMaxLines(3);
        txv_readMoreBottom.setText("Đọc thêm");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoadFinish(String string) {
        try {
            Comics comics = new ParserJSON().getComic(new JSONArray(string).getJSONObject(0));
            Picasso.with(this).load(comics.getThumbnail()).resize(400, 600).error(R.mipmap.bia).into(((ImageView) findViewById(R.id.imv_cover)));
            ((TextView) findViewById(R.id.txv_name)).setText(comics.getComicsName());
            ((TextView) findViewById(R.id.txv_author)).setText(comics.getAuthor());
            ((TextView) findViewById(R.id.txv_kinds)).setText(comics.getKind());
            ((TextView) findViewById(R.id.txv_chapter)).setText(comics.getEpisodes());
            ((TextView) findViewById(R.id.txv_views)).setText(comics.getViews() + "");
            ((TextView) findViewById(R.id.txv_review)).setText(comics.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

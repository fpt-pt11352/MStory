package vn.edu.poly.mcomics.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.widget.LikeView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.handle.backgroundtask.LoadJsonInBackground;
import vn.edu.poly.mcomics.object.handle.eventlistener.DownloadEvent;
import vn.edu.poly.mcomics.object.handle.json.ParserJSON;
import vn.edu.poly.mcomics.object.handle.social.FacebookAPI;
import vn.edu.poly.mcomics.object.variable.Comics;

public class ComicDetailActivity extends AppCompatActivity implements DownloadEvent {
    private Button btn_openComics;
    private FacebookAPI facebookAPI;
    private boolean isShow;
    private TextView txv_readMoreTop, txv_readMoreBottom, txv_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookAPI = new FacebookAPI(this);
        facebookAPI.init();
        setContentView(R.layout.activity_comics_detail);
        getView();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        facebookAPI.buttonLike(
                (LikeView) findViewById(R.id.likeView),
                "https://www.facebook.com/permalink.php?story_fbid=252253451857769&id=252252888524492");
//        Bundle params = new Bundle();
//        params.putString("object", "https://www.facebook.com/permalink.php?story_fbid=252253451857769&id=252252888524492");
//        /* make the API call */
//        new GraphRequest(
//                AccessToken.getCurrentAccessToken(),
//                "/me/og.likes",
//                params,
//                HttpMethod.POST,
//                new GraphRequest.Callback() {
//                    public void onCompleted(GraphResponse response) {
//                        Log.e("Error1", response.toString());
//                    }
//                }
//        ).executeAsync();


//        Bundle params1 = new Bundle();
//        params1.putString("object", "https://www.facebook.com/permalink.php?story_fbid=252253451857769&id=252252888524492");
//        new GraphRequest(
//                AccessToken.getCurrentAccessToken(),
//                "/935700306566899",
//                null,
//                HttpMethod.DELETE,
//                new GraphRequest.Callback() {
//                    public void onCompleted(GraphResponse response) {
//                        Log.e("Error", response.toString());
//                    }
//                }
//        ).executeAsync();
        final Intent intent = getIntent();
        LoadJsonInBackground loadJson = new LoadJsonInBackground();
        loadJson.setOnFinishEvent(this);
        loadJson.execute("http://grayguy.xyz/?kind=comics_detail&id=" + intent.getExtras().getString("id"));


        btn_openComics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getBaseContext(), ComicChaptersActivity.class);
                intent2.putExtra("id", intent.getStringExtra("id"));
                startActivity(intent2);
            }
        });
        txv_readMoreTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideReview();
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
    }

    public void hide() {
        txv_review.setMaxLines(3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
}

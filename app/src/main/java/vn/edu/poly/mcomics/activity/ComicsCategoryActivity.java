package vn.edu.poly.mcomics.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.handle.backgroundtask.LoadJsonInBackground;
import vn.edu.poly.mcomics.object.handle.custom.adapter.ComicListCustomAdapter;
import vn.edu.poly.mcomics.object.handle.eventlistener.DownloadEvent;
import vn.edu.poly.mcomics.object.handle.json.ParserJSON;
import vn.edu.poly.mcomics.object.handle.other.NavigationDrawer;
import vn.edu.poly.mcomics.object.handle.social.FacebookAPI;
import vn.edu.poly.mcomics.object.variable.Comics;

public class ComicsCategoryActivity extends AppCompatActivity implements DownloadEvent {
    private GridView androidGridView;
    private TextView text;
    private FacebookAPI facebookAPI;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    public static void showToastLONG(Activity activity, String text) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookAPI = new FacebookAPI(this);
        facebookAPI.init();
        setContentView(R.layout.navigation_view);
        new NavigationDrawer(this, R.layout.activity_comics_category, (ViewGroup) (findViewById(R.id.root)).getParent());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        text = (TextView) findViewById(R.id.text);
        LoadJsonInBackground backgroundTask = new LoadJsonInBackground();
        backgroundTask.setOnFinishEvent(this);
        backgroundTask.execute("http://grayguy.xyz/?kind=by_kind&comic_kind=" + getIntent().getExtras().getInt("id") + "&from=0&to=50");

    }

    //button search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }

    @Override
    public void onLoadFinish(String string) {
        ParserJSON parserJSON = new ParserJSON();
        try {
            ArrayList<Comics> arrComics = parserJSON.getComicArray(string);
            ComicListCustomAdapter adapterViewAndroid = new ComicListCustomAdapter(ComicsCategoryActivity.this, arrComics);
            androidGridView = (GridView) findViewById(R.id.grid_view_image_text);
            androidGridView.setAdapter(adapterViewAndroid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookAPI.onActivityResult(requestCode, resultCode, data);
    }
}

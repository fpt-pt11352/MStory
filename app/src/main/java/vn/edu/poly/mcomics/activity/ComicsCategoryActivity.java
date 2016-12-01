package vn.edu.poly.mcomics.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.handle.backgroundtask.LoadJsonInBackground;
import vn.edu.poly.mcomics.object.handle.custom.adapter.ComicListCustomAdapter;
import vn.edu.poly.mcomics.object.handle.eventlistener.DownloadEvent;
import vn.edu.poly.mcomics.object.handle.json.ParserJSON;
import vn.edu.poly.mcomics.object.variable.Comics;

public class ComicsCategoryActivity extends AppCompatActivity implements DownloadEvent {
    ParserJSON parserJSON=new ParserJSON();
    ArrayList<Comics> arrComics=new ArrayList<>();

    GridView androidGridView;
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_category);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        text = (TextView) findViewById(R.id.text);
        LoadJsonInBackground backgroundTask = new LoadJsonInBackground();
        backgroundTask.setOnFinishEvent(this);
        backgroundTask.execute("http://grayguy.xyz/?kind=by_kind&comic_kind="+getIntent().getExtras().getInt("id")+"&from=0&to=10");

    }

    //button search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }

    @Override
    public void onLoadFinish(String string) {
        ParserJSON parserJSON=new ParserJSON();
        try {
            ArrayList<Comics> arrComics=parserJSON.getComicArray(string);
            ComicListCustomAdapter adapterViewAndroid = new ComicListCustomAdapter(ComicsCategoryActivity.this,arrComics);
            androidGridView = (GridView) findViewById(R.id.grid_view_image_text);
            androidGridView.setAdapter(adapterViewAndroid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

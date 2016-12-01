package vn.edu.poly.mcomics.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
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

public class ComicCategory extends AppCompatActivity implements DownloadEvent {
    ParserJSON parserJSON=new ParserJSON();
    ArrayList<Comics> arrComics=new ArrayList<>();

    GridView androidGridView;
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_category);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        text = (TextView) findViewById(R.id.text);

        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("MyPackage");
        String title=packageFromCaller.getString("name");
        text.setText(title);
        Log.d("query","SELECT * FROM  `comics_master` WHERE kind LIKE '%"+Uri.encode("Marvel")+"%'");
        LoadJsonInBackground backgroundTask = new LoadJsonInBackground();
        backgroundTask.setOnFinishEvent(this);
        backgroundTask.execute("http://grayguy.xyz/?comics_kind="+Uri.encode(text.getText().toString()));
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
            ComicListCustomAdapter adapterViewAndroid = new ComicListCustomAdapter(ComicCategory.this,arrComics);
            androidGridView = (GridView) findViewById(R.id.grid_view_image_text);
            androidGridView.setAdapter(adapterViewAndroid);
            androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package vn.edu.poly.mcomics.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.handle.backgroundtask.LoadJsonInBackground;
import vn.edu.poly.mcomics.object.handle.custom.adapter.AdapterImage;
import vn.edu.poly.mcomics.object.handle.eventlistener.DownloadEvent;
import vn.edu.poly.mcomics.object.handle.json.ParserJSON;
import vn.edu.poly.mcomics.object.variable.Content;


public class ComicsReadingActivity extends AppCompatActivity {
    private ArrayList<Content> urlList;
    private RecyclerView recyclerView;
    private String id;
    private String chapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_reading);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        chapter = intent.getIntExtra("chapter",0)+"";
        recyclerView = (RecyclerView) findViewById(R.id.cardView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplication());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
        LoadJsonInBackground loadJsonInBackground = new LoadJsonInBackground();
        loadJsonInBackground.setOnFinishEvent(new DownloadEvent() {
            @Override
            public void onLoadFinish(String string) {
                load(string);
            }
        });
        loadJsonInBackground.execute("http://grayguy.xyz/index.php?kind=comics_content&comics_id="+id+"&chapter_number="+chapter);
    }

    public void load(String s) {
        ParserJSON parserJSON = new ParserJSON();
        try {
            urlList = parserJSON.getPage(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AdapterImage adapter = new AdapterImage(urlList);
        recyclerView.setAdapter(adapter);
    }

}

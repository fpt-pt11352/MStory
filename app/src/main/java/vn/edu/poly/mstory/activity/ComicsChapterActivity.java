package vn.edu.poly.mstory.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;

import vn.edu.poly.mstory.R;
import vn.edu.poly.mstory.object.handle.backgroundtask.LoadJsonInBackground;
import vn.edu.poly.mstory.object.handle.custom.adapter.ChapterListAdapter;
import vn.edu.poly.mstory.object.handle.eventlistener.DownloadEvent;
import vn.edu.poly.mstory.object.handle.json.ParserJSON;

public class ComicsChapterActivity extends AppCompatActivity implements DownloadEvent{
    GridView gridView;
    String comicId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_chapter);

       comicId=getIntent().getStringExtra("id");

        LoadJsonInBackground loadJson = new LoadJsonInBackground();
        loadJson.setOnFinishEvent(this);
        loadJson.execute("http://grayguy.xyz/?kind=chapter_list&comic_id="+comicId);

    }

    @Override
    public void onLoadFinish(String string) {
        ParserJSON parserJSON = new ParserJSON();
        try {
            final ArrayList<Integer> list = parserJSON.getChapterArray(string);
           gridView= ((GridView)findViewById(R.id.gridView));
            gridView.setAdapter(new ChapterListAdapter(this, list));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(getBaseContext(),ComicsReaderActivity.class);
                    intent.putExtra("id",comicId);
                    intent.putExtra("chapterNumber",list.get(position)+"");
                    startActivity(intent);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

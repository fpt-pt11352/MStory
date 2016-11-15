package vn.edu.poly.mstory.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.mstory.R;
import vn.edu.poly.mstory.object.variable.Comics;
import vn.edu.poly.mstory.object.handle.custom.AdapterRecyclerView;
import vn.edu.poly.mstory.object.handle.custom.CustomAdapter;
import vn.edu.poly.mstory.object.handle.json.JSONParser;
import vn.edu.poly.mstory.object.handle.json.ParseJson;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterRecyclerView adapter;
    List<String> data;

    String androidListViewStrings[] = {"Truyện Kinh Dị", "Truyện Ngắn", "Truyện Ngụ Ngôn", "Truyện Thiếu Nhi", "Truyện Ngôn Tình"};
    ArrayList<Comics> arrComic = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1.start();

        CustomAdapter androidListAdapter = new CustomAdapter(this, androidListViewStrings);
        ListView androidListView = (ListView) findViewById(R.id.listt);
        androidListView.setAdapter(androidListAdapter);


        try {
            t1.join();
            t2.start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void onItemClickLisener(View view) {

    }

    Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = jsonParser.getJSONFromUrl("http://grayguy.xyz/?kind=top");
            ParseJson parseJson = new ParseJson();
            arrComic = parseJson.parseComics(jsonArray);

        }
    });
    Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            Log.d("khang", arrComic.size() + "");
            recyclerView = (RecyclerView) findViewById(R.id.recyclerViewList);
            adapter = new AdapterRecyclerView(arrComic);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            Log.d("URL", arrComic.get(2).thumbnail);
            Log.d("URL", arrComic.get(3).thumbnail);
            Log.d("URL", arrComic.get(4).thumbnail);
        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }


}

package vn.edu.poly.mstory.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import vn.edu.poly.mstory.R;

public class ComicsReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_reader);

        Log.d("id and chap","dsad"+ getIntent().getStringExtra("id")+" : "+getIntent().getStringExtra("chapterNumber"));
    }
}

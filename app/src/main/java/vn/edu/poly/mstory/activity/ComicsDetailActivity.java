package vn.edu.poly.mstory.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vn.edu.poly.mstory.R;

public class ComicsDetailActivity extends AppCompatActivity {
    ImageView imgComicsDetail;
    Button btnDoctruyen, btnshare, btnviews, btnComments;
    TextView txtComicsName, txtContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_detail);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        final Intent intent = getIntent();
        imgComicsDetail = (ImageView) findViewById(R.id.imgComicsDetail);
        btnDoctruyen = (Button) findViewById(R.id.btnDoctruyen);
        btnshare = (Button) findViewById(R.id.btnshare);

        btnComments = (Button) findViewById(R.id.btncomments);
        txtComicsName = (TextView) findViewById(R.id.txtComicsName);
        txtContent = (TextView) findViewById(R.id.txtContent);
        btnviews= (Button) findViewById(R.id.btnviews);
        Picasso.with(this).load(intent.getStringExtra("thumbnail")).resize(400, 500).error(R.mipmap.bia).into(imgComicsDetail);
        txtComicsName.setText(intent.getStringExtra("comicsName"));
        txtContent.setText(intent.getStringExtra("content"));
        btnviews.setText(intent.getStringExtra("view"));
        btnDoctruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getBaseContext(),ComicsChapterActivity.class);
                intent2.putExtra("id",intent.getStringExtra("id"));
                startActivity(intent2);
            }
        });
    }
}

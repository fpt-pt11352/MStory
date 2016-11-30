package vn.edu.poly.mstory.object.handle.custom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.poly.mstory.R;
import vn.edu.poly.mstory.activity.ComicDetailActivity;
import vn.edu.poly.mstory.object.variable.Comic;


public class ComicListCustomAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Comic> arrComic=new ArrayList<>();


    public ComicListCustomAdapter(Context mContext, ArrayList<Comic> arrComics) {
        this.mContext = mContext;
        this.arrComic = arrComics;
    }

    @Override
    public int getCount() {
        return arrComic.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = (LayoutInflater.from(mContext)).inflate(R.layout.custom_gridview, null, false);
        TextView textViewAndroid = (TextView) view.findViewById(R.id.android_gridview_text);
        ImageView imageViewAndroid = (ImageView) view.findViewById(R.id.android_gridview_image);
        textViewAndroid.setText(arrComic.get(i).getComicsName());
        Picasso.with(mContext).load(arrComic.get(i).getThumbnail()).error(R.mipmap.bia).into(imageViewAndroid);

        final int position = i;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ComicDetailActivity.class);
                intent.putExtra("id",arrComic.get(position).getId()+"");
                intent.putExtra("comicsName",arrComic.get(position).getComicsName());
                intent.putExtra("view",String.valueOf(arrComic.get(position).getViews()));
                intent.putExtra("content",arrComic.get(position).getContent());
                intent.putExtra("thumbnail",arrComic.get(position).getThumbnail());
                mContext.startActivity(intent);
            }
        });
        return view;
    }
}

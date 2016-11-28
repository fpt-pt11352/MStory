package vn.edu.poly.mstory.object.handle.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.poly.mstory.R;
import vn.edu.poly.mstory.object.variable.Comic;


public class loadGridView extends BaseAdapter {

    private Context mContext;
    //private final String[] gridViewString;
    //private final int[] gridViewImageId;

    private ArrayList<Comic> arrComics=new ArrayList<>();


    public loadGridView(Context mContext, ArrayList<Comic> arrComics) {
        this.mContext = mContext;
        this.arrComics = arrComics;
    }

    @Override
    public int getCount() {
        return arrComics.size();
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
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            gridViewAndroid = inflater.inflate(R.layout.custom_gridview, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(arrComics.get(i).getComicsName());
            Picasso.with(mContext).load(arrComics.get(i).getThumbnail()).error(R.mipmap.bia).into(imageViewAndroid);
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}

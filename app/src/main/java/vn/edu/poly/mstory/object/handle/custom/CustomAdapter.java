package vn.edu.poly.mstory.object.handle.custom;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import vn.edu.poly.mstory.R;


/**
 * Created by ADMIN-PC on 11/12/2016.
 */

public class CustomAdapter extends ArrayAdapter {
    String[] androidListViewStrings;
    Integer[] imagesId;
    Context context;

    public CustomAdapter(Context context, String[] textListView) {
        super(context, R.layout.customlistview, textListView);
        this.androidListViewStrings = textListView;
        this.imagesId = imagesId;
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.customlistview, null,
                true);
        TextView mtextView = (TextView) viewRow.findViewById(R.id.text);

        mtextView.setText(androidListViewStrings[i]);

        return viewRow;
    }

}
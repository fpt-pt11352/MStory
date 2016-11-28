package vn.edu.poly.mstory.object.handle.custom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.poly.mstory.R;
import vn.edu.poly.mstory.activity.ComicsDetailActivity;
import vn.edu.poly.mstory.object.variable.Comic;

/**
 * Created by user on 11/24/2016.
 */

public class CustomRecycleViewReader extends RecyclerView.Adapter<CustomRecycleViewReader.RecyclerViewHolder> {


    private Context context;
    ArrayList<Comic> arrComic = new ArrayList<>();

    public CustomRecycleViewReader(ArrayList<Comic> arrComic) {
        this.arrComic = arrComic;
    }

    @Override
    public CustomRecycleViewReader.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.recycleview_reader, parent, false);
        return new CustomRecycleViewReader.RecyclerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(CustomRecycleViewReader.RecyclerViewHolder holder, final int position) {
        final Context context = holder.imageViewReader.getContext();

    }

    @Override
    public int getItemCount() {
        return arrComic.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewReader;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageViewReader= (ImageView) itemView.findViewById(R.id.imageViewReader);


        }

    }
}

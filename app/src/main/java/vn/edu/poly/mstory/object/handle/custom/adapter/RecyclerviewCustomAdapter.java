package vn.edu.poly.mstory.object.handle.custom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.poly.mstory.R;
import vn.edu.poly.mstory.object.variable.Comic;

/**
 * Created by lucius on 11/15/16.
 */

public class RecyclerviewCustomAdapter extends RecyclerView.Adapter<RecyclerviewCustomAdapter.RecyclerViewHolder>{

    private int[] arrImg;
    private Context context;
    ArrayList<Comic> arrComic=new ArrayList<>();
    public RecyclerviewCustomAdapter(ArrayList<Comic> arrComic) {
        this.arrComic = arrComic;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.recycleview, parent, false);
        return new RecyclerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Context context = holder.imgv.getContext();
        holder.txtHeader.setText(arrComic.get(position).comicsName);

        Picasso.with(context).load(arrComic.get(position).thumbnail).error(R.mipmap.bia).resize(300,400).into(holder.imgv);

    }

    @Override
    public int getItemCount() {
        return arrComic.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeader;
        ImageView imgv;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtHeader = (TextView) itemView.findViewById(R.id.txtHeader);
            imgv= (ImageView) itemView.findViewById(R.id.imgItem);
        }

    }
}

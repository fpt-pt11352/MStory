package vn.edu.poly.mstory.object.handle.custom;

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
import vn.edu.poly.mstory.object.variable.Comics;

/**
 * Created by ADMIN-PC on 11/12/2016.
 */

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.RecyclerViewHolder> {

    private int[] arrImg;
    private Context context;
    ArrayList<Comics> arrComic=new ArrayList<>();
    public AdapterRecyclerView(ArrayList<Comics> arrComic) {

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

   /* public View getView(final int arg0, View convertView, ViewGroup arg2) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.recycleview,
                    null);
        }

        ImageView imgItem = (ImageView) convertView.findViewById(R.id.imgItem);
        imgItem.setImageResource(arrImg[arg0]);

        return convertView;
    }*/

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
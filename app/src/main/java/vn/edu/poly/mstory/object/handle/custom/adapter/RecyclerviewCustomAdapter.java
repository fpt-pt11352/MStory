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
 * Created by lucius on 11/15/16.
 */

public class RecyclerviewCustomAdapter extends RecyclerView.Adapter<RecyclerviewCustomAdapter.RecyclerViewHolder> {

    private int[] arrImg;
    private Context context;
    ArrayList<Comic> arrComic = new ArrayList<>();

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
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final Context context = holder.comicImage.getContext();
        holder.comicName.setText(arrComic.get(position).comicsName);
        holder.viewNumber.setText(arrComic.get(position).getViews()+"");
        holder.chapterNumber.setText(arrComic.get(position).getEpisodes()+"");
        Picasso.with(context).load(arrComic.get(position).thumbnail).error(R.mipmap.bia).resize(300, 400).into(holder.comicImage);
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ComicsDetailActivity.class);
                intent.putExtra("id",arrComic.get(position).getId()+"");
                intent.putExtra("comicsName",arrComic.get(position).getComicsName());
                intent.putExtra("view",String.valueOf(arrComic.get(position).getViews()));
                intent.putExtra("content",arrComic.get(position).getContent());
                intent.putExtra("thumbnail",arrComic.get(position).getThumbnail());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrComic.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView comicName, viewNumber, chapterNumber;
        ImageView comicImage;
        LinearLayout linear;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            linear= (LinearLayout) itemView.findViewById(R.id.linear);
            comicName = (TextView) itemView.findViewById(R.id.txtHeader);
            comicImage = (ImageView) itemView.findViewById(R.id.imgItem);
            viewNumber = (TextView)itemView.findViewById(R.id.view_number);
            chapterNumber = (TextView)itemView.findViewById(R.id.chapter_number);

        }

    }
}

package vn.edu.poly.mcomics.object.handle.custom.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.variable.Content;


/**
 * Created by vuong on 30/11/2016.
 */
//   commit
public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ViewHolder> {
    private ArrayList<Content> arrImage;
    private Content ct;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageView;
        public ViewHolder(CardView v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.coverImageView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterImage(ArrayList<Content> arrImage) {
        this.arrImage = arrImage;
//        this.ct = ct;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_page, parent, false);
        // set the view's size, margins, paddings and layout parameters


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, null, true);
        WindowManager windowManager = (WindowManager)parent.getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        view.setLayoutParams(new RecyclerView.LayoutParams(width, RecyclerView.LayoutParams.MATCH_PARENT));

        ViewHolder viewHolder = new ViewHolder((CardView) view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Context context = holder.imageView.getContext();

        Picasso.with(context).load(arrImage.get(position).getLink()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrImage.size();
    }
}
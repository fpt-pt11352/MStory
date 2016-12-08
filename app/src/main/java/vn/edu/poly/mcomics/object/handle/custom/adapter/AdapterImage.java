package vn.edu.poly.mcomics.object.handle.custom.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;

import vn.edu.poly.mcomics.R;
import vn.edu.poly.mcomics.object.handle.other.SettingHandle;
import vn.edu.poly.mcomics.object.variable.Content;


/**
 * Created by vuong on 30/11/2016.
 */
public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ViewHolder> {
    private ArrayList<Content> arrImage;
    private Activity activity;

    public AdapterImage(Activity activity, ArrayList<Content> arrImage) {
        this.arrImage = arrImage;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_page, null, true);
        WindowManager windowManager = (WindowManager) parent.getContext().getSystemService(Context.WINDOW_SERVICE);

        int width = getScreenWidth();
        //view.setLayoutParams(new RecyclerView.LayoutParams(width, RecyclerView.LayoutParams.MATCH_PARENT));
        ((ImageView)view.findViewById(R.id.coverImageView)).getLayoutParams().width = getScreenWidth();
        ((ImageView)view.findViewById(R.id.coverImageView)).getLayoutParams().width = getScreenWidth();

        return new ViewHolder((CardView) view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Context context = holder.imageView.getContext();
        SettingHandle settingHandle = new SettingHandle(activity);
        if(settingHandle.getOrientation() == SettingHandle.VERTICAL){
            holder.imageView.setPadding(0, 0, 0, 15);
            holder.imageView.setBackgroundColor(Color.parseColor("#757575"));
        }
        Picasso.with(context).load(arrImage.get(position).getLink()).resize(getScreenWidth(), (getScreenHeight()-50)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrImage.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(CardView v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.coverImageView);
        }
    }

    public int getScreenWidth() {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point.x;
    }
    public int getScreenHeight() {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point.y;
    }
}
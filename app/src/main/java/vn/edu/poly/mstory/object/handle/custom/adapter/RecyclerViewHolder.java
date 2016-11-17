package vn.edu.poly.mstory.object.handle.custom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import vn.edu.poly.mstory.R;

/**
 * Created by ADMIN-PC on 11/12/2016.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView txtHeader;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        txtHeader = (TextView) itemView.findViewById(R.id.txtHeader);
    }

}
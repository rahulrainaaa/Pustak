package com.product.pustak.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.product.pustak.R;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.model.Post;

import java.util.ArrayList;

public class MyPostListViewAdapter extends ArrayAdapter<Post> {

    private BaseActivity mActivity;
    private ArrayList<Post> mPostList = null;
    private int mLayoutResource = -1;
    private int redIndicatorDrawable = R.drawable.border_shape_red_box;
    private int greenIndicatorDrawable = R.drawable.border_shape_green_box;

    public static class ViewHolder {

        public ImageView imgIndicator = null;
        public TextView txtBookName = null;
        public TextView txtBookAuthor = null;
        public TextView txtRent = null;
        public TextView txtSell = null;
        public TextView txtVisibility = null;
        public TextView txtIsActive = null;
        public TextView txtBookDescription = null;
        public TextView txtPostedBefore = null;
        public ImageButton imgDelete = null;
        public ImageButton imgEdit = null;

        public ViewHolder(View view) {

            imgIndicator = (ImageView) view.findViewById(R.id.img_indicator);
            txtBookName = (TextView) view.findViewById(R.id.txt_book_title);
            txtBookAuthor = (TextView) view.findViewById(R.id.txt_book_author);
            txtRent = (TextView) view.findViewById(R.id.txt_rent);
            txtSell = (TextView) view.findViewById(R.id.txt_book_sell);
            txtVisibility = (TextView) view.findViewById(R.id.txt_visibility);
            txtIsActive = (TextView) view.findViewById(R.id.txt_is_active);
            txtBookDescription = (TextView) view.findViewById(R.id.txt_book_desc);
            txtPostedBefore = (TextView) view.findViewById(R.id.txt_posted_before_days);
            imgDelete = (ImageButton) view.findViewById(R.id.btn_delete);
            imgEdit = (ImageButton) view.findViewById(R.id.btn_edit);
        }
    }

    public MyPostListViewAdapter(@NonNull BaseActivity activity, int resource, ArrayList<Post> list) {
        super(activity, resource, list);

        this.mActivity = activity;
        this.mLayoutResource = resource;
        this.mPostList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = null;
        View view = convertView;

        if (view == null) {

            view = mActivity.getLayoutInflater().inflate(mLayoutResource, null);
            holder = new ViewHolder(view);
            view.setTag(holder);

        } else {

            holder = (ViewHolder) view.getTag();
        }

        if (position % 2 == 0) {

            holder.imgIndicator.setImageResource(redIndicatorDrawable);
        } else {
            holder.imgIndicator.setImageResource(greenIndicatorDrawable);
        }
        holder.txtBookName.setText("Some Book Title Name...");
        holder.txtBookAuthor.setText("by: Author Name");

        return view;
    }
}

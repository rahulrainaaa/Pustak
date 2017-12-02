package com.product.pustak.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.product.pustak.R;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.model.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyPostListViewAdapter extends ArrayAdapter<Post> {

    private BaseActivity mActivity;
    private ArrayList<Post> mPostList = null;
    private int mLayoutResource = -1;

    public static class ViewHolder {

        public ImageView imgIndicator = null;
        public TextView txtBookName = null;
        public TextView txtBookAuthor = null;
        public TextView txtRent = null;
        public TextView txtSell = null;
        public TextView txtVisibility = null;
        public TextView txtIsActive = null;
        public TextView txtBookDescription = null;
        public TextView txtPostedBeforeDays = null;
        public ImageButton imgDelete = null;
        public ImageButton imgEdit = null;

        public ViewHolder(View view, View.OnClickListener clickListener) {

            imgIndicator = (ImageView) view.findViewById(R.id.img_indicator);
            txtBookName = (TextView) view.findViewById(R.id.txt_book_title);
            txtBookAuthor = (TextView) view.findViewById(R.id.txt_book_author);
            txtRent = (TextView) view.findViewById(R.id.txt_rent);
            txtSell = (TextView) view.findViewById(R.id.txt_book_sell);
            txtVisibility = (TextView) view.findViewById(R.id.txt_visibility);
            txtIsActive = (TextView) view.findViewById(R.id.txt_is_active);
            txtBookDescription = (TextView) view.findViewById(R.id.txt_book_desc);
            txtPostedBeforeDays = (TextView) view.findViewById(R.id.txt_posted_before_days);
            imgDelete = (ImageButton) view.findViewById(R.id.btn_delete);
            imgEdit = (ImageButton) view.findViewById(R.id.btn_edit);

            imgDelete.setOnClickListener(clickListener);
            imgEdit.setOnClickListener(clickListener);
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

        Post post = mPostList.get(position);
        ViewHolder holder = null;
        View view = convertView;

        if (view == null) {

            view = mActivity.getLayoutInflater().inflate(mLayoutResource, null);
            holder = new ViewHolder(view, mClickListener);
            view.setTag(holder);

        } else {

            holder = (ViewHolder) view.getTag();
        }

        if (position % 2 == 0) {

            holder.imgIndicator.setBackgroundResource(R.drawable.border_shape_red_box);
        } else {
            holder.imgIndicator.setBackgroundResource(R.drawable.border_shape_green_box);
        }

        holder.imgDelete.setTag(position);
        holder.imgEdit.setTag(position);
        holder.txtBookName.setText(post.getName());
        holder.txtBookAuthor.setText("by: " + post.getAuthor());
        holder.txtBookDescription.setText(post.getDesc());
        holder.txtRent.setText(post.getAvail() + " @ Rs." + post.getRent() + "/day.");
        holder.txtSell.setText(post.getAvail() + " @ Rs." + post.getPrice() + "/-");

        boolean flagIndicator = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date postDate = sdf.parse(post.getDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(postDate);
            long diff = Calendar.getInstance().getTimeInMillis() - cal.getTimeInMillis();
            long weeks = diff / (7 * 24 * 60 * 60 * 1000);
            long days = diff / (24 * 60 * 60 * 1000);
            long hrs = diff / (60 * 60 * 1000);
            long min = diff / (60 * 1000);

            flagIndicator = days <= 7;

            if (weeks != 0) {

                holder.txtPostedBeforeDays.setText(weeks + "w");
            } else if (days != 0) {

                holder.txtPostedBeforeDays.setText(days + "d");
            } else if (hrs != 0) {

                holder.txtPostedBeforeDays.setText(hrs + "h");
            } else if (min != 0) {

                holder.txtPostedBeforeDays.setText(min + "m");
            } else {

                holder.txtPostedBeforeDays.setText("New");
            }
        } catch (ParseException e) {

            e.printStackTrace();
            holder.txtPostedBeforeDays.setText("err");
        }

        if (post.getActive()) {

            holder.txtIsActive.setText("Active");
            flagIndicator = true & flagIndicator;

        } else {

            holder.txtIsActive.setText("Inactive");
            flagIndicator = false;
        }

        holder.imgIndicator.setBackgroundColor(Color.parseColor(flagIndicator ? "#ff99cc00" : "#ffff4444"));

        return view;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int position = (int) view.getTag();
            switch (view.getId()) {

                case R.id.btn_delete:

                    deleteEvent(view, position);
                    break;

                case R.id.btn_edit:

                    editEvent(view, position);
                    break;
            }
        }

        private void deleteEvent(View view, final int position) {

            final Post post = mPostList.get(position);
            mPostList.remove(position);
            notifyDataSetChanged();

            Snackbar.make(view, "Deleted: " + post.getName() + ".", Snackbar.LENGTH_SHORT).setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mPostList.add(position, post);
                    notifyDataSetChanged();
                }
            }).show();

        }

        private void editEvent(View view, int position) {

            Toast.makeText(mActivity, "Edit " + position, Toast.LENGTH_SHORT).show();
        }

    };
}

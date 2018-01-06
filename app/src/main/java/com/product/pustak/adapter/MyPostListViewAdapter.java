package com.product.pustak.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.product.pustak.R;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.activity.derived.EditPostActivity;
import com.product.pustak.model.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyPostListViewAdapter extends ArrayAdapter<Post> {

    public static final String TAG = "MyPostListViewAdapter";

    private BaseActivity mActivity;
    private ArrayList<Post> mPostList = null;
    private ArrayList<DocumentSnapshot> mSnapshotList = null;
    private int mLayoutResource = -1;
    private boolean webProcessing = false;      // true = web API call in progress.
    private boolean webUpdated = false;          // true = have updated data.

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

        private void deleteEvent(final View view, final int position) {

            final Post deletedPost = mPostList.get(position);
            final DocumentSnapshot deletedSnapshot = mSnapshotList.get(position);
            mPostList.remove(position);
            mSnapshotList.remove(position);
            notifyDataSetChanged();

            Snackbar.make(view, "Deleted: " + deletedPost.getName() + ".", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    /**
                     * Code to insert the same Post with same data (Restore data).
                     */
                    FirebaseFirestore.getInstance()
                            .collection("posts")
                            .document(deletedSnapshot.getId())
                            .set(deletedPost)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mPostList.add(position, deletedPost);
                                    mSnapshotList.add(position, deletedSnapshot);
                                    notifyDataSetChanged();
                                    Toast.makeText(mActivity, "Post restored:\n" + deletedPost.getName(), Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(mActivity, "Unable to restored post", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                }
            }).show();

            /**
             * Code to remove Post.
             */
            FirebaseFirestore.getInstance()
                    .collection("posts")
                    .document(deletedSnapshot.getId())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void aVoid) {


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception e) {

                            mPostList.add(position, deletedPost);
                            mSnapshotList.add(position, deletedSnapshot);
                            notifyDataSetChanged();
                            Snackbar.make(view, "Unable to delete", Snackbar.LENGTH_SHORT).show();
                        }
                    });
        }

        private void editEvent(View view, int position) {

            Post post = mPostList.get(position);
            String documentReferenceId = mSnapshotList.get(position).getId();

            Intent intent = new Intent(mActivity, EditPostActivity.class);
            intent.putExtra("post", post);
            intent.putExtra("documentReferenceId", documentReferenceId);

            mActivity.startActivity(intent);


        }

    };

    public MyPostListViewAdapter(@NonNull BaseActivity activity, int resource, ArrayList<Post> list, ArrayList<DocumentSnapshot> snapshots) {
        super(activity, resource, list);

        this.mActivity = activity;
        this.mLayoutResource = resource;
        this.mPostList = list;
        this.mSnapshotList = snapshots;
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
        holder.txtRent.setText("Rent @ Rs." + post.getRent() + "/day.");
        holder.txtSell.setText("Sell @ Rs." + post.getPrice() + "/-");

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

    public boolean isWebProcessing() {

        return this.webProcessing;
    }

    public boolean isWebUpdated() {

        return this.webUpdated;
    }

    public void resetWebUpdated() {

    }

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

}

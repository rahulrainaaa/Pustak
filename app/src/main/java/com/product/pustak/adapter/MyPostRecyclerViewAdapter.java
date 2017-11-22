package com.product.pustak.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.product.pustak.R;
import com.product.pustak.model.Post;

import java.util.ArrayList;


public class MyPostRecyclerViewAdapter extends RecyclerView.Adapter<MyPostRecyclerViewAdapter.ItemHolder> {

    /**
     * @class ItemHolder
     * @desc {@link RecyclerView.ViewHolder} holder static class for Recycler View items.
     */
    public static class ItemHolder extends RecyclerView.ViewHolder {

        public CardView cardView;

        ItemHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    /**
     * private class Data members.
     */
    private ArrayList<Post> mPostList = null;
    private Activity mActivity = null;
    private View.OnClickListener mOnClickListener = null;
    private int mLayoutResourceId = -1;

    /**
     * @constructor CategoryRecyclerAdapter
     * @desc Constructor method for this class.
     */
    public MyPostRecyclerViewAdapter(Activity activity, int layoutResourceId, View.OnClickListener onClickListener, ArrayList<Post> postList) {
        this.mActivity = activity;
        this.mPostList = postList;
        this.mOnClickListener = onClickListener;
        this.mLayoutResourceId = layoutResourceId;
    }

    /**
     * {@link RecyclerView.Adapter} adapter class override methods.
     */
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(this.mLayoutResourceId, parent, false);

        ItemHolder itemHolder = new ItemHolder(itemView, mOnClickListener);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {

        switch (position % 8) {

            case 0:
                holder.cardView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_cell_a));
                break;
            case 1:
                holder.cardView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_cell_b));
                break;
            case 2:
                holder.cardView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_cell_c));
                break;
            case 3:
                holder.cardView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_cell_d));
                break;
            case 4:
                holder.cardView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_cell_e));
                break;
            case 5:
                holder.cardView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_cell_f));
                break;
            case 6:
                holder.cardView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_cell_g));
                break;
            case 7:
                holder.cardView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.bg_cell_h));
                break;
        }


    }

    @Override
    public int getItemCount() {

        return mPostList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
    }

}

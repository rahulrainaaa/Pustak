package com.product.pustak.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.product.pustak.activity.derived.DashboardActivity;
import com.product.pustak.holder.base.CellHolder;
import com.product.pustak.holder.derived.messages.ReceivedMessageCellHolder;
import com.product.pustak.holder.derived.messages.SentMessageCellHolder;

import java.util.ArrayList;

/**
 * Adapter class to show message (Sent/received) cells in recycler view.
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<CellHolder> {

    public static final String TAG = "MessageRecyclerViewAdapter";

    private DashboardActivity mActivity = null;
    private ArrayList<String> mMessageList = null;

    public static final int SENT_CELL = 1;
    public static final int RECEIVED_CELL = 2;

    public final int mSentMessageCell = -1;
    public final int mReceivedMessageCell = -2;

    public MessageRecyclerViewAdapter(DashboardActivity activity, ArrayList<String> list) {

        this.mActivity = activity;
        this.mMessageList = list;
    }

    @Override
    public CellHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == RECEIVED_CELL) {

            View view = LayoutInflater.from(parent.getContext()).inflate(mReceivedMessageCell, parent, false);
            ReceivedMessageCellHolder holder = new ReceivedMessageCellHolder(view, null);
            holder.setIsRecyclable(true);
            return holder;

        } else if(viewType == SENT_CELL) {

            View view = LayoutInflater.from(parent.getContext()).inflate(mSentMessageCell, parent, false);
            SentMessageCellHolder holder = new SentMessageCellHolder(view, null);
            holder.setIsRecyclable(true);
            return holder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(CellHolder cellHolder, int position) {

        if(getItemViewType(position) == RECEIVED_CELL) {

            ReceivedMessageCellHolder holder = (ReceivedMessageCellHolder) cellHolder;
            holder.setTag(position);
            holder.setPositionTag(position);
            holder.setData(mMessageList.get(position));

        } else if (getItemViewType(position) == SENT_CELL) {

            SentMessageCellHolder holder = (SentMessageCellHolder) cellHolder;
            holder.setTag(position);
            holder.setPositionTag(position);
            holder.setData(mMessageList.get(position));

        }
    }

    @Override
    public int getItemCount() {

        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return RECEIVED_CELL;
    }
}

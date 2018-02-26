package com.product.pustak.holder.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Holder base class for RecyclerView.
 */
public class CellHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "CellHolder";

    private int tag = -1;

    protected CellHolder(View itemView, View.OnClickListener listener) {

        super(itemView);
    }

    public final int getTag() {

        return this.tag;
    }

    public final void setTag(int tag) {

        this.tag = tag;
    }

}

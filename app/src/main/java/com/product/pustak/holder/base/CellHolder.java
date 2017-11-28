package com.product.pustak.holder.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CellHolder extends RecyclerView.ViewHolder {

    public static final String TAG = "CellHolder";

    private int tag = -1;

    public CellHolder(View itemView, View.OnClickListener listener) {

        super(itemView);
    }

    public final void setTag(int tag) {

        this.tag = tag;
    }

    public final int getTag() {

        return this.tag;
    }

}

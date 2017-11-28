package com.product.pustak.holder.derived;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.product.pustak.R;
import com.product.pustak.holder.base.CellHolder;

public class CollapsedCellHolder extends CellHolder {


    public CardView cardView;
    public LinearLayout headerLayout;

    public CollapsedCellHolder(View itemView, View.OnClickListener onClickListener) {

        super(itemView, onClickListener);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
        headerLayout = (LinearLayout) itemView.findViewById(R.id.cell_collapsed_header_layout);
        headerLayout.setOnClickListener(onClickListener);
    }

    public void setPositionTag(int position) {

        headerLayout.setTag(position);
    }

}

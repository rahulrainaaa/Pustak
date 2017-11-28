package com.product.pustak.holder.derived;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.product.pustak.R;
import com.product.pustak.holder.base.CellHolder;

public class ExpandedCellHolder extends CellHolder {

    public CardView cardView;
    public LinearLayout headerLayout;
    public ImageButton btnCollapsing;

    public ExpandedCellHolder(View itemView, View.OnClickListener onClickListener) {

        super(itemView, onClickListener);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
        headerLayout = (LinearLayout) itemView.findViewById(R.id.cell_expanded_header_layout);
        btnCollapsing = (ImageButton) itemView.findViewById(R.id.btn_collapsing);

        btnCollapsing.setOnClickListener(onClickListener);
        headerLayout.setOnClickListener(onClickListener);
    }

    public void setPositionTag(int position) {

        headerLayout.setTag(position);
        btnCollapsing.setTag(position);
    }

}

package com.product.pustak.holder.derived;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.product.pustak.R;
import com.product.pustak.holder.base.CellHolder;

public class ExpandedCellHolder extends CellHolder {

    public CardView cardView;
    public LinearLayout layoutHide;
    public ImageButton btnExpanding;

    public ExpandedCellHolder(View itemView, View.OnClickListener onClickListener) {

        super(itemView, onClickListener);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
        layoutHide = (LinearLayout) itemView.findViewById(R.id.layout_hide);
        btnExpanding = (ImageButton) itemView.findViewById(R.id.btn_expanding);
        btnExpanding.setOnClickListener(onClickListener);
    }


}

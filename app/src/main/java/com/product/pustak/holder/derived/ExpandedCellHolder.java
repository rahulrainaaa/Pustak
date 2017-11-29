package com.product.pustak.holder.derived;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.product.pustak.R;
import com.product.pustak.holder.base.CellHolder;
import com.product.pustak.model.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExpandedCellHolder extends CellHolder {

    public CardView cardView;
    public LinearLayout headerLayout;
    public ImageButton btnCollapsing;

    public TextView txtAvailability;
    public TextView txtPriceRent;
    public TextView txtBookName;
    public TextView txtBookAuthor;
    public TextView txtBookEdition;
    public TextView txtBookPublication;
    public TextView txtPostedBeforeDays;

    public ImageButton ibtnCall;
    public ImageButton ibtnMessage;
    public ImageButton ibtnEmail;
    public ImageButton ibtnLocation;

    public TextView txtSubject;
    public TextView txtBookType;
    public TextView txtCondition;
    public TextView txtDescription;

    public ExpandedCellHolder(View itemView, View.OnClickListener onClickListener) {

        super(itemView, onClickListener);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
        headerLayout = (LinearLayout) itemView.findViewById(R.id.cell_expanded_header_layout);
        btnCollapsing = (ImageButton) itemView.findViewById(R.id.btn_collapsing);

        txtAvailability = (TextView) itemView.findViewById(R.id.txt_availability);
        txtPriceRent = (TextView) itemView.findViewById(R.id.txt_price_rent);
        txtBookName = (TextView) itemView.findViewById(R.id.txt_book_title);
        txtBookAuthor = (TextView) itemView.findViewById(R.id.txt_book_author);
        txtBookEdition = (TextView) itemView.findViewById(R.id.txt_book_edition);
        txtBookPublication = (TextView) itemView.findViewById(R.id.txt_book_publication);
        txtPostedBeforeDays = (TextView) itemView.findViewById(R.id.txt_posted_before_days);

        txtSubject = (TextView) itemView.findViewById(R.id.txt_subject);
        txtBookType = (TextView) itemView.findViewById(R.id.txt_book_type);
        txtCondition = (TextView) itemView.findViewById(R.id.txt_condition);
        txtDescription = (TextView) itemView.findViewById(R.id.txt_description);

        ibtnCall = (ImageButton) itemView.findViewById(R.id.btn_call);
        ibtnMessage = (ImageButton) itemView.findViewById(R.id.btn_message);
        ibtnEmail = (ImageButton) itemView.findViewById(R.id.btn_email);
        ibtnLocation = (ImageButton) itemView.findViewById(R.id.btn_location);

        ibtnCall.setOnClickListener(onClickListener);
        ibtnMessage.setOnClickListener(onClickListener);
        ibtnEmail.setOnClickListener(onClickListener);
        ibtnLocation.setOnClickListener(onClickListener);
        btnCollapsing.setOnClickListener(onClickListener);
        headerLayout.setOnClickListener(onClickListener);
    }

    public void setPositionTag(int position, int position1) {

        headerLayout.setTag(position);
        btnCollapsing.setTag(position);
        ibtnCall.setTag(position);
        ibtnMessage.setTag(position);
        ibtnEmail.setTag(position);
        ibtnLocation.setTag(position1);
    }


    public void setData(Post post) {

        if (post.getAvail().contains("Rent")) {

            txtAvailability.setText("R");
            txtPriceRent.setText("Rs." + post.getRent() + "/day");
        } else {
            txtAvailability.setText("S");
            txtPriceRent.setText("Rs." + post.getRent() + "/-");
        }

        txtBookName.setText(post.getName());
        txtBookAuthor.setText("by: " + post.getAuthor());
        txtBookEdition.setText("Edition: " + post.getEdition());
        txtBookPublication.setText(post.getPub());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date postDate = sdf.parse(post.getDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(postDate);
            long diff = Calendar.getInstance().getTimeInMillis() - cal.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000);
            long hrs = diff / (60 * 60 * 1000);
            long min = diff / (60 * 1000);
            if (days != 0) {

                txtPostedBeforeDays.setText(days + "d");
            } else if (hrs != 0) {

                txtPostedBeforeDays.setText(hrs + "h");
            } else if (min != 0) {

                txtPostedBeforeDays.setText(min + "m");
            } else {

                txtPostedBeforeDays.setText("New");
            }
        } catch (ParseException e) {

            e.printStackTrace();
            txtPostedBeforeDays.setText("err");
        }

        txtSubject.setText(post.getSub());
        txtBookType.setText(post.getType());
        String[] arrCondition = cardView.getContext().getResources().getStringArray(R.array.condition);
        txtCondition.setText(arrCondition[post.getCond()]);
        txtDescription.setText(post.getDesc());

    }

}

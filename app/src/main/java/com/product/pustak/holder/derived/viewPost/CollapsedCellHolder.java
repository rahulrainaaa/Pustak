package com.product.pustak.holder.derived.viewPost;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.product.pustak.R;
import com.product.pustak.holder.base.CellHolder;
import com.product.pustak.model.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CollapsedCellHolder extends CellHolder {


    public CardView cardView;
    public LinearLayout headerLayout;

    public TextView txtAvailability;
    public TextView txtPriceRent;
    public TextView txtBookName;
    public TextView txtBookAuthor;
    public TextView txtBookEdition;
    public TextView txtBookPublication;
    public TextView txtPostedBeforeDays;

    public CollapsedCellHolder(View itemView, View.OnClickListener onClickListener) {

        super(itemView, onClickListener);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
        headerLayout = (LinearLayout) itemView.findViewById(R.id.cell_collapsed_header_layout);

        txtAvailability = (TextView) itemView.findViewById(R.id.txt_availability);
        txtPriceRent = (TextView) itemView.findViewById(R.id.txt_price_rent);
        txtBookName = (TextView) itemView.findViewById(R.id.txt_book_title);
        txtBookAuthor = (TextView) itemView.findViewById(R.id.txt_book_author);
        txtBookEdition = (TextView) itemView.findViewById(R.id.txt_book_edition);
        txtBookPublication = (TextView) itemView.findViewById(R.id.txt_book_publication);
        txtPostedBeforeDays = (TextView) itemView.findViewById(R.id.txt_posted_before_days);

        headerLayout.setOnClickListener(onClickListener);
    }

    public void setPositionTag(int position) {

        headerLayout.setTag(position);
    }

    public void setData(Post post) {

        if (post.getAvail().contains("Rent")) {

            txtAvailability.setText("R");
            txtPriceRent.setText("Rs." + post.getRent() + "/day");
        } else {
            txtAvailability.setText("S");
            txtPriceRent.setText("Rs." + post.getPrice() + "/-");
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
            long weeks = diff / (7 * 24 * 60 * 60 * 1000);
            long days = diff / (24 * 60 * 60 * 1000);
            long hrs = diff / (60 * 60 * 1000);
            long min = diff / (60 * 1000);

            if (weeks != 0) {

                txtPostedBeforeDays.setText(weeks + "w");
            } else if (days != 0) {

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
    }

}

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

/**
 * Collapsed cell holder class for {@link android.support.v7.widget.RecyclerView} on {@link com.product.pustak.fragment.derived.ViewPostFragment}.
 */
public class CollapsedCellHolder extends CellHolder {

    private static final String TAG = "CollapsedCellHolder";

    /**
     * class data member(s).
     */
    public final CardView cardView;

    private final LinearLayout headerLayout;
    private final TextView txtAvailability;
    private final TextView txtPriceRent;
    private final TextView txtBookName;
    private final TextView txtBookAuthor;
    private final TextView txtBookEdition;
    private final TextView txtBookPublication;
    private final TextView txtPostedBeforeDays;

    public CollapsedCellHolder(View itemView, View.OnClickListener onClickListener) {

        super(itemView, onClickListener);
        cardView = itemView.findViewById(R.id.card_view);
        headerLayout = itemView.findViewById(R.id.cell_collapsed_header_layout);

        txtAvailability = itemView.findViewById(R.id.txt_availability);
        txtPriceRent = itemView.findViewById(R.id.txt_price_rent);
        txtBookName = itemView.findViewById(R.id.txt_book_title);
        txtBookAuthor = itemView.findViewById(R.id.txt_book_author);
        txtBookEdition = itemView.findViewById(R.id.txt_book_edition);
        txtBookPublication = itemView.findViewById(R.id.txt_book_publication);
        txtPostedBeforeDays = itemView.findViewById(R.id.txt_posted_before_days);

        headerLayout.setOnClickListener(onClickListener);
    }

    public void setPositionTag(int position) {

        headerLayout.setTag(position);
    }

    /**
     * Method to public post data on the cell.
     *
     * @param post {@link Post} model object reference.
     */
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


        // calculate the duration since when the post is added.
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

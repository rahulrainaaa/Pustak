package com.product.pustak.adapter;


import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.product.pustak.R;

/**
 * Adapter class for {@link android.widget.Spinner} to handle the dropdown text.
 */
public class WorkSpinnerAdapter extends ArrayAdapter<String> {

    public static final String TAG = "WorkSpinnerAdapter";

    /**
     * Class private data member(s).
     */
    private Activity mActivity = null;
    private int mLayout = -1;
    private int mDrawableResource = R.drawable.icon_work;
    private String[] mArrItems = null;

    public WorkSpinnerAdapter(@NonNull Activity activity, int layout, int drawableResource, @NonNull String[] arr) {
        super(activity, layout, arr);

        this.mActivity = activity;
        this.mLayout = layout;
        this.mArrItems = arr;
        this.mDrawableResource = drawableResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView textView;

        if (convertView != null) {

            textView = (TextView) convertView;

        } else {

            textView = (TextView) mActivity.getLayoutInflater().inflate(R.layout.item_spinner_textview, parent, false);
            textView.setTextColor(Color.WHITE);
            textView.setCompoundDrawablesWithIntrinsicBounds(mDrawableResource, 0, 0, 0);
        }

        textView.setText(mArrItems[position].toString());

        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView textView;

        if (convertView == null) {

            textView = (TextView) mActivity.getLayoutInflater().inflate(R.layout.item_spinner_textview, null);
            textView.setTextColor(Color.BLACK);
            textView.setPadding(20, 20, 4, 20);
        } else {

            textView = (TextView) convertView;
        }

        textView.setText(mArrItems[position].toString());
        return textView;
    }
}

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

public class WorkSpinnerAdapter extends ArrayAdapter<String> {

    public static final String TAG = "WorkSpinnerAdapter";

    /**
     * Class private data member(s).
     */
    private Activity mActivity = null;
    private int mLayout = -1;
    private String[] mArrItems = null;

    public WorkSpinnerAdapter(@NonNull Activity activity, int layout, @NonNull String[] arr) {
        super(activity, layout, arr);

        this.mActivity = activity;
        this.mLayout = layout;
        mArrItems = arr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView textView = null;

        if (convertView != null) {

            textView = (TextView) textView;

        } else {

            textView = (TextView) mActivity.getLayoutInflater().inflate(R.layout.item_spinner_textview, null);
        }

        textView.setTextColor(Color.WHITE);
        textView.setText(mArrItems[position].toString());
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_work, 0, 0, 0);
        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView textView = null;

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

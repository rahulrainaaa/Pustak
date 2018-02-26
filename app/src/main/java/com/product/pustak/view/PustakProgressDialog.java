package com.product.pustak.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.product.pustak.R;

/**
 * ProgressBar dialog box to show the processing icon on foreground and freeze UI.
 */
public class PustakProgressDialog {

    public static final String TAG = "PustakProgressDialog";

    /**
     * Private class data members.
     */
    private Activity mActivity = null;
    private AlertDialog mAlertDialog = null;
    private TextView mTextView = null;
    private boolean isShowing = false;
    private boolean isCreated = false;

    public PustakProgressDialog(Activity activity) {

        this.mActivity = activity;
    }

    /**
     * Method to create the ProgressDialog only first time (if called) in an Activity.
     */
    private void createFirstTime() {

        View mView = mActivity.getLayoutInflater().inflate(R.layout.layout_progress_dialog, null);
        mTextView = mView.findViewById(R.id.process_text);
        ProgressBar mProgress = mView.findViewById(R.id.progress_processing);
        mAlertDialog = new AlertDialog.Builder(mActivity).create();
        mAlertDialog.setView(mView);
        //noinspection ConstantConditions
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.setCancelable(false);
        isCreated = true;
        mAlertDialog.show();
    }

    /**
     * Method to setText in progress dialog.
     *
     * @param text {@link String} message to be sent.
     */
    private void setMessage(@SuppressWarnings("SameParameterValue") String text) {

        String mText = "";
        if (text == null) {

            mText = "";
        }
        assert text != null;
        mText = text.trim();
        mTextView.setText("" + mText.trim());
    }

    /**
     * Method to show the over activity.
     */
    public void show() {

        // Create it only first time and reuse it.
        if (!isCreated) {

            createFirstTime();
        }

        if (!isShowing) {

            mAlertDialog.show();
        }
        isShowing = true;
    }

    /**
     * Method to hide the progress dialog from the activity, but keep it for next use.
     */
    public void hide() {

        setMessage("");
        if (mAlertDialog != null) {

            mAlertDialog.hide();
        }
        isShowing = false;
    }

    /**
     * Method to dismiss and release the memory.
     */
    public void dismiss() {

        if (mAlertDialog != null) {

            mAlertDialog.dismiss();
        }
        isCreated = false;
        isShowing = false;
    }

}

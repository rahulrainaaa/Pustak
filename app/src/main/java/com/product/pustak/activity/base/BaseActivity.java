package com.product.pustak.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.product.pustak.view.PustakProgressDialog;

/**
 * Abstract base class for activity (enforce project standards).
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = "BaseActivity";

    /**
     * Class private data member(s).
     */
    private PustakProgressDialog mDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDialog = new PustakProgressDialog(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mDialog.dismiss();
    }

    /**
     * Show Progress dialog and freeze UI screen.
     */
    public void showProgressDialog() {

        mDialog.show();
    }

    /**
     * hide progress dialog and resume UI screen interaction.
     */
    public void hideProgressDialog() {

        mDialog.hide();
    }

}

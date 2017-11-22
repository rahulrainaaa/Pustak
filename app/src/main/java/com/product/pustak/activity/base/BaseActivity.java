package com.product.pustak.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.product.pustak.view.PustakProgressDialog;

public abstract class BaseActivity extends AppCompatActivity {

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

    public void showProgressDialog() {

        mDialog.show();
    }

    public void hideProgressDialog() {

        mDialog.hide();
    }

}

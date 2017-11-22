package com.product.pustak.activity.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.product.pustak.view.PustakProgressDialog;

public abstract class BaseActivity extends AppCompatActivity {

    private PustakProgressDialog mDialog = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

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

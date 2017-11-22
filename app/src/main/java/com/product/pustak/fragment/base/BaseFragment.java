package com.product.pustak.fragment.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.product.pustak.activity.derived.DashboardActivity;
import com.product.pustak.view.PustakProgressDialog;

public abstract class BaseFragment extends Fragment {

    private PustakProgressDialog mDialog = null;

    public DashboardActivity getDashboardActivity() {

        return (DashboardActivity) getActivity();
    }

    public enum FragmentType {ADD_POST, MESSAGE, MY_POST, PROFILE, VIEW_POST, FAILURE}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDialog = new PustakProgressDialog(getActivity());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mDialog.dismiss();

    }

    private void showProgressDilaog() {

    }

    private void hideProgressDialog() {

    }
}

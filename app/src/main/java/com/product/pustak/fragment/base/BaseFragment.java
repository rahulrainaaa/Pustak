package com.product.pustak.fragment.base;


import android.content.Intent;
import android.support.v4.app.Fragment;

import com.product.pustak.activity.derived.DashboardActivity;

public abstract class BaseFragment extends Fragment {


    protected final DashboardActivity getDashboardActivity() {

        return (DashboardActivity) getActivity();
    }

    protected void showProgressBar() {

        try {
            getDashboardActivity().showProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void hideProgressBar() {
        try {
            getDashboardActivity().hideProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void loadFragment(BaseFragment.FragmentType fragmentType) {

        getDashboardActivity().loadFragment(fragmentType);
    }

    public enum FragmentType {ADD_POST, MESSAGE, MY_POST, PROFILE, VIEW_POST, FAILURE}

}

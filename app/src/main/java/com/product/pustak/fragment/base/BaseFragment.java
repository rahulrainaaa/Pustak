package com.product.pustak.fragment.base;


import android.support.v4.app.Fragment;

import com.product.pustak.activity.derived.DashboardActivity;

public abstract class BaseFragment extends Fragment {


    protected DashboardActivity getDashboardActivity() {

        return (DashboardActivity) getActivity();
    }

    protected void showProgressBar() {

        getDashboardActivity().showProgressDialog();
    }

    protected void hideProgressBar() {

        getDashboardActivity().hideProgressDialog();
    }

    protected void loadFragment(BaseFragment.FragmentType fragmentType) {

        getDashboardActivity().loadFragment(fragmentType);
    }

    public enum FragmentType {ADD_POST, MESSAGE, MY_POST, PROFILE, VIEW_POST, FAILURE}


}

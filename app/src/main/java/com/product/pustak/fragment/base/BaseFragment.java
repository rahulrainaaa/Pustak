package com.product.pustak.fragment.base;


import android.support.v4.app.Fragment;

import com.product.pustak.activity.derived.DashboardActivity;

/**
 * Abstract base class for all {@link android.app.Fragment} shown in {@link DashboardActivity}.
 * Enforce application policy standards.
 */
public abstract class BaseFragment extends Fragment {

    public static final String TAG = "BaseFragment";

    protected final DashboardActivity getDashboardActivity() {

        return (DashboardActivity) getActivity();
    }

    /**
     * Method to show progress bar and freeze UI interaction.
     */
    protected void showProgressBar() {

        try {
            getDashboardActivity().showProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to hide progress bar and resume with screen interaction.
     */
    protected void hideProgressBar() {
        try {
            getDashboardActivity().hideProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fragmentType
     */
    protected void loadFragment(BaseFragment.FragmentType fragmentType) {

        getDashboardActivity().loadFragment(fragmentType);
    }

    /**
     * Fragment types that can be loaded into {@link DashboardActivity}.
     */
    public enum FragmentType {
        ADD_POST, MESSAGE, MY_POST, PROFILE, VIEW_POST, FAILURE
    }

}

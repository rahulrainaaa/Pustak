package com.product.pustak.fragment.base;


import android.support.v4.app.Fragment;

import com.product.pustak.activity.derived.DashboardActivity;

/**
 * Abstract base class for all {@link android.app.Fragment} shown in {@link DashboardActivity}.
 * Enforce application policy standards.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

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
     * Method to load Fragment of given type (enum).
     *
     * @param fragmentType enum
     */
    protected void loadFragment(@SuppressWarnings("SameParameterValue") BaseFragment.FragmentType fragmentType) {

        getDashboardActivity().loadFragment(fragmentType);
    }

    /**
     * Fragment types that can be loaded into {@link DashboardActivity}.
     */
    public enum FragmentType {
        ADD_POST, MY_POST, PROFILE, VIEW_POST, FAILURE;

        public FragmentType getFragType(int type) {
            switch (type) {

                case 1:
                    return MY_POST;
                case 2:
                    return ADD_POST;
                case 3:
                    return PROFILE;
                case 4:
                    return VIEW_POST;
                case 6:
                    return FAILURE;
            }
            return FAILURE;
        }


    }

}

package com.product.pustak.fragment.derived;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.product.pustak.fragment.base.BaseFragment;

/**
 * Fragment to show the application failure or other issue.
 */
public class FailureFragment extends BaseFragment {

    public static final String TAG = "FailureFragment";

    public static FailureFragment getInstance() {

        FailureFragment fragment = new FailureFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "onCreate FailureFragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getActivity(), "onDetach FailureFragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(), "onDestroy FailureFragment", Toast.LENGTH_SHORT).show();
    }
}

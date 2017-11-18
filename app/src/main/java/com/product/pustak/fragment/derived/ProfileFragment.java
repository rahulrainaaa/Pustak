package com.product.pustak.fragment.derived;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.product.pustak.fragment.base.BaseFragment;

public class ProfileFragment extends BaseFragment {

    public static ProfileFragment getInstance() {

        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "onCreate ProfileFragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getActivity(), "onDetach ProfileFragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(), "onDestroy ProfileFragment", Toast.LENGTH_SHORT).show();
    }

}

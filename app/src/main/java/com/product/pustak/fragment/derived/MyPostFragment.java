package com.product.pustak.fragment.derived;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.product.pustak.fragment.base.BaseFragment;

public class MyPostFragment extends BaseFragment {

    public static MyPostFragment getInstance() {

        MyPostFragment fragment = new MyPostFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "onCreate MyPostFragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getActivity(), "onDetach MyPostFragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(), "onDestroy MyPostFragment", Toast.LENGTH_SHORT).show();
    }
}

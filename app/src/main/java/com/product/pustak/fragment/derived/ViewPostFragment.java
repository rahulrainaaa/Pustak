package com.product.pustak.fragment.derived;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.product.pustak.fragment.base.BaseFragment;

public class ViewPostFragment extends BaseFragment {

    public static ViewPostFragment getInstance() {

        ViewPostFragment fragment = new ViewPostFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "onCreate ViewPostFragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getActivity(), "onDetach ViewPostFragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(), "onDestroy ViewPostFragment", Toast.LENGTH_SHORT).show();
    }

}

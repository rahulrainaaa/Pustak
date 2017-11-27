package com.product.pustak.fragment.derived;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.product.pustak.R;
import com.product.pustak.adapter.MyPostRecyclerViewAdapter;
import com.product.pustak.fragment.base.BaseFragment;
import com.product.pustak.model.Post;

import java.util.ArrayList;

public class MyPostFragment extends BaseFragment {

    public static MyPostFragment getInstance() {

        MyPostFragment fragment = new MyPostFragment();
        return fragment;
    }


}

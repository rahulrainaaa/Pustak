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

    private RecyclerView mRecyclerView = null;
    private ArrayList<Post> mPostList = null;
    private MyPostRecyclerViewAdapter mAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_my_post, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mPostList = new ArrayList<Post>();

        for (int i = 0; i < 30; i++) {
            mPostList.add(new Post());
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MyPostRecyclerViewAdapter(getActivity(), R.layout.item_rv_mypost, null, mPostList);
        mRecyclerView.setAdapter(mAdapter);

        return view;

    }
}

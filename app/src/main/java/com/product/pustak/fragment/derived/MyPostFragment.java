package com.product.pustak.fragment.derived;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.product.pustak.R;
import com.product.pustak.adapter.MyPostListViewAdapter;
import com.product.pustak.fragment.base.BaseFragment;
import com.product.pustak.model.Post;

import java.util.ArrayList;

public class MyPostFragment extends BaseFragment {

    public static final String TAG = "MyPostFragment";

    public static MyPostFragment getInstance() {

        MyPostFragment fragment = new MyPostFragment();
        return fragment;
    }

    private ListView listView = null;
    private ArrayList<Post> mPostList = new ArrayList<>();
    private MyPostListViewAdapter mAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        for (int i = 0; i < 50; i++) {

            mPostList.add(new Post());
        }

        listView = (ListView) inflater.inflate(R.layout.frag_my_post, null);

        mAdapter = new MyPostListViewAdapter(getDashboardActivity(), R.layout.item_list_view_my_post, mPostList);
        listView.setAdapter(mAdapter);

        return listView;
    }
}

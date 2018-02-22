package com.product.pustak.fragment.derived;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.product.pustak.R;
import com.product.pustak.adapter.MyPostListViewAdapter;
import com.product.pustak.fragment.base.BaseFragment;
import com.product.pustak.handler.BaseHandler.BaseHandler;
import com.product.pustak.handler.PostFetchedListener.PostListFetchedListener;
import com.product.pustak.handler.PostHandler.PostHandler;
import com.product.pustak.model.Post;

import java.util.ArrayList;

/**
 * Fragment to show list of {@link Post} added my signed in user.
 */
public class MyPostFragment extends BaseFragment {

    public static final String TAG = "MyPostFragment";
    public boolean refreshFlag = true;      // true = need to refresh data, false = data already up-to-date.

    /**
     * Class private data member(s).
     */
    private ListView listView = null;
    private ArrayList<Post> mPostList = new ArrayList<>();
    private ArrayList<DocumentSnapshot> mSnapshotList = new ArrayList<>();
    private MyPostListViewAdapter mAdapter = null;

    public static MyPostFragment getInstance() {

        MyPostFragment fragment = new MyPostFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        listView = (ListView) inflater.inflate(R.layout.frag_my_post, null);
        mAdapter = new MyPostListViewAdapter(getDashboardActivity(), this, R.layout.item_list_view_my_post, mPostList, mSnapshotList);
        listView.setAdapter(mAdapter);

        return listView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (refreshFlag) {

            refreshFlag = false;
            mPostList.clear();
            mSnapshotList.clear();
            /**
             * Handler to get list of all my posts.
             */
            PostHandler postHandler = new PostHandler(getDashboardActivity());
            postHandler.fetchMyPostList(getDashboardActivity().getUser().getMobile(), mPostList, mSnapshotList, new PostListFetchedListener() {
                @Override
                public void postListFetchedCallback(ArrayList<Post> list, ArrayList<DocumentSnapshot> snapshots, BaseHandler.CODE code, String message) {

                    Toast.makeText(getContext(), list.size() + " Post", Toast.LENGTH_SHORT).show();
                    switch (code) {

                        case SUCCESS:

                            mAdapter.notifyDataSetChanged();
                            break;

                    }
                }
            }, true);

        }
    }
}

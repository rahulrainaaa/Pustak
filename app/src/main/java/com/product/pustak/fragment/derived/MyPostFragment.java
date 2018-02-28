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
import com.product.pustak.handler.PostHandler.PostHandler;
import com.product.pustak.model.Post;
import com.product.pustak.utils.CacheUtils;

import java.util.ArrayList;

/**
 * Fragment to show list of {@link Post} added my signed in user.
 */
public class MyPostFragment extends BaseFragment {

    public static final String TAG = "MyPostFragment";
    private final ArrayList<Post> mPostList = new ArrayList<>();
    private final ArrayList<DocumentSnapshot> mSnapshotList = new ArrayList<>();
    public boolean refreshFlag = true;      // true = need to refresh data, false = data already up-to-date.
    private MyPostListViewAdapter mAdapter = null;

    public static MyPostFragment getInstance() {

        return new MyPostFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@SuppressWarnings("NullableProblems") LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ListView listView = (ListView) inflater.inflate(R.layout.frag_my_post, container, false);
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

            // Handler to get list of all my posts.
            PostHandler postHandler = new PostHandler(getDashboardActivity());
            postHandler.fetchMyPostList(getDashboardActivity().getUser().getMobile(), mPostList, mSnapshotList, (list, snapshots, code, message) -> {

                Toast.makeText(getContext(), list.size() + " Post", Toast.LENGTH_SHORT).show();
                switch (code) {

                    case SUCCESS:

                        mAdapter.notifyDataSetChanged();
                        CacheUtils.setTotalPost(getContext(), list.size());
                        if (list.size() == 0) {

                            getDashboardActivity().loadFailureFragment(getString(R.string.not_posted));
                        }
                        break;

                }
            }, true);

        }
    }
}

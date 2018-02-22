package com.product.pustak.fragment.derived;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.product.pustak.R;
import com.product.pustak.adapter.ViewPostRecyclerViewAdapter;
import com.product.pustak.fragment.base.BaseFragment;
import com.product.pustak.model.Post;
import com.product.pustak.utils.RemoteConfigUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Fragment class to view {@link Post} (buyer or borrower).
 */
public class ViewPostFragment extends BaseFragment {

    public static final String TAG = "ViewPostFragment";

    /**
     * Class private data member(s).
     */
    private FirebaseFirestore db = null;
    /**
     * Class private UI Object(s).
     */
    private LinearLayoutManager mLinearLayoutManager = null;
    private RecyclerView mRecyclerView = null;
    private ArrayList<Post> mPostList = new ArrayList<>();
    private ArrayList<DocumentSnapshot> mSnapshotList = new ArrayList<>();
    private ViewPostRecyclerViewAdapter mAdapter = null;
    private DocumentSnapshot lastVisibleDocument = null;
    private boolean PROCESSING_REFRESH = false;

    /**
     * Recycler view scroll listener.
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            mLinearLayoutManager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
            int firstVisiblePosition = mLinearLayoutManager.findFirstVisibleItemPosition();
            int lastVisiblePosition = mLinearLayoutManager.findLastVisibleItemPosition();

            if (lastVisiblePosition == mPostList.size() - 1) {

                refreshList();
            }
        }
    };

    public static ViewPostFragment getInstance() {

        ViewPostFragment fragment = new ViewPostFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.frag_post, container, false);
        db = FirebaseFirestore.getInstance();

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ViewPostRecyclerViewAdapter(getDashboardActivity(), mPostList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        refreshList();
        return mRecyclerView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_empty, menu);
        //MenuItem searchItem = menu.findItem(R.id.item_search);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_search:

                Toast.makeText(getActivity(), "Search selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item_filter:

//                menuFilterSelected();
                break;
        }
        return true;
    }

    /**
     * Method to fetch all the posts based on query parameter(s).
     */
    private void refreshList() {

        // 1. Check if already processing. Thread safe.
        if (PROCESSING_REFRESH) {
            return;
        } else {
            PROCESSING_REFRESH = true;
        }

        CollectionReference collectionReference = db.collection("posts");

        // 2. Apply filter for date, to avoid fetching expired post(s).
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curDate = dateFormat.format(new Date());

        Query query = collectionReference
                .whereGreaterThanOrEqualTo("expiry", curDate.trim())
                .orderBy("expiry", Query.Direction.DESCENDING);

        // 3. Apply limit to result set.
        long limit = (Long) RemoteConfigUtils.getValue(RemoteConfigUtils.REMOTE.PAGE_LIMIT);

        if (lastVisibleDocument == null) {

            showProgressBar();
            query = query.limit(limit);

        } else {

            query = query.startAfter(lastVisibleDocument).limit(limit);
        }

        // 4. Now get data from database based on the query performed.
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                hideProgressBar();

                if (task.isSuccessful()) {      // Fetched posts done.

                    // Remove scroll listener on recycler view in case there is no result set any more to be fetched.
                    if (task.getResult().getDocuments().size() == 0) {

                        mRecyclerView.removeOnScrollListener(mOnScrollListener);

                    } else {

                        lastVisibleDocument = task.getResult().getDocuments().get(task.getResult().getDocuments().size() - 1);
                    }

                    // Parse the document snapshots into post array and notify change in recycler view.
                    for (DocumentSnapshot document : task.getResult()) {

                        mSnapshotList.add(document);
                        Post post = document.toObject(Post.class);
                        mPostList.add(post);
//                        mAdapter.notifyItemInserted(mPostList.size() -  1);
                    }
                    mAdapter.notifyDataSetChanged();
                    PROCESSING_REFRESH = false;

                } else {                        // Fetching post failed.

                    Toast.makeText(getActivity(), "Failed in fetching posts", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}

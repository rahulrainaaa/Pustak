package com.product.pustak.fragment.derived;

import android.os.Bundle;
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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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

    private static final String TAG = "ViewPostFragment";
    private final ArrayList<Post> mPostList = new ArrayList<>();
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final ArrayList<DocumentSnapshot> mSnapshotList = new ArrayList<>();
    /**
     * Class private data member(s).
     */
    private FirebaseFirestore db = null;
    /**
     * Class private UI Object(s).
     */
    private LinearLayoutManager mLinearLayoutManager = null;
    private RecyclerView mRecyclerView = null;
    private ViewPostRecyclerViewAdapter mAdapter = null;
    private DocumentSnapshot lastVisibleDocument = null;
    private boolean PROCESSING_REFRESH = false;

    /**
     * Recycler view scroll listener.
     */
    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            mLinearLayoutManager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
            //noinspection unused
            int firstVisiblePosition = mLinearLayoutManager.findFirstVisibleItemPosition();
            int lastVisiblePosition = mLinearLayoutManager.findLastVisibleItemPosition();

            if (lastVisiblePosition == mPostList.size() - 1) {

                refreshList();
            }
        }
    };

    public static ViewPostFragment getInstance() {

        return new ViewPostFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@SuppressWarnings("NullableProblems") LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.frag_post, container, false);
        db = FirebaseFirestore.getInstance();

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ViewPostRecyclerViewAdapter(getDashboardActivity(), mPostList, mRecyclerView);
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

        // 1. Check if already processing. Make it thread safe.
        if (PROCESSING_REFRESH) {
            return;
        } else {
            PROCESSING_REFRESH = true;
        }

        // 2. Get collection reference and create query.
        CollectionReference collectionReference = db.collection("posts");
        long limit = (Long) RemoteConfigUtils.getValue(RemoteConfigUtils.REMOTE.PAGE_LIMIT);
        Query query;

        // 3. Apply filter for date, to avoid fetching expired post(s).
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateLimit = dateFormat.format(new Date());

//        Date currentDate = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(currentDate);
//        Long validity = (Long) RemoteConfigUtils.getValue(RemoteConfigUtils.REMOTE.POST_VALIDITY);
//        cal.add(Calendar.DATE, -validity.intValue());
//        String dateLimit = dateFormat.format(cal.getTime());

        if (lastVisibleDocument == null) {

            query = collectionReference.whereGreaterThanOrEqualTo("expiry", dateLimit.trim())
                    .orderBy("expiry", Query.Direction.DESCENDING)
                    .limit(limit);
            showProgressBar();

        } else {

            query = collectionReference.whereGreaterThanOrEqualTo("expiry", dateLimit.trim())
                    .orderBy("expiry", Query.Direction.DESCENDING)
                    .limit(limit)
                    .startAfter(lastVisibleDocument);
        }

        // 4. Now fetch data from database in order, based on the query performed.
        query.get().addOnCompleteListener(task -> {

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

            } else {   // Fetching post failed.

                Toast.makeText(getActivity(), "Unable to fetch the post.", Toast.LENGTH_SHORT).show();
            }
            if (mPostList.size() == 0) {

                getDashboardActivity().loadFailureFragment("No active post found.");
            }
        });
    }
}

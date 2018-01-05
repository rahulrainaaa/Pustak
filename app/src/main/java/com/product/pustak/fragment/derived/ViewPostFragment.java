package com.product.pustak.fragment.derived;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.RadioGroup;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewPostFragment extends BaseFragment {

    public static final String TAG = "ViewPostFragment";

    /**
     * Class private data member(s).
     */
    private FirebaseFirestore db = null;
    private PostFilterModel filterModel = new PostFilterModel();

    /**
     * Class private UI Object(s).
     */
    private RecyclerView mRecyclerView = null;
    private ArrayList<Post> mPostList = new ArrayList<Post>();
    private ViewPostRecyclerViewAdapter mAdapter = null;

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

        View view = inflater.inflate(R.layout.frag_post, container, false);
        db = FirebaseFirestore.getInstance();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ViewPostRecyclerViewAdapter(getDashboardActivity(), mPostList);
        mRecyclerView.setAdapter(mAdapter);

        refreshList(filterModel);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_frag_view_posts, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_search:

                Toast.makeText(getActivity(), "Search selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.item_filter:

                menuFilterSelected();
                break;
        }
        return true;
    }

    /**
     * Method call on filter menu item selected.
     */
    private void menuFilterSelected() {

        final View view = getLayoutInflater().inflate(R.layout.alert_layout_preference, null);
        final RadioGroup orderByRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group_order);
        final RadioGroup availabilityRadioGroup = (RadioGroup) view.findViewById(R.id.availability_group);

        orderByRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });
        availabilityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Preference");
        builder.setIcon(R.drawable.icon_filter_black);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                switch (orderByRadioGroup.getCheckedRadioButtonId()) {

                    case R.id.rent_low_to_high:

                        filterModel.setOrder(true);
                        filterModel.setOrderBy("rent");
                        break;
                    case R.id.rent_high_to_low:

                        filterModel.setOrder(false);
                        filterModel.setOrderBy("rent");
                        break;
                    case R.id.price_low_to_high:

                        filterModel.setOrder(true);
                        filterModel.setOrderBy("price");
                        break;
                    case R.id.price_high_to_low:

                        filterModel.setOrder(false);
                        filterModel.setOrderBy("price");
                        break;
                    case R.id.date_low_to_high:

                        filterModel.setOrder(true);
                        filterModel.setOrderBy("date");
                        break;
                    case R.id.date_high_to_low:

                        filterModel.setOrder(false);
                        filterModel.setOrderBy("date");
                        break;
                    case R.id.condition_low_to_high:

                        filterModel.setOrder(true);
                        filterModel.setOrderBy("cond");
                        break;
                    case R.id.condition_high_to_low:

                        filterModel.setOrder(false);
                        filterModel.setOrderBy("cond");
                        break;
                    default:

                        filterModel.setOrderBy(null);
                        filterModel.setOrder(true);
                        break;
                }

                switch (availabilityRadioGroup.getCheckedRadioButtonId()) {

                    case R.id.radio_available_all:

                        filterModel.setAvail("Sell");
                        break;
                    case R.id.radio_available_rent:

                        filterModel.setAvail("Rent");
                        break;
                    case R.id.radio_available_sell:

                        filterModel.setAvail(null);
                        break;
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });

        builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                filterModel.setAvail(null);
                filterModel.setOrderBy(null);
                filterModel.setOrder(true);
                filterModel.setKeyword(null);
                filterModel.setLimit(-1);
            }
        });

        builder.show();
    }

    /**
     * Method to fetch all the posts based on query parameter(s).
     *
     * @param filter object holding all the filtering attribute(s).
     */
    private void refreshList(PostFilterModel filter) {

        CollectionReference collectionReference = db.collection("posts");
        Query query = null;

        /**
         * Set data limit for the queried result set.
         */
        if (filter.limit < 0) {

            query = collectionReference.limit(4);

        } else {

            query = collectionReference.limit(filter.limit);
        }

        /**
         * Set the searched keyword or pattern.
         */
        if (filter.keyword != null) {
            query = query.whereEqualTo("name", filter.keyword.trim());
        }

        /**
         * Set availability (Sell or Rent or both).
         */
        if (filter.avail == null) {

            //Do nothing.

        } else if (filter.avail.trim().contains("Rent")) {

            query = query.whereEqualTo("avail", "Rent");

        } else if (filter.avail.trim().contains("Sell")) {

            query = query.whereEqualTo("avail", "Sell");
        }

        /**
         * Set ordering of the result set.
         */
        if (filter.orderBy != null) {

            query = query.orderBy(filter.orderBy.trim(), filter.order ? Query.Direction.ASCENDING : Query.Direction.DESCENDING);
        }

        /**
         * Apply filter for date, to avoid fetching expired post(s).
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curDate = dateFormat.format(new Date());
        query = query.whereGreaterThanOrEqualTo("expiry", curDate.trim());

        showProgressBar();

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                hideProgressBar();
                mPostList.clear();

                if (task.isSuccessful()) {      // Fetched posts done.

                    for (DocumentSnapshot document : task.getResult()) {

                        Post post = document.toObject(Post.class);
                        mPostList.add(post);
                    }
                    mAdapter.notifyDataSetChanged();

                } else {                        // Fetching post failed.

                    Toast.makeText(getActivity(), "Failed in fetching posts", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    /**
     * Model class for holding the filter option(s) for {@link Post} result set.
     */
    public class PostFilterModel {

        private String keyword = null;
        private String avail = null;
        private String orderBy = null;
        private boolean order = true;
        private int limit = -1;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getAvail() {
            return avail;
        }

        public void setAvail(String avail) {
            this.avail = avail;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public boolean isOrder() {
            return order;
        }

        public void setOrder(boolean order) {
            this.order = order;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

    }

}

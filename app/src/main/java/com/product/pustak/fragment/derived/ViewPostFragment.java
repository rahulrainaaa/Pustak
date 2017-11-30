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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.product.pustak.R;
import com.product.pustak.adapter.MyPostRecyclerViewAdapter;
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
    private String mSearchKey = null;
    private String mSearchPattern = null;
    private String mOrderKey = null;
    private String mOrderType = null;
    private String mAvailability = null;

    public static ViewPostFragment getInstance() {

        ViewPostFragment fragment = new ViewPostFragment();
        return fragment;
    }

    /**
     * Class private UI Object(s).
     */
    private RecyclerView mRecyclerView = null;
    private ArrayList<Post> mPostList = new ArrayList<Post>();
    private MyPostRecyclerViewAdapter mAdapter = null;

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
        mAdapter = new MyPostRecyclerViewAdapter(getDashboardActivity(), mPostList);
        mRecyclerView.setAdapter(mAdapter);

        refreshList();
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

                        break;
                    case R.id.rent_high_to_low:

                        break;
                    case R.id.price_low_to_high:

                        break;
                    case R.id.price_high_to_low:

                        break;
                    case R.id.date_low_to_high:

                        break;
                    case R.id.date_high_to_low:

                        break;
                    case R.id.condition_low_to_high:

                        break;
                    case R.id.condition_high_to_low:

                        break;
                }

                switch (availabilityRadioGroup.getCheckedRadioButtonId()) {

                    case R.id.radio_available_all:

                        break;
                    case R.id.radio_available_rent:

                        break;
                    case R.id.radio_available_sell:

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

            }
        });
        builder.show();
    }

    /**
     * Method to fetch all the posts based on query parameter(s).
     *
     * @param key       search key attribute.
     * @param pattern   search string pattern for key attribute.
     * @param orderBy   order by which attribute.
     * @param orderType Order by descending or ascending order.
     */
//    private void refreshList(String key, String pattern, String orderBy, String orderType) {
    private void refreshList() {

        showProgressBar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = dateFormat.format(new Date());

        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

}

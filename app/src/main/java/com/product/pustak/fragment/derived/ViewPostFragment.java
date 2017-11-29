package com.product.pustak.fragment.derived;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.product.pustak.R;
import com.product.pustak.adapter.MyPostRecyclerViewAdapter;
import com.product.pustak.fragment.base.BaseFragment;
import com.product.pustak.model.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewPostFragment extends BaseFragment {

    FirebaseFirestore db = null;

    public static ViewPostFragment getInstance() {

        ViewPostFragment fragment = new ViewPostFragment();
        return fragment;
    }

    private RecyclerView mRecyclerView = null;
    private ArrayList<Post> mPostList = null;
    private MyPostRecyclerViewAdapter mAdapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_post, null);
        db = FirebaseFirestore.getInstance();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mPostList = new ArrayList<Post>();

        for (int i = 0; i < 30; i++) {
            mPostList.add(new Post());
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MyPostRecyclerViewAdapter(getActivity(), mPostList);
        mRecyclerView.setAdapter(mAdapter);

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

    private void menuFilterSelected() {

        final View view = getLayoutInflater().inflate(R.layout.alert_layout_preference, null);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group_order);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Preference");
        builder.setIcon(R.drawable.icon_filter_black);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                switch (radioGroup.getCheckedRadioButtonId()) {

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

    private void refreshList(String key, String pattern, String orderBy, String orderType) {


        CollectionReference collectionReference = db.collection("posts");


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = dateFormat.format(new Date());

//        db.collection("posts").whereLessThanOrEqualTo("exp", curDate.trim());
    }
}

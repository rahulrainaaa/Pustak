package com.product.pustak.fragment.derived;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.product.pustak.R;
import com.product.pustak.fragment.base.BaseFragment;

import java.util.ArrayList;

/**
 * Fragment class to handle the instant messages and rendering in AdapterView.
 */
public class MessageFragment extends BaseFragment {

    public static final String TAG = "MessageFragment";

    public static MessageFragment getInstance() {

        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    /**
     * Class private data member(s).
     */
    private RecyclerView mRVMessages = null;
    private ArrayList<String> mListMessages = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_messages, null);



        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getActivity(), "onDetach MessageFragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(), "onDestroy MessageFragment", Toast.LENGTH_SHORT).show();
    }
}

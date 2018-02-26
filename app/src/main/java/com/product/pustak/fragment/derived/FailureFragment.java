package com.product.pustak.fragment.derived;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.product.pustak.R;
import com.product.pustak.fragment.base.BaseFragment;

/**
 * Fragment to show the application failure or other issue.
 */
public class FailureFragment extends BaseFragment {

    public static final String TAG = "FailureFragment";

    public static FailureFragment getInstance() {

        return new FailureFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_failure, container, false);
        TextView textView = view.findViewById(R.id.txt_msg);

        @SuppressWarnings("ConstantConditions") String message = getArguments().getString("message");
        textView.setText(message);

        view.findViewById(R.id.btn_refresh).setOnClickListener(v -> {

            int prevFragment = getArguments().getInt("prevFragment");
            FragmentType f = FragmentType.FAILURE;
            getDashboardActivity().loadFragment(f.getFragType(prevFragment));

        });

        return view;

    }

}

package com.product.pustak.fragment.derived;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.product.pustak.R;
import com.product.pustak.activity.derived.UpdateProfileActivity;
import com.product.pustak.fragment.base.BaseFragment;
import com.product.pustak.model.User;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    public static ProfileFragment getInstance() {

        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    private TextView txtName = null;
    private TextView txtMobile = null;
    private TextView txtEmail = null;
    private TextView txtArea = null;
    private TextView txtCity = null;
    private TextView txtState = null;
    private TextView txtCountry = null;
    private ImageButton iBtnEdit = null;

    private User mUser = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_my_profile, container, false);

        txtName = (TextView) view.findViewById(R.id.txt_user_name);
        txtMobile = (TextView) view.findViewById(R.id.txt_phone);
        txtEmail = (TextView) view.findViewById(R.id.txt_email);
        txtArea = (TextView) view.findViewById(R.id.txt_area);
        txtCity = (TextView) view.findViewById(R.id.txt_city);
        txtState = (TextView) view.findViewById(R.id.txt_state);
        txtCountry = (TextView) view.findViewById(R.id.txt_country);
        iBtnEdit = (ImageButton) view.findViewById(R.id.btn_edit_profile);

        iBtnEdit.setOnClickListener(this);

        mUser = getDashboardActivity().getUser();

        if (mUser != null) {

            txtName.setText(mUser.getName());
            txtMobile.setText(mUser.getMobile());
            txtEmail.setText(mUser.getEmail());
            txtArea.setText(mUser.getArea());
            txtCity.setText(mUser.getCity());
            txtState.setText(mUser.getState());
            txtCountry.setText(mUser.getCountry());
        }

        return view;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_edit_profile) {

            Intent intent = new Intent(getDashboardActivity(), UpdateProfileActivity.class);
            intent.putExtra("user", mUser);
            startActivity(intent);
            getDashboardActivity().finish();

        } else {

            Toast.makeText(getActivity(), "Unhandled OnClickListener()", Toast.LENGTH_SHORT).show();
        }

    }
}

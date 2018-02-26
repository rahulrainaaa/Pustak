package com.product.pustak.activity.derived;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.product.pustak.R;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.model.User;
import com.product.pustak.utils.ProfileUtils;

public class UserProfileActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "UserProfileActivity";

    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView txtName = findViewById(R.id.txt_user_name);
        TextView txtMobile = findViewById(R.id.txt_phone);
        TextView txtEmail = findViewById(R.id.txt_email);
        TextView txtArea = findViewById(R.id.txt_area);
        TextView txtCity = findViewById(R.id.txt_city);
        TextView txtState = findViewById(R.id.txt_state);
        TextView txtCountry = findViewById(R.id.txt_country);

        ImageButton imgBtnCall = findViewById(R.id.btn_call);
        ImageButton imgBtnEmail = findViewById(R.id.btn_email);

        imgBtnCall.setOnClickListener(this);
        imgBtnEmail.setOnClickListener(this);

        user = getIntent().getParcelableExtra("user");

        if (user != null) {

            txtName.setText(user.getName());
            txtMobile.setText(user.getMobile());
            txtEmail.setText(user.getEmail());
            txtArea.setText(user.getArea());
            txtCity.setText(user.getCity());
            txtState.setText(user.getState());
            txtCountry.setText(user.getCountry());
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_call:

                ProfileUtils.call(this, user.getMobile());
                break;
            case R.id.btn_email:

                ProfileUtils.email(this, user.getEmail());
                break;
        }
    }
}

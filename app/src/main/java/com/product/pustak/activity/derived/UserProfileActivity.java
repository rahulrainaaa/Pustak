package com.product.pustak.activity.derived;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.product.pustak.R;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.model.User;
import com.product.pustak.utils.ProfileUtils;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

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
    protected void onResume() {
        super.onResume();

        if (getSharedPreferences("target", 0).getBoolean("d", true)) {
            Handler handler = new Handler();
            handler.postDelayed(() -> showCallTarget(), 600);
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

    /**
     * Method to show target on call button.
     */
    private void showCallTarget() {

        new MaterialTapTargetPrompt.Builder(this)
                .setTarget(findViewById(R.id.btn_call))
                .setPrimaryText("Contact Owner")
                .setBackgroundColour(Color.argb(190, 126, 121, 255))
                .setIcon(R.drawable.icon_call_black)
                .setSecondaryText("You can simple make a phone call or drop message to owner of book.")
                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                .setPromptStateChangeListener((prompt, state) -> {
                    if (state == MaterialTapTargetPrompt.STATE_SHOW_FOR_TIMEOUT) {

                        getSharedPreferences("target", 0).edit().putBoolean("d", false).apply();
                    }
                })
                .showFor(7000);

    }
}

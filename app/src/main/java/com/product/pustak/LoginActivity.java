package com.product.pustak;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    /**
     * Class private data members.
     */

    private ImageView imgLogo = null;
    private TextView txtTitle = null;
    private TextView txtQuote = null;
    private EditText etPhone = null;
    private FloatingActionButton fabProceed = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        imgLogo = (ImageView) findViewById(R.id.imageView);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        txtQuote = (TextView) findViewById(R.id.txt_quote);
        fabProceed = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        etPhone = (EditText) findViewById(R.id.txt_phone);

        imgLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_logo_up_small));
        txtTitle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_title_up_disappear));
        txtQuote.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_quote_up_show));
        etPhone.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_phone_appear));
        fabProceed.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_fab_appear));


    }
}

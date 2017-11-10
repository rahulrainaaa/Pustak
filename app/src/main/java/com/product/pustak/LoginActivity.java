package com.product.pustak;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity class to handle splash and login UI.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OTPLoginListener {

    /**
     * Class private data members.
     */
    private ImageView imgLogo = null;
    private TextView txtTitle = null;
    private TextView txtQuote = null;
    private EditText etMobile = null;
    private FloatingActionButton fabLogin = null;
    private OTPLoginHandler mOTPLoginHandler = null;


    public static boolean isActive = true;

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

        imgLogo = findViewById(R.id.img_logo);
        txtTitle = findViewById(R.id.txt_title);
        txtQuote = findViewById(R.id.txt_quote);
        etMobile = findViewById(R.id.txt_phone);
        fabLogin = findViewById(R.id.fab_login);
        fabLogin.setOnClickListener(this);

        imgLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_logo_up_small));
        txtTitle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_title_up_disappear));
        txtQuote.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_quote_up_show));
        etMobile.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_phone_appear));
        fabLogin.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_fab_appear));

    }

    @Override
    protected void onResume() {
        super.onResume();

        isActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        isActive = false;
    }

    @Override
    public void onBackPressed() {

        OTPSMSBroadcastReceiver receiver = new OTPSMSBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        filter.setPriority(5822);
        registerReceiver(receiver, filter);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fab_login:

                fabLoginClicked();
                break;
        }

    }

    /**
     * Call the {@link OTPLoginHandler} to handle Mobile OTP Login.
     */
    public void fabLoginClicked() {

        etMobile.setError(null);

        /**
         * Permission Check for RECEIVE_SMS.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{android.Manifest.permission.RECEIVE_SMS}, 1);
                Toast.makeText(this, "Enable SMS Permission.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        /**
         * Call OTPLoginHandler to handle OTP Login.
         */
        try {
            mOTPLoginHandler = new OTPLoginHandler(this, this);

        } catch (PustakException e) {

            e.printStackTrace();
            return;
        }

        if (!mOTPLoginHandler.login(etMobile.getText().toString())) {

            // Case of mobile number validation fail.
            etMobile.setError("Enter 10 digit mobile number");
        } else {

            // Show progress dialog. Login in progress.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {     // RECEIVE_SMS Permission.

            Toast.makeText(this, "SMS permission granted.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void otpLoginCallback(int code, String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        switch (code) {

            case 0:         // Success Login.

                break;
            case 1:         // Exception or Error.

                break;

        }

    }
}

package com.product.pustak;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Activity class to handle splash and login UI.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Class constant field(s).
     */
    public static final String TAG = "LoginActivity";

    /**
     * Class Activity UI object(s).
     */
    private ImageView imgLogo = null;
    private TextView txtTitle = null;
    private TextView txtQuote = null;
    private EditText etMobile = null;
    private SweetAlertDialog mAlertDialog = null;

    /**
     * Class private data member(s).
     */
    private OTPLoginService mService = null;
    private boolean mBinded = false;
    private boolean mProcessing = false;


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

        imgLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_logo_up_small));
        txtTitle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_title_up_disappear));
        txtQuote.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_quote_up_show));
        etMobile.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_phone_appear));
        findViewById(R.id.fab_login).startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_fab_appear));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBinded) {

            unbindService(mConnection);
            mBinded = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {     // RECEIVE_SMS Permission.

            Toast.makeText(this, getString(R.string.sms_permission_granted), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.permission_declined), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * FAB button onClick callback method.
     *
     * @param view
     */
    public void fabLoginClicked(View view) {

        /**
         * Permission Check for RECEIVE_SMS.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{android.Manifest.permission.RECEIVE_SMS}, 1);
                Toast.makeText(this, getString(R.string.enable_sms_permission), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        /**
         * Check for mobile number field validation.
         */
        if (!validMobile(etMobile.getText().toString())) {

            etMobile.setError("Enter 10 digit mobile number");

        } else {

            /**
             * Call OTPLoginHandler to handle OTP Login.
             */
            PhoneAuthProvider.getInstance().verifyPhoneNumber(etMobile.getText().toString(), 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    mProcessing = false;
                    mAlertDialog.dismissWithAnimation();
                    /**
                     * Start {@link OTPLoginService} Service to handle OTP Verification.
                     */
                    LoginActivity.this.startService(new Intent(LoginActivity.this, OTPLoginService.class));

                    /**
                     * Bind to {@link OTPLoginService} service.
                     */
                    Intent intent = new Intent(LoginActivity.this, OTPLoginService.class);
                    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                    LoginActivity.this.finish();
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    mAlertDialog.setConfirmText(getString(R.string.ok))
                            .setContentText(e.getMessage())
                            .showCancelButton(false)
                            .setCancelClickListener(null)
                            .setConfirmClickListener(null)
                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    mProcessing = false;
                }
            });

            mAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            mAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            mAlertDialog.setTitleText(getString(R.string.connecting));
            mAlertDialog.setCancelable(false);
            mAlertDialog.show();
            mProcessing = true;

        }
    }

    /**
     * Check the validation of Mobile number.
     *
     * @param mobile Mobile number.
     * @return true = valid, false = invalid.
     */
    private boolean validMobile(String mobile) {

        String strRegexMobile = "[0-9]{10}";
        return Pattern.compile(strRegexMobile).matcher(mobile).matches();
    }

    /**
     * {@link ServiceConnection} object to bind with {@link OTPLoginService} for IPC.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            OTPLoginService.LocalBinder binder = (OTPLoginService.LocalBinder) iBinder;
            mService = binder.getService();
            mBinded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            mBinded = false;
        }
    };
}

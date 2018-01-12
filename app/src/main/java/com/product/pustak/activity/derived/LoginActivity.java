package com.product.pustak.activity.derived;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.product.pustak.R;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.handler.UserProfileHandler.UserProfileHandler;
import com.product.pustak.handler.UserProfileListener.UserProfileFetchedListener;
import com.product.pustak.model.User;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Activity class to handle splash and login UI.
 */
public class LoginActivity extends BaseActivity {

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
    private boolean isUIPresent = true;
    private User loginUser = null;

    private UserProfileFetchedListener mUserProfileListener = new UserProfileFetchedListener() {

        @Override
        public void userProfileFetchedCallback(User user, UserProfileHandler.CODE code, String message) {

            if (code == UserProfileHandler.CODE.SUCCESS) {

                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.putExtra("user", user);
                loginUser = user;
                proceedNext(intent);

            } else if (code == UserProfileHandler.CODE.IllegalStateException) {

                Toast.makeText(LoginActivity.this, "Please update your profile", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, UpdateProfileActivity.class);
                proceedNext(intent);

            } else if (code == UserProfileHandler.CODE.Exception) {

                etMobile.setVisibility(View.VISIBLE);
                findViewById(R.id.fab_login).setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
    protected void onResume() {
        super.onResume();
        isUIPresent = true;
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        fetchUserProfile();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isUIPresent = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (loginUser != null) {

            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra("user", loginUser);
            proceedNext(intent);
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

                    Log.d(TAG, "on verification completed");
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    Log.d(TAG, "on verification failed");
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(verificationId, forceResendingToken);

                    Log.d(TAG, "on code sent");
                }

                @Override
                public void onCodeAutoRetrievalTimeOut(String verificationId) {
                    super.onCodeAutoRetrievalTimeOut(verificationId);
                    Log.d(TAG, "on code auto retrieval timeout");
                }
            });
        }

    }

    /**
     * Method to fetch user profile from Firestone database and proceed accordingly.
     */
    private void fetchUserProfile() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        /**
         * Check if user session is present?
         * Then prompt for Phone number to login.
         */
        if (user != null) {

            etMobile.setVisibility(View.GONE);
            findViewById(R.id.fab_login).setVisibility(View.GONE);

            UserProfileHandler userProfileHandler = new UserProfileHandler(this);
            userProfileHandler.getUser(mUserProfileListener, false, null);

        }
    }

    /**
     * Method to finally match the credentials and verify.
     *
     * @param credential
     */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        /**
         * Handle SignIn for the application {@link FirebaseAuth} instance.
         */
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Toast.makeText(LoginActivity.this, "SignIn Successful.", Toast.LENGTH_SHORT).show();
                    fetchUserProfile();

                } else {

                    Log.w(TAG, "signInWithCredential:failure", task.getException());

                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LoginActivity.this, "Failed Exception", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
     * Method to proceed to next screen and finish this Activity.
     *
     * @param intent
     */
    public void proceedNext(final Intent intent) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isUIPresent) {

                    startActivity(intent);
                    finish();
                }

            }
        }, 1000);

    }

}

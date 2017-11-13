package com.product.pustak;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

public class VerifyOTPActivity extends AppCompatActivity implements OtpVerifiedListener {

    /**
     * Class constant member(s).
     */
    public static final String TAG = "VerifyOTPActivity";

    /**
     * Class private data member(s).
     */
    private OTPLoginService mService = null;
    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, OTPLoginService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * {@link ServiceConnection} object to bind with {@link OTPLoginService} for IPC.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            OTPLoginService.LocalBinder binder = (OTPLoginService.LocalBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            mBound = false;
        }
    };

    @Override
    public void otpVerificationSuccess(String mobile, String otp, String provider) {

    }

    @Override
    public void otpVerificationFailed(String message) {

    }
}

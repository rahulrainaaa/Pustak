package com.product.pustak;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class OTPLoginHandler extends PhoneAuthProvider.OnVerificationStateChangedCallbacks {

    public static final String TAG = "OTPLoginHandler";

    private Activity mActivity = null;
    private OTPLoginListener mListener = null;
    private String mStrMobile = "";

    public OTPLoginHandler(Activity activity, OTPLoginListener listener) throws PustakException {

        this.mActivity = activity;
        this.mListener = listener;
        if (mActivity == null) {

            throw new PustakException(PustakException.EXCEPTIONS.NULL_ACTIVITY);
        } else if (mActivity == null) {

            throw new PustakException(PustakException.EXCEPTIONS.NULL_LOGIN_LISTENER);
        }
    }

    /**
     * {@link PhoneAuthProvider.OnVerificationStateChangedCallbacks} callback method(s).
     */
    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        registerOTPBroadcastReceiver(phoneAuthCredential);
    }

    @Override
    public void onVerificationFailed(FirebaseException e) {

        mListener.otpLoginCallback(2, e.getMessage());
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
     * Do the Google Firebase OTP Authentication.
     *
     * @param mobile Mobile number string.
     */
    public boolean login(String mobile) {

        if (!validMobile(mobile)) {     // Invalid mobile number.

            return false;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobile.trim(), 60, TimeUnit.SECONDS, mActivity, this);
        Toast.makeText(mActivity, "Waiting for OTP.", Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * Register the {@link BroadcastReceiver} for OTP RECEIVE_SMS.
     *
     * @param phoneAuthCredential
     */
    private void registerOTPBroadcastReceiver(final PhoneAuthCredential phoneAuthCredential) {

        OTPSMSBroadcastReceiver receiver = new OTPSMSBroadcastReceiver();
        receiver.sStrMobile = mStrMobile;
        receiver.sListener = mListener;
        receiver.sStrOtp = phoneAuthCredential.getSmsCode();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        filter.setPriority(5822);
        mActivity.registerReceiver(receiver, filter);

    }

}

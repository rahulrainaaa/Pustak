package com.product.pustak;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Telephony;

/**
 * Service to handle the OTP SMS and login.
 */
public class OTPLoginService extends Service {

    /**
     * Class constant data field(s).
     */
    public static final String TAG = "OTPLoginService";

    /**
     * Class private data member(s).
     */
    private final IBinder mBinder = new LocalBinder();
    private String mMobile = null;
    private String mOTP = null;
    private String mProvider = null;
    private OTPSMSReceiver mOtpSmsReceiver = null;
    private OtpVerifiedListener mListener = null;
    private boolean mProcessing = false;

    @Override
    public IBinder onBind(Intent intent) {

        if (!mProcessing) {
            registerReceiver();
        }
        return mBinder;
    }

    /**
     * Method to set class member field data.
     *
     * @param mobile   Mobile number to verify.
     * @param otp      OTP string to match with received OTP.
     * @param provider OTP provider string data.
     */
    public void setData(String mobile, String otp, String provider) {

        this.mMobile = mobile;
        this.mOTP = otp;
        this.mProvider = provider;
    }

    /**
     * Method to register the {@link OTPSMSReceiver}
     */
    public void registerReceiver() {

        mOtpSmsReceiver = new OTPSMSReceiver();
        mOtpSmsReceiver.mService = this;
        mOtpSmsReceiver.mProvider = mProvider;
        mOtpSmsReceiver.mOtp = mOTP;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        filter.setPriority(5822);
        registerReceiver(mOtpSmsReceiver, filter);
        mProcessing = true;

    }

    /**
     * Method to set listener for otp verification callback.
     *
     * @param listener
     */
    public void setOtpVerifyListener(OtpVerifiedListener listener) {

        this.mListener = listener;
    }

    /**
     * Check, if this service is processing for OTP verification.
     *
     * @return if service is performing OTP verification operation.
     */
    public boolean isProcessing() {

        return mProcessing;
    }

    /**
     * Local Binder class to IPC with this service.
     */
    public class LocalBinder extends Binder {

        /**
         * Method to get the reference to this service.
         *
         * @return OTPLoginService.this
         */
        OTPLoginService getService() {

            return OTPLoginService.this;
        }

    }

    /**
     * Method for callback from {@link OTPSMSReceiver}.
     *
     * @param message
     * @param status
     */
    public void otpReceivedCallback(final String message, final boolean status) {

        mProcessing = false;
        /**
         * Send the callback on separate UI thread.
         */
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                try {

                    if (status) {

                        SessionUtils.getInstance().setSession(OTPLoginService.this, mMobile);
                        mListener.otpVerificationSuccess(mMobile, mOTP, mProvider);

                    } else {

                        mListener.otpVerificationFailed(message);
                    }
                } catch (Exception e) {

                    e.getMessage();
                }

                /**
                 * Stop this service.
                 */
                OTPLoginService.this.stopSelf();
            }
        });

    }

}

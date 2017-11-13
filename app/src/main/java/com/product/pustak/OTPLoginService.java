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

    @Override
    public IBinder onBind(Intent intent) {

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
     * @param phoneNumber
     * @param sms
     * @param message
     * @param receiver
     */
    public void otpReceivedCallback(String phoneNumber, String sms, String message, OTPSMSReceiver receiver) {

        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                SessionUtils.getInstance().setSession(OTPLoginService.this, mMobile);
            }
        });

    }


}

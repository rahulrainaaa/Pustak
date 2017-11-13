package com.product.pustak;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
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
    public static final String MOBILE_NUMBER = "OTPLoginService.MOBILE_NUMBER";
    public static final String MOBILE_OTP = "OTPLoginService.MOBILE_OTP";
    public static final String OTP_PROVIDER = "OTPLoginService.OTP_PROVIDER";

    /**
     * Class private data member(s).
     */
    private final IBinder mBinder = new LocalBinder();
    private String mMobile = null;
    private String mOTP = null;
    private String mProvider = null;

    @Override
    public IBinder onBind(Intent intent) {

        mMobile = intent.getStringExtra(MOBILE_NUMBER);
        mOTP = intent.getStringExtra(MOBILE_OTP);
        mProvider = intent.getStringExtra(OTP_PROVIDER);

        registerReceiver();

        return mBinder;
    }

    /**
     * Method to register the {@link OTPSMSReceiver}
     */
    private void registerReceiver() {

        OTPSMSReceiver receiver = new OTPSMSReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        filter.setPriority(5822);
        registerReceiver(receiver, filter);

    }

    private class LocalBinder extends Binder {

        OTPLoginService getService() {

            return OTPLoginService.this;
        }

    }

}

package com.product.pustak;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class OTPSMSReceiver extends BroadcastReceiver {

    /**
     * Class constant data member(s).
     */
    public static final String TAG = "OTPSMSReceiver";

    /**
     * Class private data member(s).
     */
    public String mOtp = null;
    public String mProvider = null;
    public OTPLoginService mService = null;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        Bundle b = intent.getExtras();
        try {
            if (b != null) {

                Object[] pdusObj = (Object[]) b.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    if (phoneNumber.contains(mProvider) && message.contains(mOtp)) {

                        mService.otpReceivedCallback(phoneNumber, message, "verified", this);
                        context.unregisterReceiver(this);
                    }
                }
            }

        } catch (Exception e) {

            mService.otpReceivedCallback(null, null, e.getMessage(), this);
            context.unregisterReceiver(this);
        }
    }
}

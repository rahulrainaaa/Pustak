package com.product.pustak;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsMessage;
import android.util.Log;

public class OTPSMSBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "OTPSMSBroadcastReceiver";

    public static String sStrMobile = "";
    public static String sStrOtp = "";
    public OTPLoginListener sListener = null;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        Log.d(TAG, "onReceive");
        context.unregisterReceiver(this);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                Bundle b = intent.getExtras();
                try {
                    if (b != null) {

                        Object[] pdusObj = (Object[]) b.get("pdus");

                        for (int i = 0; i < pdusObj.length; i++) {

                            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                            String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                            String senderNum = phoneNumber;
                            String message = currentMessage.getDisplayMessageBody();

                            if (message.contains(sStrOtp.trim())) {

                                SessionUtils.getInstance().setSession(context, sStrMobile.trim());
                                if (LoginActivity.isActive) {
                                    sListener.otpLoginCallback(0, "Mobile verification successful");
                                }

                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (LoginActivity.isActive) {
                        sListener.otpLoginCallback(1, "Mobile verification Error");
                    }
                }
            }
        });


    }
}

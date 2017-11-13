package com.product.pustak;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsMessage;

public class OTPSMSReceiver extends BroadcastReceiver {

    /**
     * Class constant data member(s).
     */
    public static final String TAG = "OTPSMSReceiver";

    /**
     * Class private data member(s).
     */
    public static String sStrMobile = null;
    public static String sStrOtp = null;

    @Override
    public void onReceive(final Context context, final Intent intent) {

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


                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });


    }
}

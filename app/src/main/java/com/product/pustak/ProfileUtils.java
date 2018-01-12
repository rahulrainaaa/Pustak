package com.product.pustak;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.Locale;

/**
 * Utility class for handling some user action.
 */
public class ProfileUtils {

    public static final String TAG = "ProfileUtils";

    /**
     * Method to start network call on given mobile number.
     *
     * @param activity
     * @param mobile
     */
    public static void call(Activity activity, String mobile) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                Toast.makeText(activity, "Enable call permission", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(activity, "Please enable call permission manually", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        try {

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile.trim()));
            activity.startActivity(intent);

        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(activity, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Method to send mail to given email.
     *
     * @param context
     * @param email
     */
    public static void email(Context context, String email) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(intent);
    }

    /**
     * Method to start google maps application and plot the given geo coordinates.
     *
     * @param context
     * @param geo
     */
    public static void mapLocation(Context context, String geo) {

        String uri = String.format(Locale.ENGLISH, "geo:" + geo);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }

    /**
     * Method to send SMS to given phone number.
     *
     * @param context
     * @param phone
     */
    public static void sendMessage(Context context, String phone) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
        intent.putExtra("sms_body", "");
        context.startActivity(intent);

    }

}

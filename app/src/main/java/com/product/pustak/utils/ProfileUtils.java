package com.product.pustak.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

        try {

            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile.trim()));
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

    /**
     * Method to share application url with other friends via other installed application(s).
     *
     * @param context
     */
    public static void shareWith(Context context) {

        String shareBody = "application url to be shared.";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Pustak App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Recommend to friend"));
    }
}

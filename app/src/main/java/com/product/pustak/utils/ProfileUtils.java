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
     * @param activity {@link Activity} reference.
     * @param mobile   String
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
     * @param context {@link Context} reference
     * @param email   {@link String}
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
     * @param context {@link Context} reference.
     * @param geo     {@link String} Geographical Lat,Lng
     */
    public static void mapLocation(Context context, String geo) {

        String uri = "geo:" + geo;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }

    /**
     * Method to send SMS to given phone number.
     *
     * @param context {@link Context} reference
     * @param phone   {@link String}
     */
    public static void sendMessage(Context context, String phone) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
        intent.putExtra("sms_body", "");
        context.startActivity(intent);

    }

    /**
     * Method to share application url with other friends via other installed application(s).
     *
     * @param context {@link Context} reference.
     */
    public static void shareWith(Context context) {

        String shareBody = (String) RemoteConfigUtils.getValue(RemoteConfigUtils.REMOTE.PLAY_STORE);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Pustak");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Recommend to friend"));
    }
}

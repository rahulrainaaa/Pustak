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

public class GSMUtils {

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

    public static void email(Context context, String email) {


    }

}

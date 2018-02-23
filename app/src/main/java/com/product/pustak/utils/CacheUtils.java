package com.product.pustak.utils;


import android.content.Context;

public class CacheUtils {

    public static void setTotalPost(Context context, int total) {

        context.getSharedPreferences("cache", 0).edit().putInt("total", total).apply();
    }

    public static int getTotalPost(Context context) {

        return context.getSharedPreferences("cache", 0).getInt("total", 0);
    }

    public static void newPostAdded(Context context) {

        int total = getTotalPost(context);
        setTotalPost(context, total + 1);
    }

    public static void postDeleted(Context context) {

        int total = getTotalPost(context);
        setTotalPost(context, total - 1);
    }

}

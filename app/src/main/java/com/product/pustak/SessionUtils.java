package com.product.pustak;


import android.content.Context;

public class SessionUtils {
    private static final SessionUtils ourInstance = new SessionUtils();

    public static final String MOBILE = "MOBILE";

    public static SessionUtils getInstance() {
        return ourInstance;
    }

    private SessionUtils() {
    }

    public void setSession(Context context, String mobile) {

        context.getSharedPreferences("session", 0).edit().putString(MOBILE, mobile).commit();

    }

    public String getSession(Context context) {

        return context.getSharedPreferences("session", 0).getString(MOBILE, "");

    }

    public boolean isSession(Context context) {

        if (context.getSharedPreferences("session", 0).getString(MOBILE, "").isEmpty()) {

            return true;
        } else {

            return false;
        }
    }

}

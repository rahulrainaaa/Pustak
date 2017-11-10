package com.product.pustak;


public class PustakException extends Exception {

    public static final String TAG = "PustakException";

    public static enum EXCEPTIONS {NULL_ACTIVITY, NULL_LOGIN_LISTENER}

    public EXCEPTIONS mExceptions = null;

    public PustakException(EXCEPTIONS exceptions) {


    }

    public String getMessage() {

        switch (mExceptions) {

            case NULL_ACTIVITY:
                return "NULL Activity Exception";
            case NULL_LOGIN_LISTENER:
                return "NULL OTPLoginListener Exception";
            default:
                return "";
        }
    }

}

package com.product.pustak;


public class PustakException extends Exception {

    public static final String TAG = "PustakException";

    public static enum EXCEPTIONS {NULL_ACTIVITY, NULL_LOGIN_LISTENER}

    public EXCEPTIONS mExceptions = null;

    public PustakException(EXCEPTIONS exceptions) {

        if (exceptions == EXCEPTIONS.NULL_ACTIVITY) {

        } else if (exceptions == EXCEPTIONS.NULL_LOGIN_LISTENER) {

        }
    }

}

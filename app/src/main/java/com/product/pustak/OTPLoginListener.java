package com.product.pustak;


public interface OTPLoginListener {



    public static enum CODE {SUCCESS, EXCEPTION, FAIL}

    public void otpLoginCallback(int code, String message);

}

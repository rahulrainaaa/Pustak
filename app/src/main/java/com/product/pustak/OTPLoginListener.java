package com.product.pustak;


public interface OTPLoginListener {

    public static enum CODE {SUCCESS, EXCEPTION, FAIL}

    public void otpLoginCallback(CODE code, String message);

}

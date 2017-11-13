package com.product.pustak;

/**
 * Otp Verification listener interface.
 */
public interface OtpVerifiedListener {

    /**
     * OTP verification success callback method.
     *
     * @param mobile
     * @param otp
     * @param provider
     */
    public void otpVerificationSuccess(String mobile, String otp, String provider);

    /**
     * OTP verification failed callback method.
     *
     * @param message
     */
    public void otpVerificationFailed(String message);

}

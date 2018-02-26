package com.product.pustak.utils;

/**
 * Project global constant values.
 */
public class Constants {

    public static final String REGEX_MOBILE = "^[0-9]{10}$";    // 10 digits allowed.

    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]+)?$";

    public static final String REGEX_ALPHA_ONLY = "^[a-zA-Z ]*$";

    public static final String REGEX_ALNUM = "^[a-zA-Z0-9 ]*$";     // alpha number with space.

    public static final String REGEX_NAME = "^[a-zA-Z0-9,.&- ]*$";

    public static final String REGEX_NORMAL_TEXT = "^[a-zA-Z0-9,.!?@&_- ]*$";

    public static final String REGEX_PRICE = "^\\d{0,4}(\\.\\d{1,2})?$";        // Regex pattern for price = xxxx.xx; x = digit.

    public static final String REGEX_NUMBER = "^\\d+$";             // Only digits allowed.

    public static final String REGEX_DAYS = "^\\d{1,2}$";           // Only 2 digits allowed.

    public static final double APP_VERSION = 1.0;


}

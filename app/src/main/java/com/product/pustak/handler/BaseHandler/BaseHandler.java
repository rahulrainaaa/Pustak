package com.product.pustak.handler.BaseHandler;


import com.product.pustak.activity.base.BaseActivity;

/**
 * Base handler class for all the handler classes in this application project.
 * To enforce project policy standard(s).
 */
public class BaseHandler {

    public static final String TAG = "BaseHandler";

    /**
     * Class private data member(s).
     */
    protected BaseActivity mActivity = null;
    protected boolean mShowProgress = false;

    public BaseHandler(BaseActivity activity) {

        this.mActivity = activity;
    }

    /**
     * Enumerated response fields from base handler to callback(s).
     */
    public enum CODE {
        SUCCESS, FAILED, IllegalStateException, Exception
    }

}

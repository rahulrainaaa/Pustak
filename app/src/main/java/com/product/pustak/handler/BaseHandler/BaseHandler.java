package com.product.pustak.handler.BaseHandler;


import com.product.pustak.activity.base.BaseActivity;

public class BaseHandler {

    public enum CODE {SUCCESS, FAILED, IllegalStateException, Exception}

    protected BaseActivity mActivity = null;
    protected boolean mShowProgress = false;

    public BaseHandler(BaseActivity activity) {

        this.mActivity = activity;
    }

}

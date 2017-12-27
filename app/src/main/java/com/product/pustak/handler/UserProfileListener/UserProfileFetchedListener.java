package com.product.pustak.handler.UserProfileListener;

import com.product.pustak.handler.UserProfileHandler.UserProfileHandler;
import com.product.pustak.model.User;

public interface UserProfileFetchedListener {


    public void userProfileFetchedCallback(User user, UserProfileHandler.CODE code, String message);

}

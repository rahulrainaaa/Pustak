package com.product.pustak.handler.UserProfileListener;

import com.product.pustak.handler.UserProfileHandler;
import com.product.pustak.model.User;

public interface UserProfileUpdatedListener {


    public void userProfileUpdatedCallback(User user, UserProfileHandler.CODE code, String message);

}

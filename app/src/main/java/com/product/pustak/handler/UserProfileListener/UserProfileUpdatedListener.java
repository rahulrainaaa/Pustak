package com.product.pustak.handler.UserProfileListener;

import com.product.pustak.handler.UserProfileHandler.UserProfileHandler;
import com.product.pustak.model.User;

/**
 * Interface to listen for user profile updated.
 */
public interface UserProfileUpdatedListener {

    /**
     * Callback method to handle the profile updated callback.
     *
     * @param user
     * @param code
     * @param message
     */
    public void userProfileUpdatedCallback(User user, UserProfileHandler.CODE code, String message);

}

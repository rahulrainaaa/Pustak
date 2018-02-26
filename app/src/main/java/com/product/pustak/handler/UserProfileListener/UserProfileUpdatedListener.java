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
     * @param user    {@link User} model object reference.
     * @param code    {@link com.product.pustak.handler.BaseHandler.BaseHandler.CODE}
     * @param message {@link String}
     */
    void userProfileUpdatedCallback(User user, UserProfileHandler.CODE code, String message);

}

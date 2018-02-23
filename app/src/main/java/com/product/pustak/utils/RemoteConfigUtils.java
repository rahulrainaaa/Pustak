package com.product.pustak.utils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/**
 * Utility class to handle the remote config values.
 */
public class RemoteConfigUtils {

    /**
     * Method to get the value from remote config for a given key in specific data type.
     *
     * @param remote enum for key.
     * @return Object of given type
     */
    public static Object getValue(REMOTE remote) {

        Object dataObj = null;

        switch (remote) {

            case PLAY_STORE:

                // play store application package identifier.
                dataObj = FirebaseRemoteConfig.getInstance().getString("play_store");
                break;
            case POST_VALIDITY:

                // post validity in number of days.
                dataObj = FirebaseRemoteConfig.getInstance().getLong("post_validity");
                break;
            case APP_STATUS:

                // application status false = disabled; true = working.
                dataObj = FirebaseRemoteConfig.getInstance().getBoolean("app_status");
                break;
            case STATUS_MSG:

                // application status message to show.
                dataObj = FirebaseRemoteConfig.getInstance().getString("status_msg");
                break;
            case PAGE_LIMIT:

                // view post per page limit.
                dataObj = FirebaseRemoteConfig.getInstance().getLong("page_limit");
                break;
            case POST_LIMIT:

                // quota limit for posting my adds.
                dataObj = FirebaseRemoteConfig.getInstance().getLong("post_limit");
                break;
            case VERSION_MIN:

                // double - minimum app version supported.
                dataObj = FirebaseRemoteConfig.getInstance().getDouble("version_min");
                break;
            case VERSION_NEW:

                // double - application latest version release.
                dataObj = FirebaseRemoteConfig.getInstance().getDouble("version_new");
                break;

        }

        return dataObj;

    }

    public enum REMOTE {VERSION_NEW, VERSION_MIN, POST_LIMIT, PAGE_LIMIT, STATUS_MSG, APP_STATUS, POST_VALIDITY, PLAY_STORE}
}

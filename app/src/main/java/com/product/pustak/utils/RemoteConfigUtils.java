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

                dataObj = FirebaseRemoteConfig.getInstance().getString("play_store");
                break;
            case POST_VALIDITY:

                dataObj = FirebaseRemoteConfig.getInstance().getString("post_validity");
                break;
            case APP_STATUS:

                dataObj = FirebaseRemoteConfig.getInstance().getBoolean("app_status");
                break;
            case STATUS_MSG:

                dataObj = FirebaseRemoteConfig.getInstance().getString("status_msg");
                break;
            case PAGE_LIMIT:

                dataObj = FirebaseRemoteConfig.getInstance().getLong("page_limit");
                break;
            case POST_LIMIT:

                dataObj = FirebaseRemoteConfig.getInstance().getLong("post_limit");
                break;
            case VERSION_MIN:

                dataObj = FirebaseRemoteConfig.getInstance().getDouble("version_min");
                break;
            case VERSION_NEW:

                dataObj = FirebaseRemoteConfig.getInstance().getDouble("version_new");
                break;

        }

        return dataObj;

    }

    public enum REMOTE {VERSION_NEW, VERSION_MIN, POST_LIMIT, PAGE_LIMIT, STATUS_MSG, APP_STATUS, POST_VALIDITY, PLAY_STORE}
}

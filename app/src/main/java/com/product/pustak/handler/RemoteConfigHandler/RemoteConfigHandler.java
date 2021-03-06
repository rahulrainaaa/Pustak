package com.product.pustak.handler.RemoteConfigHandler;


import android.app.Activity;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.product.pustak.R;

/**
 * Handler class to handle the {@link com.google.firebase.remoteconfig.FirebaseRemoteConfigValue}.
 */
public class RemoteConfigHandler {

    private boolean PROCESSING = false;

    /**
     * Method to sync {@link FirebaseRemoteConfig} server values.
     *
     * @param activity reference
     */
    public void syncValues(final Activity activity, final RemoteSyncListener listener) {

        // Stop multiple requests for Remote Config refresh.
        if (PROCESSING) {

            return;
        }

        PROCESSING = true;

        final FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(false)
                .build();

        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        mFirebaseRemoteConfig.fetch().addOnCompleteListener(activity, task -> {

            PROCESSING = false;
            if (task.isSuccessful()) {

                mFirebaseRemoteConfig.activateFetched();

            }

            if (listener != null) {

                listener.syncCompleted(task, task.isSuccessful());  // Send callback.
            }
        });


    }


}

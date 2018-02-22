package com.product.pustak.handler.RemoteConfigHandler;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
                .setDeveloperModeEnabled(true)
                .build();

        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        mFirebaseRemoteConfig.fetch(5).addOnCompleteListener(activity, new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {

                PROCESSING = false;
                if (task.isSuccessful()) {

                    mFirebaseRemoteConfig.activateFetched();

                } else {

                    Toast.makeText(activity, "Unable to sync remote config values", Toast.LENGTH_SHORT).show();
                }

                if (listener != null) {

                    listener.syncCompleted(task, task.isSuccessful());  // Send callback.
                }
            }
        });


    }


}

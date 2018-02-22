package com.product.pustak.handler.RemoteConfigHandler;


import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;

/**
 * Interface to listen for Firebase Remote Sync.
 */
public interface RemoteSyncListener {

    /**
     * Callback method for syncCompleted with status = false (failed in sync) or true = (successful sync).
     *
     * @param task   Sync response.
     * @param status boolean true = successful sync. else false = failed to sync data.
     */
    void syncCompleted(@NonNull Task<Void> task, boolean status);

}

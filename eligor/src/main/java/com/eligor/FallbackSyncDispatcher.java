package com.eligor;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author keishin.yokomaku
 * @since 2014/05/19
 */
/* package */ class FallbackSyncDispatcher implements Runnable {
    private final IPeriodicSyncManager mManager;
    private final Bundle mExtras;

    public FallbackSyncDispatcher(@NonNull IPeriodicSyncManager manager, @Nullable Bundle extras) {
        mManager = manager;
        mExtras = extras;
    }

    @Override
    public void run() {
        mManager.getFallbackRunnable().onPerformSync(mExtras);
    }
}

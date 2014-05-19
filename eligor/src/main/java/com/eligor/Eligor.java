/*
 * Copyright (C) 2014 nohana, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.eligor;

import android.content.ContentResolver;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Contract of the content provider periodic sync control.
 * @author KeithYokoma
 * @since 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("unused") // public API
public final class Eligor {
    public static final String TAG = Eligor.class.getSimpleName();
    private static volatile Eligor sInstance;
    private final int mDefaultPeriod;
    private final Map<String, IPeriodicSyncManager> mSyncManagers;

    /**
     * Construct this instance with the default period of the sync.
     * @param defaultPeriod default period of the automatic sync.
     */
    /* package */ Eligor(int defaultPeriod) {
        mDefaultPeriod = defaultPeriod;
        mSyncManagers = new HashMap<String, IPeriodicSyncManager>();
    }

    /**
     * Initialize singleton instance of this class.
     * @param defaultPeriod default period of the automatic sync.
     */
    public static void initialize(int defaultPeriod) {
        if (sInstance != null) {
            Log.i(TAG, TAG + " is already initialized.");
            return;
        }
        synchronized (Eligor.class) {
            if (sInstance == null) {
                sInstance = new Eligor(defaultPeriod);
            }
        }
    }

    /**
     * Returns a singleton instance of this class.
     * @return this instance.
     */
    public static synchronized Eligor getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(TAG + " is not initialized yet. Call initialize() first.");
        }
        return sInstance;
    }

    /**
     * Terminate and release all references of this class.
     */
    public static synchronized void destroy() {
        sInstance = null;
    }

    /**
     * Checks the settings whether the master automatic periodic sync is enabled or not on the phone.
     * @return true if enabled on the master setting, false otherwise.
     */
    public boolean isMasterSyncEnabled() {
        return ContentResolver.getMasterSyncAutomatically();
    }

    /**
     * Register periodic sync manager instance.
     * @param manager periodic sync manager for the {@link android.accounts.Account} and authority.
     */
    public void registerPeriodicSyncManager(IPeriodicSyncManager manager) {
        mSyncManagers.put(manager.getAuthority(), manager);
    }

    /**
     * Returns an instance of the registered periodic sync manager.
     * @param authority the periodic sync manager is associated with.
     * @return periodic sync manager. <code>null</code> if not registered for the authority.
     */
    public @Nullable IPeriodicSyncManager getPeriodicSyncManager(String authority) {
        return mSyncManagers.get(authority);
    }

    /**
     * Release registered periodic sync manager instance from this contract.
     * @param authority the periodic sync manager is associated with.
     */
    public void unregisterPeriodicSyncManager(String authority) {
        mSyncManagers.remove(authority);
    }

    /**
     * Apply automatic sync period with default value for all of the registered {@link com.eligor.IPeriodicSyncManager}.
     */
    public void applySyncPeriod() {
        applySyncPeriod(mDefaultPeriod);
    }

    /**
     * Apply automatic sync period with default value and extra arguments for all of the registered {@link com.eligor.IPeriodicSyncManager}.
     * @param args extra arguments for the {@link android.content.AbstractThreadedSyncAdapter}.
     */
    public void applySyncPeriod(Bundle args) {
        applySyncPeriod(mDefaultPeriod, args);
    }

    /**
     * Apply automatic sync period with the specified value in seconds for all of the registered {@link com.eligor.IPeriodicSyncManager}.
     * @param period automatic sync period in seconds.
     */
    public void applySyncPeriod(int period) {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.applySyncPeriod(period);
        }
    }

    /**
     * Apply automatic sync period with the specified value in seconds and extra arguments for all of the registered {@link com.eligor.IPeriodicSyncManager}.
     * @param period automatic sync period in seconds.
     * @param args extra arguments for the {@link android.content.AbstractThreadedSyncAdapter}.
     */
    public void applySyncPeriod(int period, Bundle args) {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.applySyncPeriod(period, args);
        }
    }

    /**
     * Apply automatic sync period with the default value for the specified authority's {@link com.eligor.IPeriodicSyncManager}.
     * @param authority the periodic sync manager is associated with.
     */
    public void applySyncPeriod(String authority) {
        applySyncPeriod(authority, mDefaultPeriod);
    }

    /**
     * Apply automatic sync period with the specified value in seconds for the specified authority's {@link com.eligor.IPeriodicSyncManager}.
     * @param authority the periodic sync manager is associated with.
     * @param period automatic sync period in seconds.
     */
    public void applySyncPeriod(String authority, int period) {
        IPeriodicSyncManager manager = getPeriodicSyncManager(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.applySyncPeriod(period);
    }

    /**
     * Apply automatic sync period with the default value and extra arguments for the specified authority's {@link com.eligor.IPeriodicSyncManager}.
     * @param authority the periodic sync manager is associated with.
     * @param args extra arguments for the {@link android.content.AbstractThreadedSyncAdapter}.
     */
    public void applySyncPeriod(String authority, Bundle args) {
        applySyncPeriod(authority, mDefaultPeriod, args);
    }

    /**
     * Apply automatic sync period with the specified value in seconds and extra arguments for the specified authority's {@link com.eligor.IPeriodicSyncManager}.
     * @param authority the periodic sync manager is associated with.
     * @param period automatic sync period in seconds.
     * @param args extra arguments for the {@link android.content.AbstractThreadedSyncAdapter}.
     */
    public void applySyncPeriod(String authority, int period, Bundle args) {
        IPeriodicSyncManager manager = getPeriodicSyncManager(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.applySyncPeriod(period, args);
    }

    /**
     * Request on demand sync for all of the registered {@link com.eligor.IPeriodicSyncManager}.
     */
    public void requestSync() {
        requestSync(false);
    }

    public void requestSync(boolean enableFallback) {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            if (isMasterSyncEnabled()) {
                manager.requestSync();
            } else if (enableFallback) {
                FallbackRunnableExecutor.process(new FallbackSyncDispatcher(manager, null));
            }
        }
    }

    /**
     * Request on demand sync for all of the registered {@link com.eligor.IPeriodicSyncManager} with the extra arguments.
     * @param args extra arguments for the {@link android.content.AbstractThreadedSyncAdapter}.
     */
    public void requestSync(Bundle args) {
        requestSync(args, false);
    }

    public void requestSync(Bundle args, boolean enableFallbak) {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            if (isMasterSyncEnabled()) {
                manager.requestSync(args);
            } else if (enableFallbak) {
                FallbackRunnableExecutor.process(new FallbackSyncDispatcher(manager, args));
            }
        }
    }

    /**
     * Request on demand sync for the specified authority's {@link com.eligor.IPeriodicSyncManager}.
     * @param authority the periodic sync manager is associated with.
     */
    public void requestSync(String authority) {
        requestSync(authority, false);
    }

    public void requestSync(String authority, boolean enableFallback) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        if (isMasterSyncEnabled()) {
            manager.requestSync();
        } else if (enableFallback) {
            FallbackRunnableExecutor.process(new FallbackSyncDispatcher(manager, null));
        }
    }

    /**
     * Request on demand sync for the specified authority's {@link com.eligor.IPeriodicSyncManager} with the extra arguments.
     * @param authority the periodic sync manager is associated with.
     * @param args extra arguments for the {@link android.content.AbstractThreadedSyncAdapter}.
     */
    public void requestSync(String authority, Bundle args) {
        requestSync(authority, args, false);
    }

    public void requestSync(String authority, Bundle args, boolean enableFallback) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        if (isMasterSyncEnabled()) {
            manager.requestSync(args);
        } else if (enableFallback) {
            FallbackRunnableExecutor.process(new FallbackSyncDispatcher(manager, args));
        }
    }

    /**
     * Request to cancel currently working or enqueued sync operation for all of the registered {@link com.eligor.IPeriodicSyncManager}.
     */
    public void cancelSync() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.cancelSync();
        }
    }

    /**
     * Request to cancel currently working or enqueued sync operation for the specified authority's {@link com.eligor.IPeriodicSyncManager}.
     * @param authority the periodic sync manager is associated with.
     */
    public void cancelSync(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.cancelSync();
    }

    /**
     * Set sync setting as enabled for all of the registered {@link com.eligor.IPeriodicSyncManager}.
     */
    public void enableSync() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.enableSync();
        }
    }

    /**
     * Set sync setting as enabled for the specified authority's {@link com.eligor.IPeriodicSyncManager}.
     * @param authority the periodic sync manager is associated with.
     */
    public void enableSync(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.enableSync();
    }

    /**
     * Set sync setting as disabled for all of the registered {@link com.eligor.IPeriodicSyncManager}.
     */
    public void disableSync() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.disableSync();
        }
    }

    /**
     * Set sync setting as disabled for the specified authority's {@link com.eligor.IPeriodicSyncManager}.
     * @param authority the periodic sync manager is associated with.
     */
    public void disableSync(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.disableSync();
    }

    /**
     * Set the {@link android.content.ContentProvider} as syncable for all of the registered {@link com.eligor.IPeriodicSyncManager}.
     */
    public void setSyncable() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.setSyncable();
        }
    }

    /**
     * Set the {@link android.content.ContentProvider} as syncable for the specified authority's {@link com.eligor.IPeriodicSyncManager}.
     * @param authority the periodic sync manager is associated with.
     */
    public void setSyncable(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.setSyncable();
    }

    /**
     * Set the {@link android.content.ContentProvider} as not syncable for all of the registered {@link com.eligor.IPeriodicSyncManager}.
     */
    public void setNotSyncable() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.setNotSyncable();
        }
    }

    /**
     * Set the {@link android.content.ContentProvider} as not syncable for the specified authority's {@link com.eligor.IPeriodicSyncManager}.
     * @param authority the periodic sync manager is associated with.
     */
    public void setNotSyncable(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.setNotSyncable();
    }

    /**
     * Checks if all of the registered {@link com.eligor.IPeriodicSyncManager} sync enabled.
     * @return true all sync is enabled, false otherwise.
     */
    public boolean isSyncEnabled() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            if (!manager.isSyncEnabled()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the specified {@link com.eligor.IPeriodicSyncManager} sync enabled.
     * @param authority the periodic sync manager is associated with.
     * @return true if the sync of the authority enabled, false otherwise.
     */
    public boolean isSyncEnabled(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return false;
        }
        return manager.isSyncEnabled();
    }

    /**
     * Checks if all of the registered {@link com.eligor.IPeriodicSyncManager} sync active.
     * @return true all sync is active, false otherwise.
     */
    public boolean isSyncActive() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            if (!manager.isSyncActive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the specified {@link com.eligor.IPeriodicSyncManager} sync active.
     * @param authority the periodic sync manager is associated with.
     * @return true if the sync of the authority active, false otherwise.
     */
    public boolean isSyncActive(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return false;
        }
        return manager.isSyncActive();
    }

    /**
     * Checks if all of the registered {@link com.eligor.IPeriodicSyncManager} sync pending.
     * @return true all sync is pending, false otherwise.
     */
    public boolean isSyncPending() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            if (!manager.isSyncPending()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the specified {@link com.eligor.IPeriodicSyncManager} sync pending.
     * @param authority the periodic sync manager is associated with.
     * @return true if the sync of the authority is pending, false otherwise.
     */
    public boolean isSyncPending(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return false;
        }
        return manager.isSyncPending();
    }

    /**
     * Checks if the specified provider authority is syncable or not.
     * @param authority the periodic sync manager is associated with.
     * @return true if syncable, false otherwise.
     */
    public boolean isSyncable(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return false;
        }
        return manager.isSyncable();
    }
}

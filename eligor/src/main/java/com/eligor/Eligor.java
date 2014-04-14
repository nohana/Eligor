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

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
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

    /* package */ Eligor(int defaultPeriod) {
        mDefaultPeriod = defaultPeriod;
        mSyncManagers = new HashMap<String, IPeriodicSyncManager>();
    }

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

    public static synchronized Eligor getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(TAG + " is not initialized yet. Call initialize() first.");
        }
        return sInstance;
    }

    public static synchronized void destroy() {
        sInstance = null;
    }

    public void registerPeriodicSyncManager(IPeriodicSyncManager manager) {
        mSyncManagers.put(manager.getAuthority(), manager);
    }

    public IPeriodicSyncManager getPeriodicSyncManager(String authority) {
        return mSyncManagers.get(authority);
    }

    public void unregisterPeriodicSyncManager(String authority) {
        mSyncManagers.remove(authority);
    }

    public void applySyncPeriod() {
        applySyncPeriod(mDefaultPeriod);
    }

    public void applySyncPeriod(Bundle args) {
        applySyncPeriod(mDefaultPeriod, args);
    }

    public void applySyncPeriod(int period) {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.applySyncPeriod(period);
        }
    }

    public void applySyncPeriod(int period, Bundle args) {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.applySyncPeriod(period, args);
        }
    }

    public void applySyncPeriod(String authority) {
        applySyncPeriod(authority, mDefaultPeriod);
    }

    public void applySyncPeriod(String authority, int period) {
        IPeriodicSyncManager manager = getPeriodicSyncManager(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.applySyncPeriod(period);
    }

    public void applySyncPeriod(String authority, Bundle args) {
        applySyncPeriod(authority, mDefaultPeriod, args);
    }

    public void applySyncPeriod(String authority, int period, Bundle args) {
        IPeriodicSyncManager manager = getPeriodicSyncManager(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.applySyncPeriod(period, args);
    }

    public void requestSync() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.requestSync();
        }
    }

    public void requestSync(Bundle args) {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.requestSync(args);
        }
    }

    public void requestSync(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.requestSync();
    }

    public void requestSync(String authority, Bundle args) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.requestSync(args);
    }

    public void cancelSync() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.cancelSync();
        }
    }

    public void cancelSync(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.cancelSync();
    }

    public void enableSync() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.enableSync();
        }
    }

    public void enableSync(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.enableSync();
    }

    public void disableSync() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.disableSync();
        }
    }

    public void disableSync(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.disableSync();
    }

    public void setSyncable() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.setSyncable();
        }
    }

    public void setSyncable(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.setSyncable();
    }

    public void setNotSyncable() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            manager.setNotSyncable();
        }
    }

    public void setNotSyncable(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return;
        }
        manager.setNotSyncable();
    }

    public boolean isSyncEnabled() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            if (!manager.isSyncEnabled()) {
                return false;
            }
        }
        return true;
    }

    public boolean isSyncEnabled(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return false;
        }
        return manager.isSyncEnabled();
    }

    public boolean isSyncActive() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            if (!manager.isSyncActive()) {
                return false;
            }
        }
        return true;
    }

    public boolean isSyncActive(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return false;
        }
        return manager.isSyncActive();
    }

    public boolean isSyncPending() {
        for (IPeriodicSyncManager manager : mSyncManagers.values()) {
            if (!manager.isSyncPending()) {
                return false;
            }
        }
        return true;
    }

    public boolean isSyncPending(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return false;
        }
        return manager.isSyncPending();
    }

    public boolean isSyncable(String authority) {
        IPeriodicSyncManager manager = mSyncManagers.get(authority);
        if (manager == null) {
            Log.i(TAG, "unknown authority for the request. ensure to call registerPeriodicSyncManager(IPeriodicSyncManager) first.");
            return false;
        }
        return manager.isSyncable();
    }
}

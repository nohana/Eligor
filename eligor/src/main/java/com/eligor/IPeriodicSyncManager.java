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

import android.accounts.Account;
import android.os.Bundle;
import android.support.annotation.NonNull;


/**
 * Interface to deal with the wrapper of periodic sync manager.
 * @author KeithYokoma
 * @since 1.0.0
 * @version 1.0.0
 */
public interface IPeriodicSyncManager {

    /**
     * Applies sync period and enqueue the sync request.
     * After the successful application, the sync will automatically requested by the framework.
     *
     * @see android.content.ContentResolver#addPeriodicSync(android.accounts.Account, String, android.os.Bundle, long)
     * @param period the period of each sync.
     */
    public void applySyncPeriod(int period);

    /**
     * Applies sync period and enqueue the sync request.
     * After the successful application, the sync will automatically requested by the framework.
     *
     * @see android.content.ContentResolver#addPeriodicSync(android.accounts.Account, String, android.os.Bundle, long)
     * @param period the period of each sync.
     * @param args extra arguments for the request.
     */
    public void applySyncPeriod(int period, Bundle args);

    /**
     * Request on demand sync.
     *
     * @see android.content.ContentResolver#requestSync(android.accounts.Account, String, android.os.Bundle)
     */
    public void requestSync();

    /**
     * Requests on demand sync.
     *
     * @see android.content.ContentResolver#requestSync(android.accounts.Account, String, android.os.Bundle)
     * @param args extra arguments for the request.
     */
    public void requestSync(Bundle args);

    /**
     * Cancels current sync progress.
     *
     * @see android.content.ContentResolver#cancelSync(android.accounts.Account, String)
     */
    public void cancelSync();

    /**
     * Enables periodic sync.
     *
     * @see android.content.ContentResolver#setSyncAutomatically(android.accounts.Account, String, boolean)
     */
    public void enableSync();

    /**
     * Disables periodic sync.
     *
     * @see android.content.ContentResolver#setSyncAutomatically(android.accounts.Account, String, boolean)
     */
    public void disableSync();

    /**
     * Sets the provider authority as syncable.
     *
     * @see android.content.ContentResolver#setIsSyncable(android.accounts.Account, String, int)
     */
    public void setSyncable();

    /**
     * Sets the provider authority as not syncable.
     *
     * @see android.content.ContentResolver#setIsSyncable(android.accounts.Account, String, int)
     */
    public void setNotSyncable();

    /**
     * Checks if the provider authority has been configured as automatic periodic sync enabled for the account or not.
     *
     * @see android.content.ContentResolver#getSyncAutomatically(android.accounts.Account, String)
     * @return true if enabled, false otherwise.
     */
    public boolean isSyncEnabled();

    /**
     * Checks if the periodic sync of the provider authority is currently working or not.
     *
     * @see android.content.ContentResolver#isSyncActive(android.accounts.Account, String)
     * @return true if currently active, false otherwise.
     */
    public boolean isSyncActive();

    /**
     * Checks if the periodic sync of the provider authority is currently pending to work or not.
     *
     * @see android.content.ContentResolver#isSyncPending(android.accounts.Account, String)
     * @return true if currently pending, false otherwise.
     */
    public boolean isSyncPending();

    /**
     * Checks if the periodic sync of the provider authority is set as syncable or not.
     *
     * @see android.content.ContentResolver#getIsSyncable(android.accounts.Account, String)
     * @return true if syncable, false otherwise.
     */
    public boolean isSyncable();

    /**
     * Returns {@link android.accounts.Account} that is associated for the periodic sync of the auhtority.
     * @return an account. May not be null.
     */
    public @NonNull Account getAccount();

    /**
     * Returns provider authority for the periodic sync.
     * @return an authority. May not be null.
     */
    public @NonNull String getAuthority();

    /**
     *
     * @return
     */
    public @NonNull FallbackRunnable getFallbackRunnable();
}

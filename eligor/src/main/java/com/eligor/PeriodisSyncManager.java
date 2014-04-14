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

import org.jetbrains.annotations.NotNull;

import android.accounts.Account;
import android.content.ContentResolver;
import android.os.Bundle;

/**
 * Concrete class that is an implementation of {@link com.eligor.IPeriodicSyncManager}.
 * @author KeithYokoma
 * @since 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("unused")
public class PeriodisSyncManager extends AbstractPeriodicSyncManager {
    public PeriodisSyncManager(@NotNull Account account, @NotNull String authority) {
        super(account, authority);
    }

    @Override
    public void applySyncPeriod(int period) {
        ContentResolver.addPeriodicSync(getAccount(), getAuthority(), new Bundle(), period);
    }

    @Override
    public void applySyncPeriod(int period, Bundle args) {
        ContentResolver.addPeriodicSync(getAccount(), getAuthority(), args, period);
    }

    @Override
    public void requestSync() {
        ContentResolver.requestSync(getAccount(), getAuthority(), new Bundle());
    }

    @Override
    public void requestSync(Bundle args) {
        ContentResolver.requestSync(getAccount(), getAuthority(), args);
    }

    @Override
    public void cancelSync() {
        ContentResolver.cancelSync(getAccount(), getAuthority());
    }

    @Override
    public void enableSync() {
        ContentResolver.setSyncAutomatically(getAccount(), getAuthority(), true);
    }

    @Override
    public void disableSync() {
        ContentResolver.setSyncAutomatically(getAccount(), getAuthority(), false);
    }

    @Override
    public void setSyncable() {
        ContentResolver.setIsSyncable(getAccount(), getAuthority(), FLAG_SYNCABLE);
    }

    @Override
    public void setNotSyncable() {
        ContentResolver.setIsSyncable(getAccount(), getAuthority(), FLAG_NOT_SYNCABLE);
    }

    @Override
    public boolean isSyncable() {
        int flag = ContentResolver.getIsSyncable(getAccount(), getAuthority());
        return flag > FLAG_NOT_SYNCABLE; // according to the ContentResolver javadoc note.
    }

    @Override
    public boolean isSyncEnabled() {
        return ContentResolver.getSyncAutomatically(getAccount(), getAuthority());
    }

    @Override
    public boolean isSyncActive() {
        return ContentResolver.isSyncActive(getAccount(), getAuthority());
    }

    @Override
    public boolean isSyncPending() {
        return ContentResolver.isSyncPending(getAccount(), getAuthority());
    }
}

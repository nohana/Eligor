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
import android.content.ContentResolver;
import android.os.Bundle;

import javax.annotation.Nonnull;

/**
 * Concrete class that is an implementation of {@link com.eligor.IPeriodicSyncManager}.
 * @author KeithYokoma
 * @since 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("unused")
public class SimplePeriodicSyncManager extends AbstractPeriodicSyncManager {
    /**
     * {@inheritDoc}
     */
    public SimplePeriodicSyncManager(@Nonnull Account account, @Nonnull String authority) {
        super(account, authority);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applySyncPeriod(int period) {
        ContentResolver.addPeriodicSync(getAccount(), getAuthority(), new Bundle(), period);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applySyncPeriod(int period, Bundle args) {
        ContentResolver.addPeriodicSync(getAccount(), getAuthority(), args, period);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void requestSync() {
        ContentResolver.requestSync(getAccount(), getAuthority(), new Bundle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void requestSync(Bundle args) {
        ContentResolver.requestSync(getAccount(), getAuthority(), args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelSync() {
        ContentResolver.cancelSync(getAccount(), getAuthority());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enableSync() {
        ContentResolver.setSyncAutomatically(getAccount(), getAuthority(), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableSync() {
        ContentResolver.setSyncAutomatically(getAccount(), getAuthority(), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSyncable() {
        ContentResolver.setIsSyncable(getAccount(), getAuthority(), FLAG_SYNCABLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNotSyncable() {
        ContentResolver.setIsSyncable(getAccount(), getAuthority(), FLAG_NOT_SYNCABLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSyncable() {
        int flag = ContentResolver.getIsSyncable(getAccount(), getAuthority());
        return flag > FLAG_NOT_SYNCABLE; // according to the ContentResolver javadoc note.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSyncEnabled() {
        return ContentResolver.getSyncAutomatically(getAccount(), getAuthority());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSyncActive() {
        return ContentResolver.isSyncActive(getAccount(), getAuthority());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSyncPending() {
        return ContentResolver.isSyncPending(getAccount(), getAuthority());
    }
}

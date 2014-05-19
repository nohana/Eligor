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
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import javax.annotation.Nonnull;

/**
 * Concrete class that is an implementation of {@link com.eligor.IPeriodicSyncManager}.
 * This class holds sync preferences to determine if the sync should be applied or not.
 * So if the sync disabled on the preference, the periodic sync won't be applied, but the manual sync is still be enabled.
 * @author KeithYokoma
 * @since 1.1.0
 * @version 1.0.0
 */
@SuppressWarnings("unused")
public class PreferencedPeriodicSyncManager extends AbstractPeriodicSyncManager {
    private static final String KEY_PERIOD = "period";
    private static final String KEY_ENABLED = "enabled";
    private final boolean mDefaultEnabled;
    private SharedPreferences mPreferences;

    /**
     * {@inheritDoc}
     */
    public PreferencedPeriodicSyncManager(Context context, @Nonnull Account account, @Nonnull String authority, @Nonnull FallbackRunnable fallbackRunnable, boolean defaultEnabled) {
        super(account, authority, fallbackRunnable);
        mPreferences = context.getSharedPreferences(authority, Context.MODE_PRIVATE);
        mDefaultEnabled = defaultEnabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applySyncPeriod(int period) {
        boolean enabled = mPreferences.getBoolean(KEY_ENABLED, mDefaultEnabled);
        if (enabled) {
            applyEdit(mPreferences.edit().putInt(KEY_PERIOD, period));
            ContentResolver.addPeriodicSync(getAccount(), getAuthority(), new Bundle(), period);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applySyncPeriod(int period, Bundle args) {
        boolean enabled = mPreferences.getBoolean(KEY_ENABLED, mDefaultEnabled);
        if (enabled) {
            applyEdit(mPreferences.edit().putInt(KEY_PERIOD, period));
            ContentResolver.addPeriodicSync(getAccount(), getAuthority(), args, period);
        }
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
        applyEdit(mPreferences.edit().putBoolean(KEY_ENABLED, true));
        ContentResolver.setSyncAutomatically(getAccount(), getAuthority(), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableSync() {
        applyEdit(mPreferences.edit().putBoolean(KEY_ENABLED, false));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSyncable() {
        int flag = ContentResolver.getIsSyncable(getAccount(), getAuthority());
        return flag > FLAG_NOT_SYNCABLE; // according to the ContentResolver javadoc note.
    }

    @SuppressLint("NewApi") // it's ok to suppress lint that we known which to call for the api version
    private void applyEdit(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }
}

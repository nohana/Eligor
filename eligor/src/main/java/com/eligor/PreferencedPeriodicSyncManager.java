package com.eligor;

import org.jetbrains.annotations.NotNull;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

/**
 * @author KeithYokoma
 * @since 1.1.0
 * @version 1.0.0
 */
public class PreferencedPeriodicSyncManager extends AbstractPeriodicSyncManager {
    private static final String KEY_PERIOD = "period";
    private static final String KEY_ENABLED = "enabled";
    private final boolean mDefaultEnabled;
    private SharedPreferences mPreferences;

    public PreferencedPeriodicSyncManager(Context context, @NotNull Account account, @NotNull String authority, boolean defaultEnabled) {
        super(account, authority);
        mPreferences = context.getSharedPreferences(authority, Context.MODE_PRIVATE);
        mDefaultEnabled = defaultEnabled;
    }

    @Override
    public void applySyncPeriod(int period) {
        boolean enabled = mPreferences.getBoolean(KEY_ENABLED, mDefaultEnabled);
        if (enabled) {
            applyEdit(mPreferences.edit().putInt(KEY_PERIOD, period));
            ContentResolver.addPeriodicSync(getAccount(), getAuthority(), new Bundle(), period);
        }
    }

    @Override
    public void applySyncPeriod(int period, Bundle args) {
        boolean enabled = mPreferences.getBoolean(KEY_ENABLED, mDefaultEnabled);
        if (enabled) {
            applyEdit(mPreferences.edit().putInt(KEY_PERIOD, period));
            ContentResolver.addPeriodicSync(getAccount(), getAuthority(), new Bundle(), period);
        }
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
        applyEdit(mPreferences.edit().putBoolean(KEY_ENABLED, true));
        ContentResolver.setSyncAutomatically(getAccount(), getAuthority(), true);
    }

    @Override
    public void disableSync() {
        applyEdit(mPreferences.edit().putBoolean(KEY_ENABLED, false));
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

    @Override
    public boolean isSyncable() {
        int flag = ContentResolver.getIsSyncable(getAccount(), getAuthority());
        return flag > FLAG_NOT_SYNCABLE; // according to the ContentResolver javadoc note.
    }

    private void applyEdit(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            editor.commit();
        } else {
            editor.apply();
        }
    }
}

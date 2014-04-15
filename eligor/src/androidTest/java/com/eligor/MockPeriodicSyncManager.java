package com.eligor;

import org.jetbrains.annotations.NotNull;

import android.accounts.Account;
import android.os.Bundle;

/**
 * @author KeithYokoma
 * @since 2014/04/14
 */
public class MockPeriodicSyncManager extends AbstractPeriodicSyncManager {
    public MockPeriodicSyncManager(@NotNull Account account, @NotNull String authority) {
        super(account, authority);
    }

    @Override
    public void applySyncPeriod(int period) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void applySyncPeriod(int period, Bundle args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void requestSync() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void requestSync(Bundle args) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void cancelSync() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void enableSync() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disableSync() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSyncable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNotSyncable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSyncEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSyncActive() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSyncPending() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSyncable() {
        throw new UnsupportedOperationException();
    }
}

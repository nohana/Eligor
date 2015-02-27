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
import android.support.annotation.NonNull;

/**
 * Skeleton class for the convenience to implement {@link com.eligor.IPeriodicSyncManager}.
 * @author KeithYokoma
 * @since 1.0.0
 * @version 1.0.0
 */
abstract class AbstractPeriodicSyncManager implements IPeriodicSyncManager {
    public static final int FLAG_SYNCABLE = 1;
    public static final int FLAG_NOT_SYNCABLE = 0;
    public static final int FLAG_UNKNOWN = -1;

    private final Account mAccount;
    private final String mAuthority;
    private final FallbackRunnable mFallbackRunnable;

    /**
     * Create a new instance of the {@link com.eligor.IPeriodicSyncManager} with associated {@link android.accounts.Account} and authority.
     * @param account associated account of the sync, may not be null.
     * @param authority associated provider authority of the sync, may not be null.
     * @param fallbackRunnable fallback runnable that is executed if the master sync is disabled.
     */
    /* package */ AbstractPeriodicSyncManager(@NonNull Account account, @NonNull String authority, @NonNull FallbackRunnable fallbackRunnable) {
        mAccount = account;
        mAuthority = authority;
        mFallbackRunnable = fallbackRunnable;
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public Account getAccount() {
        return mAccount;
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public String getAuthority() {
        return mAuthority;
    }

    @NonNull
    @Override
    public FallbackRunnable getFallbackRunnable() {
        return mFallbackRunnable;
    }
}

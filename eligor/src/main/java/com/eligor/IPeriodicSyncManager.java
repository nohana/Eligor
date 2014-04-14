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
import android.os.Bundle;

/**
 * Interface to deal with the wrapper of periodic sync manager.
 * @author KeithYokoma
 * @since 1.0.0
 * @version 1.0.0
 */
public interface IPeriodicSyncManager {
    public void applySyncPeriod(int period);
    public void applySyncPeriod(int period, Bundle args);
    public void requestSync();
    public void requestSync(Bundle args);
    public void cancelSync();
    public void enableSync();
    public void disableSync();
    public void setSyncable();
    public void setNotSyncable();
    public boolean isSyncEnabled();
    public boolean isSyncActive();
    public boolean isSyncPending();
    public boolean isSyncable();
    public @NotNull Account getAccount();
    public @NotNull String getAuthority();
}

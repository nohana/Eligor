package com.eligor;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;

/**
 * @author keishin.yokomaku
 * @since 2014/04/15
 */
public class PreferencedPeriodicSyncManagerTest extends AndroidTestCase {
    private static final String MOCK_ACCOUNT_NAME = "account";
    private static final String MOCK_ACCOUNT_TYPE = "type";
    private static final String MOCK_AUTHORITY = "authority";
    private static final int DEFAULT_PERIOD = 1000;
    private MockPreferenceContext mMockPreferenceContext;
    private PreferencedPeriodicSyncManager mDefaultEnabledManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockPreferenceContext = new MockPreferenceContext(getContext());
        mDefaultEnabledManager = new PreferencedPeriodicSyncManager(
                mMockPreferenceContext,
                new Account(MOCK_ACCOUNT_NAME, MOCK_ACCOUNT_TYPE),
                MOCK_AUTHORITY, true);
    }

    public void testSyncSettingChange() throws Exception{
        SharedPreferences preferences = mMockPreferenceContext.getSharedPreferences(MOCK_AUTHORITY, Context.MODE_PRIVATE); // XXX

        mDefaultEnabledManager.disableSync();
        assertFalse(preferences.getBoolean("enabled", true));

        mDefaultEnabledManager.enableSync();
        assertTrue(preferences.getBoolean("enabled", false));
    }
}

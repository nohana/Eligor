package com.eligor;

import android.accounts.Account;
import android.os.Bundle;
import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;

/**
 * @author keishin.yokomaku
 * @since 2014/04/14
 */
public class EligorTest extends AndroidTestCase {
    private static final String MOCK_ACCOUNT_NAME = "account";
    private static final String MOCK_ACCOUNT_TYPE = "type";
    private static final String MOCK_AUTHORITY = "authority";
    private static final int DEFAULT_PERIOD = 1000;
    private Account mMockAccount;
    private Eligor mEligor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Eligor.initialize(DEFAULT_PERIOD);
        mEligor = Eligor.getInstance();
        mMockAccount = new Account(MOCK_ACCOUNT_NAME, MOCK_ACCOUNT_TYPE);

    }

    @Override
    protected void tearDown() throws Exception {
        mEligor.unregisterPeriodicSyncManager(MOCK_AUTHORITY);
        super.tearDown();
    }

    public void testRegisterPeriodicSyncManager() throws Exception {
        assertNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));
        MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount, MOCK_AUTHORITY);

        mEligor.registerPeriodicSyncManager(manager);

        assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

        MockPeriodicSyncManager another = new MockPeriodicSyncManager(mMockAccount, MOCK_AUTHORITY);

        mEligor.registerPeriodicSyncManager(another);

        assertNotSame(manager, mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));
        assertSame(another, mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));
    }

    public void testUnregisterPeriodicSyncManager() throws Exception {
        MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount, MOCK_AUTHORITY);
        mEligor.registerPeriodicSyncManager(manager);
        assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

        mEligor.unregisterPeriodicSyncManager(MOCK_AUTHORITY);
        assertNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));
    }

    public void testApplySyncPeriod() throws Exception {
        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void applySyncPeriod(int period) {
                    assertEquals(DEFAULT_PERIOD, period);
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.applySyncPeriod();
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void applySyncPeriod(int period) {
                    assertEquals(1001, period);
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.applySyncPeriod(1001);
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void applySyncPeriod(int period, Bundle args) {
                    assertEquals(DEFAULT_PERIOD, period);
                    assertNotNull(args);
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.applySyncPeriod(new Bundle());
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void applySyncPeriod(int period, Bundle args) {
                    assertEquals(1001, period);
                    assertNotNull(args);
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.applySyncPeriod(1001, new Bundle());
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void applySyncPeriod(int period) {
                    assertEquals(DEFAULT_PERIOD, period);
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.applySyncPeriod("some_authority", DEFAULT_PERIOD);
            assertEquals(1, mLatch.getCount());
            mEligor.applySyncPeriod(MOCK_AUTHORITY, DEFAULT_PERIOD);
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void applySyncPeriod(int period, Bundle args) {
                    assertEquals(DEFAULT_PERIOD, period);
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.applySyncPeriod("some_authority", DEFAULT_PERIOD, new Bundle());
            assertEquals(1, mLatch.getCount());
            mEligor.applySyncPeriod(MOCK_AUTHORITY, DEFAULT_PERIOD, new Bundle());
            mLatch.await();
        }
    }

    public void testRequestSync() throws Exception {
        
    }
}

package com.eligor;

import android.accounts.Account;
import android.os.Bundle;
import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;

/**
 * @author KeithYokoma
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
        Eligor.destroy();
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
        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void requestSync() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.requestSync();
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void requestSync() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.requestSync("");
            assertEquals(1, mLatch.getCount());
            mEligor.requestSync(MOCK_AUTHORITY);
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void requestSync(Bundle args) {
                    assertNotNull(args);
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.requestSync(new Bundle());
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void requestSync(Bundle args) {
                    assertNotNull(args);
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.requestSync("", new Bundle());
            assertEquals(1, mLatch.getCount());
            mEligor.requestSync(MOCK_AUTHORITY, new Bundle());
            mLatch.await();
        }
    }

    public void testCancelSync() throws Exception {
        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void cancelSync() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.cancelSync();
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void cancelSync() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.cancelSync("");
            assertEquals(1, mLatch.getCount());
            mEligor.cancelSync(MOCK_AUTHORITY);
            mLatch.await();
        }
    }

    public void testEnableSync() throws Exception {
        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void enableSync() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.enableSync();
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void enableSync() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.enableSync("");
            assertEquals(1, mLatch.getCount());
            mEligor.enableSync(MOCK_AUTHORITY);
            mLatch.await();
        }
    }

    public void testDisableSync() throws Exception {
        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void disableSync() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.disableSync();
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void disableSync() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.disableSync("");
            assertEquals(1, mLatch.getCount());
            mEligor.disableSync(MOCK_AUTHORITY);
            mLatch.await();
        }
    }

    public void testSetSyncable() throws Exception {
        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void setSyncable() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.setSyncable();
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void setSyncable() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.setSyncable("");
            assertEquals(1, mLatch.getCount());
            mEligor.setSyncable(MOCK_AUTHORITY);
            mLatch.await();
        }
    }

    public void testSetNotSyncable() throws Exception {
        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void setNotSyncable() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.setNotSyncable();
            mLatch.await();
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public void setNotSyncable() {
                    mLatch.countDown();
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            mEligor.setNotSyncable("");
            assertEquals(1, mLatch.getCount());
            mEligor.setNotSyncable(MOCK_AUTHORITY);
            mLatch.await();
        }
    }

    public void testIsSyncEnabled() throws Exception {
        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public boolean isSyncEnabled() {
                    mLatch.countDown();
                    return true;
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            assertTrue(mEligor.isSyncEnabled());
            mLatch.await();

            MockPeriodicSyncManager some = new MockPeriodicSyncManager(mMockAccount,
                    "some") {
                @Override
                public boolean isSyncEnabled() {
                    return true;
                }
            };
            mEligor.registerPeriodicSyncManager(some);
            assertTrue(mEligor.isSyncEnabled());

            MockPeriodicSyncManager another = new MockPeriodicSyncManager(mMockAccount,
                    "another") {
                @Override
                public boolean isSyncEnabled() {
                    return false;
                }
            };
            mEligor.registerPeriodicSyncManager(another);
            assertFalse(mEligor.isSyncEnabled());
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public boolean isSyncEnabled() {
                    mLatch.countDown();
                    return true;
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            assertFalse(mEligor.isSyncEnabled(""));
            assertEquals(1, mLatch.getCount());
            assertTrue(mEligor.isSyncEnabled(MOCK_AUTHORITY));
            mLatch.await();
        }
    }

    public void testIsSyncActive() throws Exception {
        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public boolean isSyncActive() {
                    mLatch.countDown();
                    return true;
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            assertTrue(mEligor.isSyncActive());
            mLatch.await();

            MockPeriodicSyncManager some = new MockPeriodicSyncManager(mMockAccount,
                    "some") {
                @Override
                public boolean isSyncActive() {
                    return true;
                }
            };
            mEligor.registerPeriodicSyncManager(some);
            assertTrue(mEligor.isSyncActive());

            MockPeriodicSyncManager another = new MockPeriodicSyncManager(mMockAccount,
                    "another") {
                @Override
                public boolean isSyncActive() {
                    return false;
                }
            };
            mEligor.registerPeriodicSyncManager(another);
            assertFalse(mEligor.isSyncActive());
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public boolean isSyncActive() {
                    mLatch.countDown();
                    return true;
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            assertFalse(mEligor.isSyncActive(""));
            assertEquals(1, mLatch.getCount());
            assertTrue(mEligor.isSyncActive(MOCK_AUTHORITY));
            mLatch.await();
        }
    }

    public void testIsSyncPending() throws Exception {
        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public boolean isSyncPending() {
                    mLatch.countDown();
                    return true;
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            assertTrue(mEligor.isSyncPending());
            mLatch.await();

            MockPeriodicSyncManager some = new MockPeriodicSyncManager(mMockAccount,
                    "some") {
                @Override
                public boolean isSyncPending() {
                    return true;
                }
            };
            mEligor.registerPeriodicSyncManager(some);
            assertTrue(mEligor.isSyncPending());

            MockPeriodicSyncManager another = new MockPeriodicSyncManager(mMockAccount,
                    "another") {
                @Override
                public boolean isSyncPending() {
                    return false;
                }
            };
            mEligor.registerPeriodicSyncManager(another);
            assertFalse(mEligor.isSyncPending());
        }

        {
            final CountDownLatch mLatch = new CountDownLatch(1);
            MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                    MOCK_AUTHORITY) {
                @Override
                public boolean isSyncPending() {
                    mLatch.countDown();
                    return true;
                }
            };
            mEligor.registerPeriodicSyncManager(manager);
            assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

            assertFalse(mEligor.isSyncPending(""));
            assertEquals(1, mLatch.getCount());
            assertTrue(mEligor.isSyncPending(MOCK_AUTHORITY));
            mLatch.await();
        }
    }

    public void testIsSyncable() throws Exception {
        final CountDownLatch mLatch = new CountDownLatch(1);
        MockPeriodicSyncManager manager = new MockPeriodicSyncManager(mMockAccount,
                MOCK_AUTHORITY) {
            @Override
            public boolean isSyncable() {
                mLatch.countDown();
                return true;
            }
        };
        mEligor.registerPeriodicSyncManager(manager);
        assertNotNull(mEligor.getPeriodicSyncManager(MOCK_AUTHORITY));

        assertFalse(mEligor.isSyncable(""));
        assertEquals(1, mLatch.getCount());
        assertTrue(mEligor.isSyncable(MOCK_AUTHORITY));
        mLatch.await();
    }
}

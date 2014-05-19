package com.eligor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author keishin.yokomaku
 * @since 2014/05/19
 */
/* package */ class FallbackRunnableExecutor {
    public static final String TAG = FallbackRunnableExecutor.class.getSimpleName();
    private static final int CORE_THREAD_POOL_SIZE = 3;
    private static final int MAX_THREAD_POOL_SIZE = 64;
    private static final int KEEP_ALIVE = 1;
    private static final int POOL_WORK_QUEUE_CAPACITY = 10;
    private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue<Runnable>(POOL_WORK_QUEUE_CAPACITY);
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, TAG + " #" + mCount.getAndIncrement());
        }
    };
    private static final Executor sThreadPoolExecutor = new ThreadPoolExecutor(
            CORE_THREAD_POOL_SIZE, MAX_THREAD_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sWorkQueue, sThreadFactory);

    public static void process(Runnable runnable) {
        sThreadPoolExecutor.execute(runnable);
    }
}

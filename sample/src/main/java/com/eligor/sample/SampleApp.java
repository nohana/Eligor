package com.eligor.sample;

import com.eligor.Eligor;

import android.app.Application;

/**
 * @author keishin.yokomaku
 * @since 2014/04/14
 */
public class SampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Eligor.initialize(1000); // sync period is set by default as every 1sec
    }

    @Override
    public void onTerminate() {
        Eligor.destroy();
        super.onTerminate();
    }
}

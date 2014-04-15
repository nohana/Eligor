package com.eligor;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.mock.MockContext;

/**
 * @author keishin.yokomaku
 * @since 2014/04/15
 */
public class MockPreferenceContext extends MockContext {
    public static final String SUFFIX_FILE_NAME = ".test";
    private Context mWrappedContext;

    public MockPreferenceContext(Context context) {
        mWrappedContext = context;
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return mWrappedContext.getSharedPreferences(name + SUFFIX_FILE_NAME, mode);
    }
}

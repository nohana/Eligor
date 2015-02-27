package com.eligor;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author keishin.yokomaku
 * @since 2014/05/19
 */
public interface FallbackRunnable {
    public void onPerformSync(@Nullable Bundle extras);
}

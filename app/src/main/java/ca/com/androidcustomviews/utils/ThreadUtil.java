package ca.com.androidcustomviews.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by hp on 2018/7/21.
 */

public class ThreadUtil {
    private static Executor executor = Executors.newSingleThreadExecutor();
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void executeSubThread(Runnable runnable) {
        executor.execute(runnable);
    }

    public static void executeMainThread(Runnable runnable) {
        handler.post(runnable);
    }

}

package com.github.sample;

import android.app.Application;

/**
 * Created by lvming on 12/17/20 4:42 PM.
 * Email: lvming@guazi.com
 * Description:
 */
public class KLogUtil {

    private static final String TAG = KLogUtil.class.getSimpleName();

    private KLogUtil() {
    }

    private static class KLogUtilHolder {
        private static final KLogUtil sInstance = new KLogUtil();
    }

    public static KLogUtil getInstance() {
        return KLogUtil.KLogUtilHolder.sInstance;
    }

    public void initKLog(Application application, boolean isInitXLog, boolean isDebug) {
    }
}

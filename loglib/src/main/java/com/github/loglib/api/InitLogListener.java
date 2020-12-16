package com.github.loglib.api;

import android.app.Application;

/**
 * Created by lvming on 12/14/20 6:10 PM.
 * Email: lvming@guazi.com
 * Description:
 */
public interface InitLogListener {
    void beforeInitLog(Application application);

    void init(Application application);

    void afterInitLog(Application application);
}

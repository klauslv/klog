package com.github.sample.impl;

import android.app.Application;
import android.util.Log;

import com.github.log.KLog;
import com.github.log.printer.BasePrinter;
import com.github.sample.FakeCrashLibrary;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

/**
 * Created by lvming on 12/9/20 10:45 AM.
 * Email: lvming@guazi.com
 * Description:timber框架
 * https://github.com/JakeWharton/timber
 */
public class TimberPrinter extends BasePrinter {
    @Override
    public void init(Application application) {
        if (KLog.getKLogConfig().isDebuggable()) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    @Override
    public void v(String tag, String msg) {
        Timber.tag(tag).v(msg);
    }

    @Override
    public void d(String tag, String msg) {
        Timber.tag(tag).d(msg);
    }

    @Override
    public void i(String tag, String msg) {
        Timber.tag(tag).i(msg);
    }

    @Override
    public void w(String tag, String msg) {
        Timber.tag(tag).w(msg);
    }

    @Override
    public void e(String tag, String msg) {
        Timber.tag(tag).e(msg);
    }

    @Override
    public void w(String tag, String msg, Throwable tr) {
        Timber.tag(tag).w(tr,msg);
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
        Timber.tag(tag).e(tr,msg);
    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}

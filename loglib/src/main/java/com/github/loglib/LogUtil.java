package com.github.loglib;

import android.app.Application;

import com.github.loglib.api.InitLogListener;

/**
 * Created by lvming on 12/7/20 3:19 PM.
 * Email: lvming@guazi.com
 * Description:日志打印类
 */
public final class LogUtil {


    public static LoggerImpl sLog = new LoggerImpl();

    private static LogConfig logConfig = LogConfig.getInstance();

    public static void init(Application application, InitLogListener initLogListener) {
        sLog = LoggerImpl.getsLoggerImpl();
        sLog.init(application, initLogListener);
    }

    public static void v(String tag, String msg) {
        sLog.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        sLog.v(tag, msg, tr);
    }

    public static void d(String tag, String msg) {
        sLog.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        sLog.d(tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        sLog.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        sLog.i(tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        sLog.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        sLog.w(tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        sLog.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        sLog.e(tag, msg, tr);
    }

    public static void json(String json) {
        sLog.json(json);
    }

    public static void xml(String xml) {
        sLog.xml(xml);
    }

    public static void v(String tag, final String format, final Object... obj) {
        sLog.v(tag, format, obj);
    }

    public static void d(String tag, final String format, final Object... obj) {
        sLog.d(tag, format, obj);
    }

    public static void i(String tag, final String format, final Object... obj) {
        sLog.i(tag, format, obj);
    }

    public static void w(String tag, final String format, final Object... obj) {
        sLog.w(tag, format, obj);
    }

    public static void e(String tag, final String format, final Object... obj) {
        sLog.e(tag, format, obj);
    }

    public static LogConfig getLogConfig() {
        return logConfig;
    }

    /**
     * 上传日志
     */
    public static void uploadLog() {
        logConfig.uploadLog();
    }
}

package com.github.sample.impl;

import android.app.Application;

import com.github.log.printer.BasePrinter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by lvming on 12/8/20 7:02 PM.
 * Email: lvming@guazi.com
 * Description: Logger框架
 * https://github.com/orhanobut/logger
 */
public class LoggerPrinter extends BasePrinter {

    @Override
    public void init(Application application) {
        Logger.clearLogAdapters();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(3)
//                .logStrategy(cu)
                .tag("klaus")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public void v(String tag, String msg) {
        Logger.t(tag).v(msg);
    }

    @Override
    public void d( String tag, String msg) {
        Logger.t(tag).d(msg);
    }

    @Override
    public void i(String tag, String msg) {
        Logger.t(tag).i(msg);
    }

    @Override
    public void w(String tag, String msg) {
        Logger.t(tag).w(msg);
    }

    @Override
    public void e(String tag, String msg) {
        Logger.t(tag).e(msg);
    }

    @Override
    public void w(String tag, String msg, Throwable tr) {
        Logger.t(tag).e(tr, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
        Logger.t(tag).e(tr, msg);
    }

    @Override
    public void json(String json) {
        Logger.json(json);
    }

    @Override
    public void xml(String xml) {
        Logger.xml(xml);
    }
}

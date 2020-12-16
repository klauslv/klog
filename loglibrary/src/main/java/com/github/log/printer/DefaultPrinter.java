package com.github.log.printer;

import android.app.Application;
import android.util.Log;


/**
 * Created by lvming on 12/7/20 4:17 PM.
 * Email: lvming@guazi.com
 * Description: 默认使用Android 原生的Log进行打印日志
 */
public class DefaultPrinter extends BasePrinter {
    @Override
    public void init(Application application) {
        Log.println(Log.INFO, "default", "安卓默认日志打印初始化");
    }

    @Override
    public void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    @Override
    public void w(String tag, String msg, Throwable tr) {
        Log.w(tag, msg, tr);
    }

    @Override
    public void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }
}

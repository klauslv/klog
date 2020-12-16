package com.github.log.printer;

import android.app.Application;

/**
 * Created by lvming on 12/7/20 2:47 PM.
 * Email: lvming@guazi.com
 * Description:日志打印接口
 */
public interface IPrinter {

    void init(Application application);

    void v(String tag, String msg);

    void d(String tag, String msg);

    void i(String tag, String msg);

    void w(String tag, String msg);

    void w(String tag, String msg, Throwable tr);

    void e(String tag, String msg);

    void e(String tag, String msg, Throwable tr);

    void json(String json);

    void xml(String xml);

}

package com.github.loglib;

import android.app.Application;

import com.github.loglib.api.InitLogListener;


/**
 * Created by lvming on 12/14/20 5:29 PM.
 * Email: lvming@guazi.com
 * Description:
 */
public class LoggerImpl {

    private static LoggerImpl sLoggerImpl = new LoggerImpl();


    public static LoggerImpl getsLoggerImpl() {
        return sLoggerImpl;
    }

    public static void setsLoggerImpl(LoggerImpl sLoggerImpl) {
        LoggerImpl.sLoggerImpl = sLoggerImpl;
    }

    public void init(Application application, InitLogListener initLogListener) {
        LogConfig.getInstance().beforeInit(application, initLogListener);
        LogConfig.getInstance().init(application, initLogListener);
        LogConfig.getInstance().afterInit(application, initLogListener);
    }

    public void v(String tag, String msg) {
        v(tag, msg, (Object[]) null);
    }

    public void v(String tag, String msg, Throwable tr) {
        w(tag, tr, msg, (Object[]) null);
    }

    public void d(String tag, String msg) {
        d(tag, msg, (Object[]) null);
    }

    public void d(String tag, String msg, Throwable tr) {
        w(tag, tr, msg, (Object[]) null);
    }

    public void i(String tag, String msg) {
        i(tag, msg, (Object[]) null);
    }

    public void i(String tag, String msg, Throwable tr) {
        w(tag, tr, msg, (Object[]) null);
    }

    public void w(String tag, String msg) {
        w(tag, msg, (Object[]) null);
    }

    public void w(String tag, String msg, Throwable tr) {
        w(tag, tr, msg, (Object[]) null);
    }

    public void e(String tag, String msg) {
        e(tag, msg, (Object[]) null);
    }

    public void e(String tag, String msg, Throwable tr) {
        e(tag, tr, msg, (Object[]) null);
    }

    public void json(String json) {
        if (LogConfig.getPrinter() != null) {
            LogConfig.getPrinter().json(json);
        }
    }

    public void xml(String xml) {
        if (LogConfig.getPrinter() != null) {
            LogConfig.getPrinter().xml(xml);
        }
    }

    public void v(String tag, final String format, final Object... obj) {
        if (LogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            LogConfig.getPrinter().v(tag, log);
        }
    }

    public void d(String tag, final String format, final Object... obj) {
        if (LogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            LogConfig.getPrinter().d(tag, log);
        }
    }

    public void i(String tag, final String format, final Object... obj) {
        if (LogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            LogConfig.getPrinter().i(tag, log);
        }
    }

    public void w(String tag, final String format, final Object... obj) {
        if (LogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            LogConfig.getPrinter().w(tag, log);
        }
    }

    public void e(String tag, final String format, final Object... obj) {
        if (LogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            LogConfig.getPrinter().e(tag, log);
        }
    }

    public void w(String tag, Throwable tr, String format, Object... obj) {
        if (LogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            LogConfig.getPrinter().w(tag, log, tr);
        }
    }

    public void e(String tag, Throwable tr, String format, Object... obj) {
        if (LogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            LogConfig.getPrinter().e(tag, log, tr);
        }
    }
}

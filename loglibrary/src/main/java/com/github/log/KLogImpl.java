package com.github.log;

import android.app.Application;

import com.github.log.printer.InitLogListener;


/**
 * Created by lvming on 12/14/20 5:29 PM.
 * Email: lvming@guazi.com
 * Description:
 */
public class KLogImpl {

    private static KLogImpl sKLogImpl = new KLogImpl();


    public static KLogImpl getsKLogImpl() {
        return sKLogImpl;
    }

    public static void setsKLogImpl(KLogImpl sKLogImpl) {
        KLogImpl.sKLogImpl = sKLogImpl;
    }

    public void init(Application application, InitLogListener initLogListener) {
        KLogConfig.getInstance().beforeInit(application, initLogListener);
        KLogConfig.getInstance().init(application, initLogListener);
        KLogConfig.getInstance().afterInit(application, initLogListener);
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
        if (KLogConfig.getPrinter() != null) {
            KLogConfig.getPrinter().json(json);
        }
    }

    public void xml(String xml) {
        if (KLogConfig.getPrinter() != null) {
            KLogConfig.getPrinter().xml(xml);
        }
    }

    public void v(String tag, final String format, final Object... obj) {
        if (KLogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            KLogConfig.getPrinter().v(tag, log);
        }
    }

    public void d(String tag, final String format, final Object... obj) {
        if (KLogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            KLogConfig.getPrinter().d(tag, log);
        }
    }

    public void i(String tag, final String format, final Object... obj) {
        if (KLogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            KLogConfig.getPrinter().i(tag, log);
        }
    }

    public void w(String tag, final String format, final Object... obj) {
        if (KLogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            KLogConfig.getPrinter().w(tag, log);
        }
    }

    public void e(String tag, final String format, final Object... obj) {
        if (KLogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            KLogConfig.getPrinter().e(tag, log);
        }
    }

    public void w(String tag, Throwable tr, String format, Object... obj) {
        if (KLogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            KLogConfig.getPrinter().w(tag, log, tr);
        }
    }

    public void e(String tag, Throwable tr, String format, Object... obj) {
        if (KLogConfig.getPrinter() != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            KLogConfig.getPrinter().e(tag, log, tr);
        }
    }
}

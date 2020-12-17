package com.github.sample.impl;

import android.app.Application;
import android.content.Context;

import com.github.log.printer.BasePrinter;

import java.io.File;

/**
 * Created by lvming on 12/9/20 2:56 PM.
 * Email: lvming@guazi.com
 * Description:log4a框架
 * https://github.com/pqpo/Log4a
 */
public class Log4aPrinter extends BasePrinter {
    public static final int BUFFER_SIZE = 1024 * 400; //400k

    @Override
    public void init(Application application) {
        initLog4a(application);
    }

    @Override
    public void v(String tag, String msg) {
//        Log4a.v(tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
//        Log4a.d(tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
//        Log4a.i(tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
//        Log4a.w(tag, msg);
    }

    @Override
    public void e(String tag, String msg) {
//        Log4a.e(tag, msg);
    }

    @Override
    public void w(String tag, String msg, Throwable tr) {
//        Log4a.w(tag, msg, tr);
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
//        Log4a.e(tag, msg, tr);
    }

    public static void initLog4a(Context context) {
//        int level = Level.DEBUG;
//        Interceptor wrapInterceptor = new Interceptor() {
//            @Override
//            public boolean intercept(LogData logData) {
//                return true;
//            }
//        };
//        AndroidAppender androidAppender = new AndroidAppender.Builder()
//                .setLevel(level)
//                .addInterceptor(wrapInterceptor)
//                .create();
//
//        File log = getLogDir(context);
//        String buffer_path = log.getAbsolutePath() + File.separator + ".logCache";
//        String time = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(new Date());
//        String log_path = log.getAbsolutePath() + File.separator + time + ".txt";
//        FileAppender fileAppender = new FileAppender.Builder(context)
//                .setLogFilePath(log_path)
//                .setLevel(level)
//                .addInterceptor(wrapInterceptor)
//                .setBufferFilePath(buffer_path)
//                .setFormatter(new DateFileFormatter())
//                .setCompress(false)
//                .setBufferSize(BUFFER_SIZE)
//                .create();
//
//        AppenderLogger logger = new AppenderLogger.Builder()
//                .addAppender(androidAppender)
//                .addAppender(fileAppender)
//                .create();
//        Log4a.setLogger(logger);
    }

    public static File getLogDir(Context context) {
        File log = context.getExternalFilesDir("logs");
        if (log == null) {
            log = new File(context.getFilesDir(), "logs");
        }
        if (!log.exists()) {
            log.mkdir();
        }
        return log;
    }
}

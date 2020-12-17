package com.github.sample.impl;

import android.app.Application;
import android.os.Environment;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.ClassicFlattener;
import com.elvishew.xlog.interceptor.BlacklistTagsFilterInterceptor;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.github.log.KLog;
import com.github.log.printer.BasePrinter;

import java.io.File;

/**
 * Created by lvming on 12/9/20 6:32 PM.
 * Email: lvming@guazi.com
 * Description:XLog框架
 * https://github.com/elvishew/xLog
 */
public class EXLogPrinter extends BasePrinter {
    @Override
    public void init(Application application) {
        initXlog();
    }

    @Override
    public void v(String tag, String msg) {
        XLog.tag(tag).enableThreadInfo().v(msg);
    }

    @Override
    public void d(String tag, String msg) {
        XLog.tag(tag).enableThreadInfo().d(msg);
    }

    @Override
    public void i(String tag, String msg) {
        XLog.tag(tag).enableThreadInfo().i(msg);
    }

    @Override
    public void w(String tag, String msg) {
        XLog.tag(tag).enableThreadInfo().w(msg);
    }

    @Override
    public void w(String tag, String msg, Throwable tr) {
        XLog.tag(tag).enableThreadInfo().w(msg, tr);
    }

    @Override
    public void e(String tag, String msg) {
        XLog.tag(tag).enableThreadInfo().e(msg);
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
        XLog.tag(tag).enableThreadInfo().e(msg, tr);
    }

    @Override
    public void json(String json) {
        XLog.json(json);
    }

    @Override
    public void xml(String xml) {
        XLog.xml(xml);
    }

    /**
     * Initialize XLog.
     */
    private void initXlog() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(KLog.getKLogConfig().isDebuggable() ? LogLevel.ALL             // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                        : LogLevel.NONE)
                .tag("Xlog")                   // Specify TAG, default: "X-LOG"
                 .enableThreadInfo()                                 // Enable thread info, disabled by default
                 .enableStackTrace(2)                                // Enable stack trace info with depth 2, disabled by default
//                 .enableBorder()                                     // Enable border, disabled by default
                // .jsonFormatter(new MyJsonFormatter())               // Default: DefaultJsonFormatter
                // .xmlFormatter(new MyXmlFormatter())                 // Default: DefaultXmlFormatter
                // .throwableFormatter(new MyThrowableFormatter())     // Default: DefaultThrowableFormatter
                // .threadFormatter(new MyThreadFormatter())           // Default: DefaultThreadFormatter
                // .stackTraceFormatter(new MyStackTraceFormatter())   // Default: DefaultStackTraceFormatter
                // .borderFormatter(new MyBoardFormatter())            // Default: DefaultBorderFormatter
                // .addObjectFormatter(AnyClass.class,                 // Add formatter for specific class of object
                //     new AnyClassObjectFormatter())                  // Use Object.toString() by default
                .addInterceptor(new BlacklistTagsFilterInterceptor(    // Add blacklist tags filter
                        "blacklist1", "blacklist2", "blacklist3"))
                // .addInterceptor(new WhitelistTagsFilterInterceptor( // Add whitelist tags filter
                //     "whitelist1", "whitelist2", "whitelist3"))
                // .addInterceptor(new MyInterceptor())                // Add a log interceptor
                .build();

        Printer androidPrinter = new AndroidPrinter();             // Printer that print the log using android.util.Log
        Printer filePrinter = new FilePrinter                      // Printer that print the log to the file system
                .Builder(new File(Environment.getExternalStorageDirectory(), "xlogsample").getPath())       // Specify the path to save log file
                .fileNameGenerator(new DateFileNameGenerator())        // Default: ChangelessFileNameGenerator("log")
                // .backupStrategy(new MyBackupStrategy())             // Default: FileSizeBackupStrategy(1024 * 1024)
                // .cleanStrategy(new FileLastModifiedCleanStrategy(MAX_TIME))     // Default: NeverCleanStrategy()
                .flattener(new ClassicFlattener())                     // Default: DefaultFlattener
                .build();

        XLog.init(                                                 // Initialize XLog
                config,                                                // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
                androidPrinter,                                        // Specify printers, if no printer is specified, AndroidPrinter(for Android)/ConsolePrinter(for java) will be used.
                filePrinter);

        // For future usage: partial usage in MainActivity.
//        globalFilePrinter = filePrinter;
    }

}

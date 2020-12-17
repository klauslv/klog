package com.github.sample.impl;

import android.app.Application;
import android.os.Environment;

import com.apkfuns.log2file.LogFileEngineFactory;
import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;
import com.github.log.printer.BasePrinter;

/**
 * Created by lvming on 12/9/20 2:39 PM.
 * Email: lvming@guazi.com
 * Description: LogUtils框架
 * https://github.com/pengwei1024/LogUtils
 */
public class LogUtilsPrinter extends BasePrinter {
    @Override
    public void init(Application application) {
        LogUtils.getLogConfig()
                .configAllowLog(true) //s
                .configFormatTag("LogutilsDemo") //配置统一的TAG前缀
                .configShowBorders(true) //是否显示边框
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}") // 首行显示信息(可配置日期，线程等等)
                .configLevel(LogLevel.TYPE_VERBOSE);//配置可显示日志等级

        // 支持输入日志到文件
        String filePath = Environment.getExternalStorageDirectory() + "/LogUtils/logs/";
        LogUtils.getLog2FileConfig()
                .configLog2FileEnable(true)  // 是否输出日志到文件
                .configLogFileEngine(new LogFileEngineFactory(application)) // 日志文件引擎实现
                .configLog2FilePath(filePath)  // 日志路径
                .configLog2FileNameFormat("app-%d{yyyyMMdd}.txt") // 日志文件名称
                .configLog2FileLevel(LogLevel.TYPE_VERBOSE) ;// 文件日志等级
//                .configLogFileFilter(new LogFileFilter() {  // 文件日志过滤
//                    @Override
//                    public boolean accept(int level, String tag, String logContent) {
//                        return true;
//                    }
//                });

    }

    @Override
    public void v(String tag, String msg) {
        LogUtils.tag(tag).v(msg);
    }

    @Override
    public void d(String tag, String msg) {
        LogUtils.tag(tag).d(msg);
    }

    @Override
    public void i(String tag, String msg) {
        LogUtils.tag(tag).i(msg);
    }

    @Override
    public void w(String tag, String msg) {
        LogUtils.tag(tag).w(msg);
    }

    @Override
    public void e(String tag, String msg) {
        LogUtils.tag(tag).e(msg);
    }

    @Override
    public void json(String json) {
        LogUtils.json(json);
    }

    @Override
    public void xml(String xml) {
        LogUtils.xml(xml);
    }
}

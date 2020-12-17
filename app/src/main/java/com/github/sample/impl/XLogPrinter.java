package com.github.sample.impl;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.github.log.KLog;
import com.github.log.printer.BasePrinter;
import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

import java.util.List;

/**
 * Created by lvming on 12/7/20 3:23 PM.
 * Email: lvming@guazi.com
 * Description: 微信XLog框架
 */
public class XLogPrinter extends BasePrinter {

    @Override
    public void init(Application application) {
        initXLog(application, KLog.getKLogConfig().isDebuggable());
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
    public void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    @Override
    public void w(String tag, String msg, Throwable tr) {
        Log.printErrStackTrace(tag,tr,msg,(Object[]) null);
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
        Log.printErrStackTrace(tag,tr,msg,(Object[]) null);
    }

    public static void initXLog(Context context, boolean isDebug) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
            if (runningAppProcesses != null && !runningAppProcesses.isEmpty()) {
                for (ActivityManager.RunningAppProcessInfo appProcess : runningAppProcesses) {
                    if (appProcess.pid == pid) {
                        processName = appProcess.processName;
                        break;
                    }
                }
            }
        }
        // this is necessary, or may cash for SIGBUS
        String logFileName;
        if (TextUtils.isEmpty(processName) || !processName.contains(":")) {
            logFileName = "Sample";
        } else {
            logFileName = "Sample" + processName.substring(processName.indexOf(":") + 1);
        }
        String cachePath = context.getFilesDir() + "/xlog";
        String logPath = "";
//        if (ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            logPath = context.getFilesDir() + "/xlogLive";
//        } else {
            // get process name to generate log file name
            logPath = getLogPath(isDebug, context);
//        }
        Xlog.open(true, Xlog.LEVEL_VERBOSE, Xlog.AppednerModeAsync, cachePath, logPath, logFileName, "");
        Xlog.setConsoleLogOpen(isDebug);
        Log.setLogImp(new Xlog());
    }

    public static String getLogPath(boolean isDebug, Context context) {
        String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
        String logPath;
        String tempPath;
        if (isDebug) {
            tempPath = SDCARD + "/" + "Sample" + "-debug";
        } else {
            tempPath = SDCARD + "/" + "Sample";
        }
        logPath = tempPath + "/log" + context.getPackageName();
        return logPath;
    }

    /**
     * 关闭XLOG close会及时写到文件中，但是日志会关闭，再打印日志需要重新初始化
     */
    public static void closeXlog() {
        putPhoneInfoToLog();
        Log.appenderClose();
    }

    /**
     * 重新初始化XLog
     */
    public static void reopenXlog() {
        initXLog(KLog.getKLogConfig().getContext(), KLog.getKLogConfig().isDebuggable());
    }

    /**
     * 刷新日志，同步刷新，日志会及时的保存到系统缓存，不一定实时保存到文件当中
     * 异步刷新有极短的延迟
     */
    public static void flushXlog(boolean isSync) {
        Log.appenderFlush(isSync);
    }

    /**
     * 获取下手机信息
     */
    private static void putPhoneInfoToLog() {
        Log.i("PhoneInfo", "osversion[" + Build.VERSION.RELEASE + "]");
        Log.i("PhoneInfo", "mobilemodel[" + Build.MANUFACTURER + "," + Build.MODEL + "]");
//        Log.i("PhoneInfo", "apkversion[" + BuildConfig.VERSION_NAME + "]");
    }

}

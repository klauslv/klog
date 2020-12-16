package com.github.log;

import android.app.Application;
import android.content.Context;

import com.github.log.printer.IPrinter;
import com.github.log.printer.DefaultPrinter;
import com.github.log.printer.InitLogListener;
import com.github.log.upload.IUpload;
import com.github.log.upload.IUploadCallback;

/**
 * Created by lvming on 12/15/20 4:18 PM.
 * Email: lvming@guazi.com
 * Description: 日志管理类
 */
public final class KLogConfig {
    private final String SYS_INFO;

    private static IPrinter printer = new DefaultPrinter();
    private boolean isDebuggable = false;

    private Context mContext;
    private InitLogListener mInitLogListener;

    private IUpload mIUpload;
    private IUploadCallback mIUploadCallback;

    private KLogConfig() {
        final StringBuilder sb = new StringBuilder();
        try {
            sb.append("VERSION.RELEASE:[" + android.os.Build.VERSION.RELEASE);
            sb.append("] VERSION.CODENAME:[" + android.os.Build.VERSION.CODENAME);
            sb.append("] VERSION.INCREMENTAL:[" + android.os.Build.VERSION.INCREMENTAL);
            sb.append("] BOARD:[" + android.os.Build.BOARD);
            sb.append("] DEVICE:[" + android.os.Build.DEVICE);
            sb.append("] DISPLAY:[" + android.os.Build.DISPLAY);
            sb.append("] FINGERPRINT:[" + android.os.Build.FINGERPRINT);
            sb.append("] HOST:[" + android.os.Build.HOST);
            sb.append("] MANUFACTURER:[" + android.os.Build.MANUFACTURER);
            sb.append("] MODEL:[" + android.os.Build.MODEL);
            sb.append("] PRODUCT:[" + android.os.Build.PRODUCT);
            sb.append("] TAGS:[" + android.os.Build.TAGS);
            sb.append("] TYPE:[" + android.os.Build.TYPE);
            sb.append("] USER:[" + android.os.Build.USER + "]");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        SYS_INFO = sb.toString();
    }

    public static class LogManagerHolder {
        public static KLogConfig sInstance = new KLogConfig();
    }

    public static KLogConfig getInstance() {
        return LogManagerHolder.sInstance;
    }

    public String getSysInfo() {
        return SYS_INFO;
    }

    /**
     * 初始化之前做一些参数的保存
     *
     * @param application
     */
    void beforeInit(Application application, InitLogListener initLogListener) {
        mContext = application;
        mInitLogListener = initLogListener;
        if (initLogListener != null) {
            initLogListener.beforeInitLog(application);
        }
    }

    /**
     * 初始化
     *
     * @param application
     * @param initLogListener
     */
    void init(Application application, InitLogListener initLogListener) {
        if (initLogListener != null) {
            initLogListener.init(application);
        }
        printer.init(application);
    }

    /**
     * 初始化之后，做一些特殊的额调用
     */
    void afterInit(Application application, InitLogListener initLogListener) {
        if (initLogListener != null) {
            initLogListener.afterInitLog(application);
        }
    }

    public KLogConfig setPrinter(IPrinter printer) {
        this.printer = printer;
        return LogManagerHolder.sInstance;
    }

    public static IPrinter getPrinter() {
        return printer;
    }

    public KLogConfig setIsDebuggable(boolean debuggable) {
        isDebuggable = debuggable;
        return LogManagerHolder.sInstance;
    }

    public boolean isDebuggable() {
        return isDebuggable;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public IUpload getIUpload() {
        return mIUpload;
    }

    public KLogConfig setIUpload(IUpload IUpload) {
        mIUpload = IUpload;
        return LogManagerHolder.sInstance;
    }

    public IUploadCallback getIUploadCallback() {
        return mIUploadCallback;
    }

    public KLogConfig setIUploadCallback(IUploadCallback IUploadCallback) {
        mIUploadCallback = IUploadCallback;
        return LogManagerHolder.sInstance;
    }

    public void uploadLog(){
        if (mIUpload != null){
            mIUpload.uploadLog();
            return;
        }
        if (mIUploadCallback != null){
//            UploadLogUtils.getInstance().uploadLogFile(mIUploadCallback);
        }
    }
}

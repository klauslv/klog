package com.github.sample.impl;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.dianping.logan.Logan;
import com.dianping.logan.LoganConfig;
import com.dianping.logan.OnLoganProtocolStatus;
import com.dianping.logan.SendLogCallback;
import com.github.log.KLog;
import com.github.log.printer.BasePrinter;
import com.github.sample.RealSendLogRunnable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * Created by lvming on 12/8/20 5:55 PM.
 * Email: lvming@guazi.com
 * Description: 美团logan日志框架
 * https://github.com/Meituan-Dianping/Logan
 */
public class LoganPrinter extends BasePrinter {

    private static RealSendLogRunnable realSendLogRunnable;

    @Override
    public void init(Application application) {
        LoganConfig config = new LoganConfig.Builder()
                .setCachePath(application.getFilesDir().getAbsolutePath())
                .setPath(application.getExternalFilesDir(null).getAbsolutePath()
                        + File.separator + "logan_v1")
                .setEncryptKey16("".getBytes())
                .setEncryptIV16("".getBytes())
                .build();
        Logan.init(config);
        Logan.setDebug(KLog.getKLogConfig().isDebuggable());
        Logan.setOnLoganProtocolStatus(new OnLoganProtocolStatus() {
            @Override
            public void loganProtocolStatus(String cmd, int code) {
                Log.d("LoganPrinter", "clogan > cmd : " + cmd + " | " + "code : " + code);
            }
        });
        realSendLogRunnable = new RealSendLogRunnable();
    }

    @Override
    public void v(@NonNull String tag, String msg) {
        Logan.w(tag + ":" + msg, 1);
    }

    @Override
    public void d(@NonNull String tag, String msg) {
        Logan.w(tag + ":" + msg, 1);
    }

    @Override
    public void i(@NonNull String tag, String msg) {
        Logan.w(tag + ":" + msg, 1);
    }

    @Override
    public void w(@NonNull String tag, String msg) {
        Logan.w(tag + ":" + msg, 1);
    }

    @Override
    public void e(@NonNull String tag, String msg) {
        Logan.w(tag + ":" + msg, 1);
    }

    private void loganSend() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String d = dateFormat.format(new Date(System.currentTimeMillis()));
        String[] temp = new String[1];
        temp[0] = d;
        Logan.s(temp, realSendLogRunnable);
    }

    private void loganFilesInfo() {
        Map<String, Long> map = Logan.getAllFilesInfo();
        if (map != null) {
            StringBuilder info = new StringBuilder();
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                info.append("文件日期：").append(entry.getKey()).append("  文件大小（bytes）：").append(
                        entry.getValue()).append("\n");
            }
//            mTvInfo.setText(info.toString());
        }
    }

    private void loganSendByDefault() {
        String buildVersion = "";
        String appVersion = "";
        try {
            PackageInfo pInfo = KLog.getKLogConfig().getContext().getPackageManager()
                    .getPackageInfo(KLog.getKLogConfig().getContext().getPackageName(), 0);
            appVersion = pInfo.versionName;
            buildVersion = String.valueOf(pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final String url = "https://openlogan.inf.test.sankuai.com/logan/upload.json";
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String date = dataFormat.format(new Date(System.currentTimeMillis()));
        Logan.s(url, date, "1", "logan-test-unionid", "deviceId", buildVersion, appVersion, new SendLogCallback() {
            @Override
            public void onLogSendCompleted(int statusCode, byte[] data) {
                final String resultData = data != null ? new String(data) : "";
                Log.d("LoganPrinter", "日志上传结果, http状态码: " + statusCode + ", 详细: " + resultData);
            }
        });
    }
}

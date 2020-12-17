package com.github.sample;

import android.app.Application;
import android.os.Bundle;

import com.github.klog.BuildConfig;
import com.github.klog.R;
import com.github.log.KLog;
import com.github.log.KLogLevel;
import com.github.log.printer.InitLogListener;
import com.github.sample.impl.LogUtilsPrinter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static String name = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KLog.getKLogConfig().setPrinter(new LogUtilsPrinter())
                .setIsDebuggable(BuildConfig.DEBUG)
                .setLevel(KLogLevel.ALL);
        KLog.init(getApplication(), new InitLogListener() {
            @Override
            public void beforeInitLog(Application application) {
                //初始化log之前调用
            }

            @Override
            public void init(Application application) {
//                初始化时调用
            }

            @Override
            public void afterInitLog(Application application) {
                //初始化时调用
            }
        });

        for (int i = 0; i < 100; i++) {
            KLog.v(TAG, "测试test_" + i);
            KLog.d(TAG, "测试test_" + i);
            KLog.i(TAG, "测试test_" + i);
            KLog.w(TAG, "测试test_" + i);
            KLog.e(TAG, "测试test_" + i);
        }
        try {
            name.getBytes();
        }catch (Throwable throwable){
            KLog.e(TAG,throwable);
        }
    }
}
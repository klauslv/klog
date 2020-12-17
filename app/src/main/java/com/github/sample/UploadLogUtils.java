package com.github.sample;

import com.github.log.KLog;
import com.github.log.upload.IUploadCallback;
import com.github.log.upload.UploadHelper;
import com.github.sample.impl.XLogPrinter;
import com.tencent.mars.xlog.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lvming on 2019/3/5.
 * 上传日志
 */
public class UploadLogUtils {

    private static final String TAG = UploadLogUtils.class.getSimpleName();

    private UploadLogUtils() {
    }

    private static class UploadLogUtilsHolder {
        private static final UploadLogUtils sInstance = new UploadLogUtils();
    }

    public static UploadLogUtils getInstance() {
        return UploadLogUtils.UploadLogUtilsHolder.sInstance;
    }

    /**
     * 上传日志文件
     */
    public void uploadLogFile(IUploadCallback listener) {
        Log.i(TAG, "uploadLogFile");
        //保存一下日志
        XLogPrinter.closeXlog();
        //重新初始化
        XLogPrinter.reopenXlog();

        String logPath = XLogPrinter.getLogPath(KLog.getKLogConfig().isDebuggable(), KLog.getKLogConfig().getContext());
        File logFolder = new File(logPath);
        File[] files = null;
        final Queue<File> fileList = new LinkedBlockingQueue<>();
        if (logFolder.isDirectory()) {
            files = logFolder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
//                    if (filename.startsWith("custom-sdk")) {
                    return true;
//                    } else {
//                        return false;
//                    }
                }
            });
        }

        if (null == files || files.length == 0) {
            Log.i(TAG, "null == files || files.length == 0");
            return;
        }

        for (File item : files) {
            try {
                String str[] = item.getName().split("_");
                String dateStr = str[str.length - 1].substring(0, 8);
                dateStr = dateStr.substring(0, 4) + "-" +
                        dateStr.substring(4, 6) + "-" +
                        dateStr.substring(6, 8) + " 00:00:00";
                long dateTimeStamp = Timestamp.valueOf(dateStr).getTime();
                if (System.currentTimeMillis() - dateTimeStamp <= 7 * 24 * 60 * 60 * 1000) {
                    fileList.add(item);
                }
            } catch (Exception e) {
                Log.printErrStackTrace(TAG, e, "");
                continue;
            }
        }
        uploadLogFiles(fileList, listener);
    }


//    /**
//     * 上传DB文件
//     */
//    public void uploadDBFile() {
//        try {
//            final File dbFile = getDBFile();
//            RemoteDataSourceManager.getInstance().uploadLog(getUploadParams(), toMultiPart(dbFile), new RemoteApiCallback<Object>() {
//                @Override
//                public void onSuccess(Object response) {
//                    Log.i(TAG, "上报数据库成功");
//                }
//
//                @Override
//                public void onFailure(int errorCode, String errorMsg) {
//                    Log.i(TAG, "上报数据库失败regist error:" + errorMsg);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.printErrStackTrace(TAG, e, "");
//        }
//    }

    private void uploadLogFiles(final Queue<File> fileList, final IUploadCallback listener) {
        if (listener != null) {
            listener.onStart();
        }
        OkHttpClient client = new OkHttpClient();
        File file = fileList.poll();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("log", file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), file));
        if (UploadHelper.getInstance().getParamMap() != null) {
            for (Map.Entry<String, String> entry : UploadHelper.getInstance().getParamMap().entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = builder.build();
        Request.Builder request = new Request.Builder().url(UploadHelper.getInstance().getUrl())
                .post(requestBody);
        if (UploadHelper.getInstance().getBaseHeaderMap() != null) {
            for (Map.Entry<String, String> entry : UploadHelper.getInstance().getBaseHeaderMap().entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "上传日志失败:" + e.getMessage());
                if (listener != null) {
                    listener.onUploadFail(-1, "");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    Log.i(TAG, "上报日志成功");
                    if (!fileList.isEmpty()) {
                        uploadLogFiles(fileList, listener);
                    } else {
                        if (listener != null) {
                            listener.onUploadSuccess();
                        }
                    }
                } else {
                    Log.i(TAG, response.message() + " error : body " + response.body().string());
                }
            }
        });

    }

//    /**
//     * 组装上传文件参数
//     *
//     * @return
//     */
//    private HashMap<String, RequestBody> getUploadParams() {
//        HashMap<String, RequestBody> map = new HashMap<>();
//        map.put("uid", RequestBody.create(null, String.valueOf(IMLibManager.getInstance().getUid())));
//        map.put("clientVersion", RequestBody.create(null, String.valueOf(IMLibManager.getInstance().getAppId())));
//        map.put("os", RequestBody.create(null, "android"));
//        map.put("domain", RequestBody.create(null, "123"));
//        map.put("appId", RequestBody.create(null, String.valueOf(IMLibManager.getInstance().getAppId())));
//        map.put("sign", RequestBody.create(null, "guazi_im"));
//        return map;
//    }
//
//    /**
//     * file 转换为multipart 上传
//     *
//     * @param file
//     * @return
//     */
//    private MultipartBody.Part toMultiPart(File file) {
//        return MultipartBody.Part.createFormData("log", file.getName(),
//                RequestBody.create(MediaType.parse("application/octet-stream"), file));
//    }
//
//    private File getDBFile() {
//        String mDbEncryptFileName = "im_customer_" + IMLibManager.getInstance().getUid();
//        return IMLibManager.getInstance().getApplication().getDatabasePath(mDbEncryptFileName);
//
//    }

    /**
     * 解密数据库
     *
     * @param encryptedName 数据库名称
     * @param key           密码
     */
//    private void decrypt(String SDcardPath, String encryptedName, String key) {
//        try {
//            File databaseFile = MainApplicaton.getInstance().getDatabasePath(encryptedName);
//            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, key.getBytes(), null, null);
//
//            File decrypteddatabaseFile = MainApplicaton.getInstance().getDatabasePath(SDcardPath);
//
//            //连接到解密后的数据库，并设置密码为空
//            database.execSQL(String.format("ATTACH DATABASE '%s' as " + encryptedName.split("\\.")[0] + " KEY '';", decrypteddatabaseFile.getAbsolutePath()));
//            database.execSQL("SELECT sqlcipher_export('" + encryptedName.split("\\.")[0] + "');");
//            database.execSQL("DETACH DATABASE " + encryptedName.split("\\.")[0] + ";");
//
//            SQLiteDatabase decrypteddatabase = SQLiteDatabase.openOrCreateDatabase(decrypteddatabaseFile, "".getBytes(), null, null);
//            decrypteddatabase.setVersion(database.getVersion());
//            decrypteddatabase.close();
//
//            database.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.printErrStackTrace(TAG, e, "");
//        }
//    }
}

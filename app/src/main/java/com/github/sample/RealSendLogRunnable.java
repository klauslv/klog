package com.github.sample;

import android.text.TextUtils;

import com.dianping.logan.SendLogRunnable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * Created by lvming on 12/8/20 6:19 PM.
 * Email: lvming@guazi.com
 * Description:
 */
public class RealSendLogRunnable extends SendLogRunnable {
    private String mUploadLogUrl = "http://localhost:3000/logupload";

    @Override
    public void sendLog(File logFile) {
        boolean success = doSendFileByAction(logFile);

    }

    /**
     * 主动上报
     *
     * @param logFile
     * @return
     */
    private boolean doSendFileByAction(File logFile) {
        boolean isSuccess = false;
        try {
            FileInputStream fileInputStream = new FileInputStream(logFile);
            byte[] backData = doPostRequest(mUploadLogUrl, fileInputStream, getActionHeader());
            isSuccess = handleSendLogBackData(backData);
        } catch (FileNotFoundException | JSONException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean handleSendLogBackData(byte[] backData) throws JSONException {
        boolean isSuccess = false;
        if (backData != null){
            String data = new String(backData);
            if (!TextUtils.isEmpty(data)){
                JSONObject jsonObject = new JSONObject(data);
                if (jsonObject.optBoolean("success",false)){
                    isSuccess = true;
                }
            }
        }
        return isSuccess;
    }

    private byte[] doPostRequest(String uploadLogUrl, InputStream inputData, Map<String, String> headerMap) {
        byte[] data = null;
        byte[] buffer = new byte[2048];
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        ByteArrayOutputStream back;
        try {
            URL url = new URL(uploadLogUrl);
            connection = (HttpURLConnection) url.openConnection();
            if (connection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            }

            Set<Map.Entry<String, String>> entrySet = headerMap.entrySet();
            for (Map.Entry<String, String> tempEntry : entrySet) {
                connection.addRequestProperty(tempEntry.getKey(), tempEntry.getValue());
            }
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            outputStream = connection.getOutputStream();
            int i;
            while ((i=inputData.read(buffer))!= -1){
                outputStream.write(buffer,0,1);
            }
            outputStream.flush();
            int res = connection.getResponseCode();
            if (res == 200){
                back = new ByteArrayOutputStream();
                inputStream = connection.getInputStream();
                while ((i=inputStream.read(buffer))!= -1){
                    back.write(buffer,0,i);
                }
                data = back.toByteArray();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputData != null){
                try {
                    inputData.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                connection.disconnect();
            }
        }
        return data;
    }

    private HashMap<String, String> getActionHeader() {
        HashMap<String, String> map = new HashMap<>();
        map.put("Content-Type", "binary/octet-stream");
        map.put("client", "android");
        return map;
    }
}

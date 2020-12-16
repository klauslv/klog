package com.github.log.upload;

/**
 * Created by lvming on 12/9/20 7:03 PM.
 * Email: lvming@guazi.com
 * Description:
 */
public interface IUploadCallback {

    void onStart();

    void onUploadSuccess();

    void onUploadFail(int errorCode, String errorMsg);
}

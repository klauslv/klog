package com.github.log.upload;

import java.util.Map;

/**
 * Created by lvming on 12/10/20 3:24 PM.
 * Email: lvming@guazi.com
 * Description:
 */
public final class UploadHelper {
    private String url;
    private Map<String, String> mParamMap;
    private Map<String, String> mBaseHeaderMap;


    private UploadHelper() {
    }

    private static class UploadHelperHolder {
        private static final UploadHelper sInstance = new UploadHelper();
    }

    public static UploadHelper getInstance() {
        return UploadHelper.UploadHelperHolder.sInstance;
    }

    public String getUrl() {
        return url;
    }

    public UploadHelper setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, String> getParamMap() {
        return mParamMap;
    }

    public UploadHelper setParamMap(Map<String, String> paramMap) {
        this.mParamMap = paramMap;
        return this;
    }

    public Map<String, String> getBaseHeaderMap() {
        return mBaseHeaderMap;
    }

    public UploadHelper setBaseHeaderMap(Map<String, String> baseHeaderMap) {
        mBaseHeaderMap = baseHeaderMap;
        return this;
    }
}

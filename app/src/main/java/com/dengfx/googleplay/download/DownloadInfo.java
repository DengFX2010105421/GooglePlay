package com.dengfx.googleplay.download;

import android.support.annotation.NonNull;

import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.utils.FileUtils;
import com.dengfx.googleplay.utils.HttpUtils;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 邓FX on 2016/10/22.
 */

public class DownloadInfo implements Serializable {

    public String packageName;
    public long size;
    public int currentState = DownloadManager.STATE_UNDOWNLOAD;
    String downloadUrl;
    DownloadManager.DownloadTask downloadTask;

    public long getRange() {
        return getApkSavedFile().exists() ? getApkSavedFile().length() : 0;
    }

    public File getApkSavedFile() {
        return new File(FileUtils.getExternalStoragePath(), packageName + ".apk");
    }

    @NonNull
    String getFullDownloadUrl() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", downloadUrl);
        params.put("range", getRange());
        return Constants.URLS.BASEURL + "download?" + HttpUtils.getUrlParamsByMap(params);
    }
}

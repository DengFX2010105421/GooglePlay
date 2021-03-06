package com.dengfx.googleplay.base;

import com.dengfx.googleplay.MyApplication;
import com.dengfx.googleplay.config.Constants;
import com.dengfx.googleplay.utils.FileUtils;
import com.dengfx.googleplay.utils.LogUtils;
import com.dengfx.googleplay.utils.MD5utils;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 邓FX on 2016/10/15.
 */

public class BaseProtocol<T> {

    protected Gson mGson;
    private OkHttpClient mOkHttpClient;

    public BaseProtocol() {
        mGson = new Gson();
        mOkHttpClient = getOkHttpClient();
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) synchronized (BaseProtocol.class) {
            if (mOkHttpClient == null)
                mOkHttpClient = new OkHttpClient();
        }
        return mOkHttpClient;
    }

    public T loadData(String url) throws IOException, JSONException {
        return loadDataFromMemory(url);
    }

    private T loadDataFromMemory(String url) throws IOException, JSONException {
        Map<String, String> cachedJsonMap = MyApplication.getCachedJsonMap();
        String key = MD5utils.md5EncodeString(url);
        if (cachedJsonMap.containsKey(key)) {
            String json = cachedJsonMap.get(key);
            LogUtils.s("读取内存成功");
            return parseJson2Obj(json);
        } else {
            LogUtils.s("读取内存失败");
            return loadDataFromLocal(url);
        }
    }

    private T loadDataFromLocal(String url) throws IOException, JSONException {
        File cachedFile = getCachedFile(url);
        if (cachedFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(cachedFile))) {
                long lastCachedTime = Long.parseLong(reader.readLine());
                if (System.currentTimeMillis() - lastCachedTime < Constants.PROTOCOL_TIMEOUT) {
                    String lastCachedJson = reader.readLine();
                    LogUtils.s("读取本地缓存成功" + cachedFile.getAbsolutePath());
                    LogUtils.s(backupData2Memory(url, lastCachedJson) ? "内存缓存成功" : "内存缓存失败");
                    return parseJson2Obj(lastCachedJson);
                }
            }
            LogUtils.s("读取本地缓存失败");
        }
        return loadDataFromNet(url);
    }

    private T loadDataFromNet(String url) throws IOException, JSONException {
//        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient okHttpClient = getOkHttpClient();
        Request request = new Request.Builder().get().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String newJson = response.body().string();
            LogUtils.e("Net success  ==========  " + newJson);
            LogUtils.s(backupData2Memory(url, newJson) ? "内存缓存成功" : "内存缓存失败");
            LogUtils.s(backupData2Local(url, newJson) ? "本地缓存成功" : "本地缓存失败");
            return parseJson2Obj(newJson);
        }
        return null;
    }

    private boolean backupData2Memory(String url, String json) {
        Map<String, String> cachedJsonMap = MyApplication.getCachedJsonMap();
        cachedJsonMap.put(MD5utils.md5EncodeString(url), json);
        return true;
    }

    private boolean backupData2Local(String url, String json) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getCachedFile(url)))) {
            writer.write(System.currentTimeMillis() + "");
            writer.newLine();
            writer.write(json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private File getCachedFile(String url) {
        return new File(FileUtils.getDir("json"), MD5utils.md5EncodeString(url) + ".json");
    }

    public T parseJson2Obj(String json) {
//        Class clazz = this.getClass();
//        Type type = clazz.getGenericSuperclass();
//        ParameterizedType pType = (ParameterizedType) type;
//        Type[] actualTypeArguments = pType.getActualTypeArguments();
//        Class cla = (Class) actualTypeArguments[0];
//        Type type1 = getClass().getGenericSuperclass();
//        ParameterizedType genericSuperclass = (ParameterizedType) type1;
//        Type type = genericSuperclass.getActualTypeArguments()[0];
//        return mGson.fromJson(json, type);

        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType pType = (ParameterizedType) genericSuperclass;
        Type type = pType.getActualTypeArguments()[0];
        return mGson.fromJson(json, type);
    }
}

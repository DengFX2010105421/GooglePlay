package com.dengfx.googleplay.protocol;

import com.dengfx.googleplay.bean.HomeBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 邓FX on 2016/10/15.
 */

public class HomeProtocol_v1 {

    private OkHttpClient mOkHttpClient;

    public HomeProtocol_v1() {
        if (mOkHttpClient == null) synchronized (HomeProtocol_v1.class){
            if (mOkHttpClient == null)
            mOkHttpClient = new OkHttpClient();
        }
    }

    public HomeBean loadDataFromNet(String url) throws IOException {
        Request request = new Request.Builder().get().url(url).build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.isSuccessful() ?
                new Gson().fromJson(response.body().string(), HomeBean.class) : null;
    }
}

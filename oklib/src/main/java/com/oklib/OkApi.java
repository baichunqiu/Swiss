package com.oklib;

import com.oklib.body.BitmapBody;
import com.oklib.body.FileBody;
import com.oklib.callback.CallBack;
import com.oklib.callback.FileCallBack;
import com.oklib.core.Core;

import java.util.Map;

import okhttp3.OkHttpClient;

public class OkApi {

    /**
     * 设置配置信息
     * @param builder
     */
    public static void config(OkHttpClient.Builder builder){
        Core.core().builder(builder);
    }
    public static void download(String url, Map<String, Object> params, FileCallBack fileCallBack) {
        Core.core().get(url, params, fileCallBack);
    }

    public static <T> void bitmap(String url, String key, BitmapBody bitmap, CallBack<T> callBack) {
        Core.core().post(url, key, bitmap, callBack);
    }

    public static <T> void file(String url, String key, FileBody fileBody, CallBack<T> callBack) {
        Core.core().post(url, key, fileBody, callBack);
    }


    public static <T> void get(String url, Map<String, Object> params, CallBack<T> callBack) {
        Core.core().get(url, params, callBack);
    }

    public static <T> void post(String url, Map<String, Object> params, CallBack<T> callBack) {
        Core.core().post(url, params, callBack);
    }


}

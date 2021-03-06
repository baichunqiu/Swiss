package com.bcq.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.bcq.net.callback.GeneralListCallback;
import com.bcq.net.callback.GeneralStateCallback;
import com.bcq.net.callback.base.IListCallback;
import com.business.GeneralCallBack;
import com.business.IBusiCallback;
import com.business.ILoadTag;
import com.business.parse.Parser;
import com.kit.Logger;
import com.kit.UIKit;
import com.oklib.core.Method;
import com.oklib.core.ReQuest;

import java.util.Map;


/**
 * @author: BaiCQ
 * @ClassName: NetApi
 * @Description: 网络请求工具类
 * request:
 * 请求获取数据相关api 返回数据集
 * operate：
 * 提交操作相关api 返回状态
 */
public class NetApi {
    public final static String TAG = "NetApi";

    /**
     * @param tag           load视图
     * @param url           地址
     * @param params        参数
     * @param method        Method get/post
     * @param iListCallback 数据集回调
     * @return 请求封装体
     */
    public static <R> ReQuest request(ILoadTag tag, String url, Map<String, Object> params, Method method, IListCallback<R> iListCallback) {
        return request(url, params, method, new GeneralListCallback(tag, null, iListCallback));
    }

    /**
     * @param tag           load视图
     * @param url           地址
     * @param params        参数
     * @param parser        自定义解析器
     * @param method        Method get/post
     * @param iListCallback 数据集回调
     * @return 请求封装体
     */
    public static <R> ReQuest request(ILoadTag tag, String url, Map<String, Object> params, Parser parser, Method method, IListCallback<R> iListCallback) {
        return request(url, params, method, new GeneralListCallback(tag, parser, iListCallback));
    }

    /**
     * @param tag       load视图
     * @param url       地址
     * @param params    参数
     * @param method    Method get/post
     * @param iCallback 状态回调
     * @return 请求封装体
     */
    public static ReQuest operate(ILoadTag tag, String url, Map<String, Object> params, Method method, IBusiCallback<Integer, String> iCallback) {
        return request(url, params, method, new GeneralStateCallback(tag, null, iCallback));
    }

    /**
     * @param tag       load视图
     * @param url       地址
     * @param params    参数
     * @param parser    自定义解析器
     * @param method    Method get/post
     * @param iCallback 状态回调
     * @return 请求封装体
     */
    public static ReQuest operate(ILoadTag tag, String url, Map<String, Object> params, Parser parser, Method method, IBusiCallback<Integer, String> iCallback) {
        return request(url, params, method, new GeneralStateCallback(tag, parser, iCallback));
    }

    /**
     * 请求
     *
     * @param url             地址
     * @param params          参数
     * @param generalCallBack 回调
     * @param method          请求方式
     * @param <R>             result类型（主数据）
     * @param <E>             extra类型 （附加数据）
     * @return ReQuest 请求封装体
     */
    public static <R, E> ReQuest request(String url, Map<String, Object> params, Method method, GeneralCallBack<R, E> generalCallBack) {
        ReQuest.Builder builder = method == Method.post ? ReQuest.Builder.post() : ReQuest.Builder.get();
        ReQuest reQuest = builder.url(url)
                .param(params)
                .callback(generalCallBack)
                .build();
        if (checkOK(url)) reQuest.request();
        return reQuest;
    }


    /**
     * 检查网 并根据tag的类型 取消加载动画
     *
     * @return
     */
    public static <T> boolean checkOK(String url) {
        if (TextUtils.isEmpty(url)) {
            Logger.e(TAG, "you net request url is null ，please check it！");
            return false;
        }
        return isNetworkConnected();
    }

    /**
     * 是否连接上网络
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) UIKit.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return true;//wifi
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return true;//mobile
            }
        }
        return false;
    }
}

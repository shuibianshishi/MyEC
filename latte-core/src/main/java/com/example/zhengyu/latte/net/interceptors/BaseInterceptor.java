package com.example.zhengyu.latte.net.interceptors;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZHENGYU on 2017/10/25.
 * 基础拦截器
 */

public abstract class  BaseInterceptor implements Interceptor {

    /*
        对get请求直接通过url 获取请求参数
     */
    protected LinkedHashMap<String,String> getUrlParameters(Chain chain){
        final LinkedHashMap<String,String> UrlParams = new LinkedHashMap<>();
        HttpUrl httpUrl = chain.request().url();

        for(int i=0;i<httpUrl.querySize();i++){
            UrlParams.put(httpUrl.queryParameterName(i),httpUrl.queryParameterValue(i));
        }
        return UrlParams;
    }

    protected String getUrlParameters(Chain chain, String key) {
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    protected  LinkedHashMap<String,String> getBodyParameters(Chain chain){
        final LinkedHashMap<String,String>BodyParams = new LinkedHashMap<>();
        FormBody formBody = (FormBody) chain.request().body();
        if(formBody != null) {
            for (int i = 0; i < formBody.size(); i++){
                BodyParams.put(formBody.name(i),formBody.value(i));
            }
        }
        return BodyParams;

    }

    protected String getBodyParameters(Chain chain, String key) {
        return getBodyParameters(chain).get(key);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}

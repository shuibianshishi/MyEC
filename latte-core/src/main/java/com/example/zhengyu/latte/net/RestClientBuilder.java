package com.example.zhengyu.latte.net;

import com.example.zhengyu.latte.net.callback.IError;
import com.example.zhengyu.latte.net.callback.IFailure;
import com.example.zhengyu.latte.net.callback.IRequest;
import com.example.zhengyu.latte.net.callback.ISuccess;

import java.util.Objects;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by ZHENGYU on 2017/10/17.
 */

public class RestClientBuilder {

    private  String mUrl;
    private  final WeakHashMap<String, Object> mParams = RestCreator.getParamsHolder();
    private  IError mIError;
    private  IFailure mIFailure;
    private  IRequest mIRequest;
    private ISuccess mISuccess;
    private  RequestBody mBody;

    RestClientBuilder(){

    }

    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Objects>params){
        this.mParams.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder error(IError error){
        this.mIError = error;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure){
        this.mIFailure = failure;
        return this;
    }

    public final RestClientBuilder mIRequest(IRequest request){
        this.mIRequest = request;
        return this;
    }

    public final RestClientBuilder success(ISuccess success){
        this.mISuccess = success;
        return this;
    }

    public RestClient builder(){
        return new RestClient(mUrl,mParams,
                mIError, mIFailure,mIRequest,mISuccess,
                mBody);
    }

}

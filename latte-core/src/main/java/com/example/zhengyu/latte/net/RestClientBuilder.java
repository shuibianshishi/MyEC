package com.example.zhengyu.latte.net;

import android.content.Context;

import com.example.zhengyu.latte.net.callback.IError;
import com.example.zhengyu.latte.net.callback.IFailure;
import com.example.zhengyu.latte.net.callback.IRequest;
import com.example.zhengyu.latte.net.callback.ISuccess;
import com.example.zhengyu.latte.ui.LoaderStyle;

import java.io.File;
import java.util.Objects;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by ZHENGYU on 2017/10/17.
 */

public class RestClientBuilder {

    private String mUrl = null;
    private final WeakHashMap<String, Object> mParams = RestCreator.getParamsHolder();
    private IError mIError = null;
    private IFailure mIFailure = null;
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;


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

    public final RestClientBuilder file(String fileUrl) {
        mFile = new File(fileUrl);
        return this;
    }

    public final RestClientBuilder file(File file) {
        mFile = file;
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
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

    public final RestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final RestClientBuilder loader(Context context,LoaderStyle stype){
        this.mContext = context;
        this.mLoaderStyle = stype;
        return this;
    }

    public RestClient builder(){
        return new RestClient(mUrl,mParams,
                mIError, mIFailure,mIRequest,mISuccess,
                mBody, mContext, mLoaderStyle,mFile,mDownloadDir,mExtension,mName);
    }

}

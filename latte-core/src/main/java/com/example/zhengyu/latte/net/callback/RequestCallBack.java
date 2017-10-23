package com.example.zhengyu.latte.net.callback;

import android.os.Handler;

import com.example.zhengyu.latte.ui.LatteLoader;
import com.example.zhengyu.latte.ui.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by ZHENGYU on 2017/10/20.
 */

public class RequestCallBack implements Callback<String>{
    private final IError IERROR;
    private final IFailure IFAILURE;
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();// 建立一个handler 实现让 请求loading 延迟一秒

    public RequestCallBack(IError ierror, IFailure ifailure, IRequest irequest, ISuccess isuccess, LoaderStyle loaderStyle) {
        IERROR = ierror;
        IFAILURE = ifailure;
        IREQUEST = irequest;
        ISUCCESS = isuccess;
        LOADER_STYLE = loaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful()){
            if(call.isExecuted()){//call 已经执行
                if(ISUCCESS!=null){
                    ISUCCESS.onSuccess(response.body());
                }
            }
        }else{
            if(IERROR !=null){
                IERROR.onError(response.code(),response.message());
            }
        }
        onRequestFinish();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(IFAILURE!=null){
            IFAILURE.onFailure();
        }
        if(IREQUEST!=null){
            IREQUEST.onRequestEnd();
        }

        onRequestFinish();
    }

    private void onRequestFinish(){
        if(LOADER_STYLE != null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopAllLoading();
                }
            }, 1000);
        }
    }

}

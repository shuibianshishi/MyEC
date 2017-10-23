package com.example.zhengyu.latte.net.callback;

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

    public RequestCallBack(IError ierror, IFailure ifailure, IRequest irequest, ISuccess isuccess) {
        IERROR = ierror;
        IFAILURE = ifailure;
        IREQUEST = irequest;
        ISUCCESS = isuccess;
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
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(IFAILURE!=null){
            IFAILURE.onFailure();
        }
        if(IREQUEST!=null){
            IREQUEST.onRequestEnd();
        }
    }

}

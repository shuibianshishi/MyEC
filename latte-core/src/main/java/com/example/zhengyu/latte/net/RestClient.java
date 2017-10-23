package com.example.zhengyu.latte.net;

import com.example.zhengyu.latte.net.callback.IError;
import com.example.zhengyu.latte.net.callback.IFailure;
import com.example.zhengyu.latte.net.callback.IRequest;
import com.example.zhengyu.latte.net.callback.ISuccess;
import com.example.zhengyu.latte.net.callback.RequestCallBack;

import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by ZHENGYU on 2017/10/17.
 */

public class RestClient {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParamsHolder();
    private final IError IERROR;
    private final IFailure IFAILURE;
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final RequestBody BODY;


    public RestClient(String url,
                       WeakHashMap<String, Object> params,
                       IError ierror,
                       IFailure ifailure,
                       IRequest irequest,
                       ISuccess isuccess,
                       RequestBody body) {
        URL = url;
        PARAMS.putAll(params);
        IERROR = ierror;
        IFAILURE = ifailure;
        IREQUEST = irequest;
        ISUCCESS = isuccess;
        BODY = body;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method){
        final RestService service = RestCreator.getRestService();
        Call<String> call  = null;
        if(IREQUEST!=null){
            IREQUEST.onRequestStart();
        }
        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            default:
                break;
        }

        if(call!=null){
            call.enqueue(getRequestCallBack());
        }

    }

    private Callback<String> getRequestCallBack(){
        return new RequestCallBack(
                IERROR,
                IFAILURE,
                IREQUEST,
                ISUCCESS);
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        request(HttpMethod.POST);
    }

    public final void put(){
        request(HttpMethod.PUT);
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }
}

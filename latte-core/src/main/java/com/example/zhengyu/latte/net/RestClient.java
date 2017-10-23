package com.example.zhengyu.latte.net;

import android.content.Context;
import android.util.Log;

import com.example.zhengyu.latte.net.callback.IError;
import com.example.zhengyu.latte.net.callback.IFailure;
import com.example.zhengyu.latte.net.callback.IRequest;
import com.example.zhengyu.latte.net.callback.ISuccess;
import com.example.zhengyu.latte.net.callback.RequestCallBack;
import com.example.zhengyu.latte.ui.LatteLoader;
import com.example.zhengyu.latte.ui.LoaderStyle;

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
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;


    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      IError ierror,
                      IFailure ifailure,
                      IRequest irequest,
                      ISuccess isuccess,
                      RequestBody body,
                      Context context,
                      LoaderStyle loader_style) {
        URL = url;
        CONTEXT = context;
        LOADER_STYLE = loader_style;
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

        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
            Log.d("dialog","start");
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
                ISUCCESS,LOADER_STYLE);
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

package com.example.zhengyu.latte.net;

import android.content.Context;

import com.example.zhengyu.latte.net.callback.IError;
import com.example.zhengyu.latte.net.callback.IFailure;
import com.example.zhengyu.latte.net.callback.IRequest;
import com.example.zhengyu.latte.net.callback.ISuccess;
import com.example.zhengyu.latte.net.callback.RequestCallBack;
import com.example.zhengyu.latte.ui.LatteLoader;
import com.example.zhengyu.latte.ui.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by ZHENGYU on 2017/10/17.
 */

public class RestClient {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParamsHolder();
    private final String DOWNLOAD_DIR;//下载目录
    private final String EXTENSION;//扩展后缀名
    private final String NAME;//下载文件名
    private final IError IERROR;
    private final IFailure IFAILURE;
    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final RequestBody BODY;// 请求实体
    private final File FILE;
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
                      LoaderStyle loader_style,
                      File file,
                      String download_dir,
                      String extension,
                      String name) {
        this.URL = url;
        this.CONTEXT = context;
        this.LOADER_STYLE = loader_style;
        this.PARAMS.putAll(params);
        this.IERROR = ierror;
        this.IFAILURE = ifailure;
        this.IREQUEST = irequest;
        this.ISUCCESS = isuccess;
        this.BODY = body;
        this.FILE = file;
        this.DOWNLOAD_DIR = download_dir;
        this.EXTENSION = extension;
        this.NAME = name;
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
           // Log.d("dialog","start");
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
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT_RAW:
                call = service.put(URL, PARAMS);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
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
                ISUCCESS,
                LOADER_STYLE);
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put(){
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }

    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

    public final void upload(){

    }

    public final void download(){

    }
}

package com.example.zhengyu.latte.net.download;

import android.os.AsyncTask;

import com.example.zhengyu.latte.net.RestCreator;
import com.example.zhengyu.latte.net.callback.IError;
import com.example.zhengyu.latte.net.callback.IFailure;
import com.example.zhengyu.latte.net.callback.IRequest;
import com.example.zhengyu.latte.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZHENGYU on 2017/10/24.
 */

public class DownloadHandler {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;//下载目录
    private final String EXTENSION;//扩展后缀名
    private final String NAME;//下载文件名
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(String url,
                           WeakHashMap<String, Object> params,
                           IRequest request,
                           String download_dir,
                           String extension,
                           String name,
                           ISuccess success,
                           IFailure failure,
                           IError error) {
        this.URL = url;
        this.PARAMS = params;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = download_dir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    public final void handlerFileDownload(){
        if(SUCCESS != null){
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL,PARAMS).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    SaveFileTask  FileTask = new SaveFileTask(SUCCESS,REQUEST);
                    FileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,NAME,DOWNLOAD_DIR,EXTENSION,responseBody);

                    //这里一定要注意判断，否则文件下载不全
                    if (FileTask.isCancelled()) {
                        if (REQUEST != null) {
                            REQUEST.onRequestEnd();
                        }
                    }
                }else {
                    if(ERROR != null){
                        ERROR.onError(response.code(),response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(FAILURE != null){
                    FAILURE.onFailure();
                }
            }
        });
    }

}

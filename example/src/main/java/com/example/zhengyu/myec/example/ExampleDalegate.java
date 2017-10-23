package com.example.zhengyu.myec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.zhengyu.latte.delegates.LatteDalegate;
import com.example.zhengyu.latte.net.RestClient;
import com.example.zhengyu.latte.net.callback.IError;
import com.example.zhengyu.latte.net.callback.IFailure;
import com.example.zhengyu.latte.net.callback.IRequest;
import com.example.zhengyu.latte.net.callback.ISuccess;

/**
 * Created by ZHENGYU on 2017/10/14.
 */

public class ExampleDalegate extends LatteDalegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootVew) {
        testRestClient();
    }

    public void testRestClient(){
        RestClient.builder()
                .url("http://news.baidu.com/")
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String reponse) {
                        Toast.makeText(getContext(),reponse,Toast.LENGTH_LONG).show();
                    }
                })
                .raw("")
                .mIRequest(new IRequest() {
                    @Override
                    public void onRequestStart() {

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                }).builder()
                .get();
    }
}

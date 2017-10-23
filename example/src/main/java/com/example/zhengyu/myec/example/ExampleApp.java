package com.example.zhengyu.myec.example;

import android.app.Application;

import com.example.zhengyu.latte.app.Latte;
import com.example.zhengyu.latte.ec.icon.FontEcModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by ZHENGYU on 2017/10/8.
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1/")
                .configure();

    }
}

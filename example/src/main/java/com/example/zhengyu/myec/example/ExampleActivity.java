package com.example.zhengyu.myec.example;

import com.example.zhengyu.latte.activities.ProxyActivity;
import com.example.zhengyu.latte.delegates.LatteDalegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public LatteDalegate setRootDelegate() {
        return new ExampleDalegate();
    }

}

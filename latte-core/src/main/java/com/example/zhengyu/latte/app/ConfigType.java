package com.example.zhengyu.latte.app;

/**
 * Created by ZHENGYU on 2017/10/8.
 */

public enum  ConfigType {

    API_HOST,//网络请求域名
    APPLICATION_CONTEXT,//全局上下文
    CONFIG_READY,//控制初始化是否完成标志
    ICON,//存储初始化项目
    HANDLER,
    INTERCEPTORS,// 拦截器
    LOADER_DELAYED//加载延迟时间

}

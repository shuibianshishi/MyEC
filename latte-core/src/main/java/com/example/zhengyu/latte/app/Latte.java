package com.example.zhengyu.latte.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by ZHENGYU on 2017/10/8.
 */

public final class Latte{

    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }
    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static HashMap<String,Object> getConfigurations(){

        return Configurator.getInstance().getEcConfigs();
    }

    public static Context getApplication(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }

}

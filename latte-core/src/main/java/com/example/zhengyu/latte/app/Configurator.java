package com.example.zhengyu.latte.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ZHENGYU on 2017/10/8.
 */

public final class Configurator {

    private static final HashMap<String,Object> EC_CONFIGS = new HashMap<>();//配置信息
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();//字体图标配置

    private Configurator(){
        EC_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
    }

    public static Configurator getInstance(){
       // Log.d("API000", Latte.getConfigurations().get(ConfigType.API_HOST.name()).toString() + "asdasdas");
        return Holder.INSTANCE;
    }

    final HashMap<String,Object> getEcConfigs(){
        return EC_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure(){
        EC_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

    private void initIcons(){
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withApiHost(String host){
        EC_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    private final Configurator withIcons(IconFontDescriptor descriptor){
        return this;
    }

    private void checkConfiguration(){
        final boolean isReady = (boolean) EC_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw  new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = EC_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) EC_CONFIGS.get(key);
    }

}

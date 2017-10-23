package com.example.zhengyu.latte.Util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.zhengyu.latte.app.Latte;

/**
 * Created by ZHENGYU on 2017/10/23.
 */

/*
    尺寸工具类
 */
public class DimenUtil {

    static final Resources resources = Latte.getApplication().getResources();
    static final DisplayMetrics dm = resources.getDisplayMetrics();

    public static int getScreenWith(){
        return dm.widthPixels;
    }

    public static int getScreenHtight(){
        return  dm.heightPixels;
    }
}

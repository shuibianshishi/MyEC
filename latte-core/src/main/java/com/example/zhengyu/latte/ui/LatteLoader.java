package com.example.zhengyu.latte.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.zhengyu.latte.R;
import com.example.zhengyu.latte.Util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by ZHENGYU on 2017/10/23.
 */

public class LatteLoader
{
    private static final int LOADER_SIZE_SCALE = 8;//缩放比例为 8
    private static final int LOADER_OFFSET_SCALE = 10;//偏移比例为 10
    private static final ArrayList<AppCompatDialog> DialogLoading = new ArrayList<>();
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();// 默认loading 图标

    public static void showLoading(Context context,String type){

        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type,context);
        dialog.setContentView(avLoadingIndicatorView);

        int ScreenWidth = DimenUtil.getScreenWith();
        int ScreenHeigth = DimenUtil.getScreenHtight();

        final Window DialogWindow = dialog.getWindow();

        if(DialogWindow != null){
            WindowManager.LayoutParams layoutParams = DialogWindow.getAttributes();
            layoutParams.width = ScreenWidth / LOADER_SIZE_SCALE;
            layoutParams.height = ScreenHeigth / LOADER_SIZE_SCALE;
            layoutParams.height = layoutParams.height + ScreenHeigth / LOADER_OFFSET_SCALE;
            layoutParams.gravity = Gravity.CENTER;
        }
        DialogLoading.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }

    public static void showLoading(Context context, Enum<LoaderStyle> loaderStyle){
        showLoading(context,loaderStyle.name());
    }

    public static void stopAllLoading() {
        for (AppCompatDialog dialog : DialogLoading) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }
}

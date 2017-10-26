package com.example.zhengyu.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.zhengyu.latte.Util.file.FileUtil;
import com.example.zhengyu.latte.app.Latte;
import com.example.zhengyu.latte.net.callback.IRequest;
import com.example.zhengyu.latte.net.callback.ISuccess;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by ZHENGYU on 2017/10/24.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(ISuccess iSuccess, IRequest iRequest){
        this.REQUEST = iRequest;
        this.SUCCESS = iSuccess;
    }
    @Override
    protected File doInBackground(Object[] params) {
        String name = (String)params[0];//文件名
        String downloadDir = (String)params[1];//下载目录
        String extension = (String)params[2];//扩展名
        final ResponseBody responsebody = (ResponseBody) params[3];
        final InputStream inputStream = responsebody.byteStream();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if(name == null){//文件名为空，则以扩展名和时间来命名
            return FileUtil.writeToDisk(inputStream, downloadDir, extension.toUpperCase(), extension);
        }else {
            return FileUtil.writeToDisk(inputStream, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS != null){
            SUCCESS.onSuccess(file.getPath().toString());
        }
        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    /*
        如果下载的文件是apk文件，自动安装
     */
    private void autoInstallApk(File file){
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            Latte.getApplication().startActivity(intent);
        }
    }
}

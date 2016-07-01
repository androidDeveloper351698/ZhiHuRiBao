package com.example.dell.zhihu.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by DELL on 2016/6/23.
 */
public class HttpUtil {
    private static AsyncHttpClient client=new AsyncHttpClient(true,80,443);

    public  static void get(String url, ResponseHandlerInterface responseHandlerInterface){
        client.get(Constant.BASEURL+url,responseHandlerInterface);
    }

    public static void getImage(String url,ResponseHandlerInterface responseHandlerInterface){
        client.get(url, responseHandlerInterface);
    }

    public static boolean isNetworkConnected(Context context){
        if(context!=null){
            ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=manager.getActiveNetworkInfo();
            if(info!=null){
                return info.isAvailable();
            }
        }
        return false;
    }
}

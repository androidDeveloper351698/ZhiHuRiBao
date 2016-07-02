package com.example.dell.zhihu.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DELL on 2016/7/2.
 */
public class SPUtil {
    private static SPUtil mSPUtil;
    private static SharedPreferences mSharedPreferences;

    public static SPUtil newInstance(Context context){
        if(mSPUtil==null){
            mSPUtil=new SPUtil();
        }
        return initSP(context);
    }
    private static SPUtil initSP(Context context){
        if(mSharedPreferences==null) {
            mSharedPreferences = context.getSharedPreferences(Constant.CONFIG, Context.MODE_PRIVATE);
        }
        return mSPUtil;
    }

    public  void save(String key,String value){
        mSharedPreferences.edit().putString(key,value).apply();
    }
    public  String get(String key,String defaultValue){
        return mSharedPreferences.getString(key,defaultValue);
    }
    public void clear(){
        mSharedPreferences.edit().clear().apply();
    }

}
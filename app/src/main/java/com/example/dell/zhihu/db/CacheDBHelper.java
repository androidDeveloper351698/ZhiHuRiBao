package com.example.dell.zhihu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DELL on 2016/7/3.
 */
public class CacheDBHelper extends SQLiteOpenHelper{
    public CacheDBHelper(Context context, int version) {
        super(context, "cache.db", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists cacheList(id Integer primary key autoincrement,date INTEGER unique,json text)");
        db.execSQL("create table if not exists contentCache(id integer primary key autoincrement,newsId integer unique,json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

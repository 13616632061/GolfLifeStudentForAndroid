package com.glorystudent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库
 * Created by hyj on 2017/1/13.
 */
public class MyDataBase extends SQLiteOpenHelper {

    public MyDataBase(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "video.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table img(id integer PRIMARY KEY AUTOINCREMENT,fileMd5 String,type String,duration String,date String,path String,zippath String,title String,content String,picBytes BLOB)");
        db.execSQL("create table videoModel(id integer PRIMARY KEY AUTOINCREMENT,fileMd5 String,type String,duration String,date String,path String,zippath String,title String,content String,picBytes BLOB)");
        db.execSQL("create table friends(id integer PRIMARY KEY AUTOINCREMENT,phonenumber String)");
        db.execSQL("create table golffriends(id integer PRIMARY KEY AUTOINCREMENT, phonenumber String, username String)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

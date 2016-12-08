package com.hdmb.ireader.db;

import android.content.Context;

import com.litesuits.orm.LiteOrm;

/**
 * 作者    武博
 * 时间    2016/7/16 0016 15:37
 * 文件    TbReader
 * 描述
 */
public class DBManager  {

    public static LiteOrm mLiteOrm;
    private static DBManager manager;

    private DBManager(Context context) {
        if (mLiteOrm == null){
            mLiteOrm = LiteOrm.newSingleInstance(context, "reader.db");
        }
        mLiteOrm.setDebugged(true);
    }

    public static DBManager getInstance(Context context){
        if (manager == null)
            manager = new DBManager(context);
        return manager;
    }

    public LiteOrm getLiteOrm(){
        return mLiteOrm;
    }

}

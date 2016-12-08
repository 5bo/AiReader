package com.hdmb.ireader.txtreader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hdmb.ireader.txtreader.bean.Page;

/**
 * 作者    武博
 * 时间    2016/8/8 0008 19:17
 * 文件    TbReader
 * 描述
 */
public class TxtPageDB extends SQLiteOpenHelper {

    public static final String DBNAME = "txtreader";
    public static final String TABLENAME = "page";

    public static final String firstElementCharIndex = "firstElementCharIndex";
    public static final String firstElementParagraphIndex = "firstElementParagraphIndex";
    public static final String lastElementCharIndex = "lastElementCharIndex";
    public static final String lastElementParagraphIndex = "lastElementParagraphIndex";

    private final String sql = "CREATE TABLE " + TABLENAME + "(id integer primary key autoincrement," +
            firstElementCharIndex + " integer, " + firstElementParagraphIndex + " integer,"
            + lastElementCharIndex + " integer, " + lastElementParagraphIndex + " integer)";


    public TxtPageDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createTable() {
        getWritableDatabase().execSQL(sql);
    }

    public void closeDB() {
        close();
    }

    public void delectTable() {
        String sql = "DROP TABLE IF EXISTS " + TABLENAME;
        getWritableDatabase().execSQL(sql);
    }

    public void savePage(int firstCharindex, int firstpindex, int lastcharindex, int lastpindex) {
        String sql = " insert into " + TABLENAME + " (" + firstElementCharIndex + ","
                + firstElementParagraphIndex + "," + lastElementCharIndex + ","
                + lastElementParagraphIndex + ") values ('" + firstCharindex
                + "','" + firstpindex + "','" + lastcharindex + "','" + lastpindex + "')";
        getWritableDatabase().execSQL(sql);
    }

    /**
     * 通过页数返回页相关数据，如果查找不到返回空
     *
     * @param index 页数下标是从1开始的
     * @return
     */
    public Page getPageByInedx(int index) {
        Page page = new Page();
        Cursor cursor = getWritableDatabase().rawQuery("select * from " + TABLENAME + " where id =" + index, null);
        cursor.moveToFirst();
        if (index >= 1 && cursor.getCount() > 0) {
            page.setFirstElementCharIndex(cursor.getInt(cursor.getColumnIndex(firstElementCharIndex)));
            page.setFirstElementParagraphIndex(cursor.getInt(cursor.getColumnIndex(firstElementParagraphIndex)));
            page.setLastElementCharIndex(cursor.getInt(cursor.getColumnIndex(lastElementCharIndex)));
            page.setLastElementParagraphIndex(cursor.getInt(cursor.getColumnIndex(lastElementParagraphIndex)));
            page.setPageIndex(index);
            cursor.close();
            return page;
        }
        return null;
    }


    public int getLastPageIndex() {
        int index = 0;
        Cursor cursor = getWritableDatabase().rawQuery("select id from " + TABLENAME, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            index = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        return index;
    }
}

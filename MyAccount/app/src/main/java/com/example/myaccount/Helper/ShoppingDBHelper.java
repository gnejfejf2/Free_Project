package com.example.myaccount.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.EntrepreneurContract;
import com.example.myaccount.Contract.ShoppingContract;


public class ShoppingDBHelper extends SQLiteOpenHelper {
    private static ShoppingDBHelper sInstance;
    private static final int version = 1;
    private static final String DB_NAME = "Shopping.db";
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s TEXT,%s TEXT)",
                    ShoppingContract.ShoppingEntry.TABLE_NAME,
                    ShoppingContract.ShoppingEntry._ID,
                    ShoppingContract.ShoppingEntry.COLUMN_NAME_BUYID,
                    ShoppingContract.ShoppingEntry.COLUMN_NAME_ITEMTITLE,
                    ShoppingContract.ShoppingEntry.COLUMN_NAME_ITEMPRICE,
                    ShoppingContract.ShoppingEntry.COLUMN_NAME_ITEMCOUNTER

            );//sql문을 스트링으로 적어놓은것 일반적인 sql테이블생성 타이틀 컨텐츠 날짜 3가지를 가짐
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ShoppingContract.ShoppingEntry.TABLE_NAME;

    public ShoppingDBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    public static ShoppingDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ShoppingDBHelper(context);

        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);//기존테이블을 지우고
        db.execSQL(SQL_CREATE_ENTRIES);//새로운 테이블 생성
    }
}

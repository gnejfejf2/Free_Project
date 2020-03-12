package com.example.myaccount.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.CompanyBoardContract;
import com.example.myaccount.Contract.Shoppingbasketcontract;


public class ShoppingbasketDBHelper extends SQLiteOpenHelper {
    private static ShoppingbasketDBHelper sInstance;
    private static final int version = 1;
    private static final String DB_NAME = "Shoppingbasket.db";
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT)",
                    Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME,
                    Shoppingbasketcontract.Shoppingbasketentry._ID,
                    Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_PRICE,
                    Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_TITLE,
                    Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_BUYUSERID,
                    Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_SELLUSERID,
                    Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_PHOTOJSON,
                    Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_ISSELECTED,
                    Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_BUYNUMBER
            );//sql문을 스트링으로 적어놓은것 일반적인 sql테이블생성 타이틀 컨텐츠 날짜 3가지를 가짐
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME;

    public ShoppingbasketDBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    public static ShoppingbasketDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ShoppingbasketDBHelper(context);

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

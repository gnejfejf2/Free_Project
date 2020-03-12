package com.example.myaccount.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.CompanyBoardContract;


public class CompanyBoardDBHelper extends SQLiteOpenHelper {
    private static CompanyBoardDBHelper sInstance;
    private static final int version = 1;
    private static final String DB_NAME = "CompanyBoard.db";
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT)",
                    CompanyBoardContract.ComponyBoardEntry.TABLE_NAME,
                    CompanyBoardContract.ComponyBoardEntry._ID,//0
                    CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_TITLE,//1
                    CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_CONTENTS,//2
                    CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_COMPANYID,//3
                    CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_DAY,//4
                    CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_PHOTOJSON,//5
                    CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_ITEMLIST,//6
                    CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_LATITUDE,//7
                    CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_LONGITUDE,//8 7,8 위도경도 5사진 1이름
                    CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_STREETID
            );//sql문을 스트링으로 적어놓은것 일반적인 sql테이블생성 타이틀 컨텐츠 날짜 3가지를 가짐
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CompanyBoardContract.ComponyBoardEntry.TABLE_NAME;

    public CompanyBoardDBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    public static CompanyBoardDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CompanyBoardDBHelper(context);

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

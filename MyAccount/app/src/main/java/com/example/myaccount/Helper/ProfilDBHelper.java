package com.example.myaccount.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myaccount.Contract.ProfilContract;


public class ProfilDBHelper extends SQLiteOpenHelper {
    private static ProfilDBHelper sInstance;
    private static final int version = 1;
    private static final String DB_NAME = "Profil.db";
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s TEXT)",
                    ProfilContract.ProfilEntry.TABLE_NAME,
                    ProfilContract.ProfilEntry._ID,
                    ProfilContract.ProfilEntry.COLUMN_NAME_DAY,
                    ProfilContract.ProfilEntry.COLUMN_NAME_MYPHOTO,
                    ProfilContract.ProfilEntry.COLUMN_NAME_GIRLPHOTO);//sql문을 스트링으로 적어놓은것 일반적인 sql테이블생성 타이틀 컨텐츠 날짜 3가지를 가짐
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProfilContract.ProfilEntry.TABLE_NAME;

    public ProfilDBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    public static ProfilDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ProfilDBHelper(context);

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

package com.example.myaccount.Helper;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        db.execSQL("CREATE TABLE MONEYBOOK (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, price INTEGER, create_at TEXT,place TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void insert(String create_at, String item, int price, String place) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO MONEYBOOK VALUES(null, '" + item + "', " + price + ", '" + create_at + "','" + place + "');");
        db.close();
    }


    public String getResult(String ID) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        String item = ID;
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력

        String query = "SELECT * FROM MONEYBOOK WHERE item Like '%" + item + "%'";
        Cursor cursor = db.rawQuery(query, null);
        db.getPageSize();
        cursor.moveToLast();

        if(cursor.getCount()!=0) {
            result +=

                    "사용금액 : "
                            + cursor.getInt(2)
                            + "원 "
                            + "\n"
                            + "사용 장소 : "
                            + cursor.getString(4)
                            + "\n"
                            + "사용 시간 : "
                            + cursor.getString(3)
                            + "\n"
                            + "\n";
        }

        while (cursor.moveToPrevious()) {
            result +=

                    "사용금액 : "
                            + cursor.getInt(2)
                            + "원 "
                            + "\n"
                            + "사용 장소 : "
                            + cursor.getString(4)
                            + "\n"
                            + "사용 시간 : "
                            + cursor.getString(3)
                            + "\n"
                            + "\n";

        }

        return result;
    }
}
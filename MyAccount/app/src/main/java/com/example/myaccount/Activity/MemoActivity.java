package com.example.myaccount.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myaccount.Contract.DdayContract;
import com.example.myaccount.Helper.DdayDBHelper;
import com.example.myaccount.Calendar.MyGridViewCalendar;
import com.example.myaccount.R;

import java.util.Date;

public class MemoActivity extends AppCompatActivity {


    public TextView mdayEditText;
    public Date selectedDate;
    private EditText mTitleEditText;
    private EditText mContentsEditText;

    private Button Button_setbutton, Button_resetbutton, Button_delete;
    private long mMemoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        mTitleEditText = findViewById(R.id.title_edit);
        mContentsEditText = findViewById(R.id.content_edit);
        mdayEditText = findViewById(R.id.day_edit);
        Button_setbutton = findViewById(R.id.Button_setbutton);
        Button_resetbutton = findViewById(R.id.Button_resetbutton);
        Button_delete = findViewById(R.id.Button_delete);


        mdayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int resultrot=1000;
                MyGridViewCalendar myGridViewCalendar = new MyGridViewCalendar();//선언
                myGridViewCalendar.setSelectedDate(new Date(),resultrot);//수정을 할때 필요함
                myGridViewCalendar.show(getSupportFragmentManager(), "grid_view_calendar");//그리고 보여주기
            }
        });//그리드뷰 캘린더랑 연동해서 day를 채운다

        Intent intent = getIntent();

        if (intent != null) {
            mMemoId = intent.getLongExtra("id", -1);
            String title = intent.getStringExtra("title");
            String contents = intent.getStringExtra("contents");
            String day = intent.getStringExtra("day");


            mTitleEditText.setText(title);
            mContentsEditText.setText(contents);
            mdayEditText.setText(day);
        }//인텐트가 널이아니라면 즉 새로추가버튼이아닌 편집버튼등이라면 기존에 있던 값을 가져와야한다


        Button_setbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEditText.getText().toString();
                String contents = mContentsEditText.getText().toString();
                String day = mdayEditText.getText().toString();

                ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
                contentValues.put(DdayContract.MemoEntry.COLUMN_NAME_TITLE, title);
                contentValues.put(DdayContract.MemoEntry.COLUMN_NAME_CONTENTS, contents);
                contentValues.put(DdayContract.MemoEntry.COLUMN_NAME_DAY, day);
                SQLiteDatabase db = DdayDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                db.insert(DdayContract.MemoEntry.TABLE_NAME, null, contentValues);//데이터베이스에 컨텐트벨류값을 넣어준다
                setResult(RESULT_OK);//이게있어야 바로바로 변함
                Intent intent = new Intent(MemoActivity.this, MemolistActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //////////////삽입문
        Button_resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEditText.getText().toString();
                String contents = mContentsEditText.getText().toString();
                String day = mdayEditText.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put(DdayContract.MemoEntry.COLUMN_NAME_TITLE, title);
                contentValues.put(DdayContract.MemoEntry.COLUMN_NAME_CONTENTS, contents);
                contentValues.put(DdayContract.MemoEntry.COLUMN_NAME_DAY, day);
                SQLiteDatabase db = DdayDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                db.update(DdayContract.MemoEntry.TABLE_NAME, contentValues, DdayContract.MemoEntry._ID + "=" + mMemoId, null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문
                setResult(RESULT_OK);
                Intent intent = new Intent(MemoActivity.this, MemolistActivity.class);
                startActivity(intent);
                finish();
            }
        });//수정하기

        Button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = DdayDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                db.delete(DdayContract.MemoEntry.TABLE_NAME, DdayContract.MemoEntry._ID + "=" + mMemoId, null);//데이터삭제를 도와주는 db.delete
                setResult(RESULT_OK);//setResult()를 통해 돌려줄 결과를 저장
                Intent intent = new Intent(MemoActivity.this, MemolistActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}

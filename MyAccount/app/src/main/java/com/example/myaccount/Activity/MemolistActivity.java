package com.example.myaccount.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import android.view.View;

import android.widget.AdapterView;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.myaccount.Adapter.DdayAdapter;
import com.example.myaccount.Contract.DdayContract;
import com.example.myaccount.Helper.DdayDBHelper;
import com.example.myaccount.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MemolistActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_INSERT = 1000;
    private DdayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memolist);


        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MemolistActivity.this, MemoActivity.class),
                        REQUEST_CODE_INSERT);///
                finish();
            }
        });

        final SwipeMenuListView listView;
        listView = findViewById(R.id.memo_list);

        Cursor cursor = getMemoCursor();

        mAdapter = new DdayAdapter(this, cursor, true);
        listView.setAdapter(mAdapter);
        //리스트뷰가 어뎁터에 연결이 되있음 출력을 해주기위해서
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemolistActivity.this, MemoActivity.class);//추후수정
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                //그위치에있는 값을 가져오기위해 포지션 사용
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DdayContract.MemoEntry.COLUMN_NAME_TITLE));
                String contents = cursor.getString(cursor.getColumnIndexOrThrow(DdayContract.MemoEntry.COLUMN_NAME_CONTENTS));
                String day = cursor.getString(cursor.getColumnIndexOrThrow(DdayContract.MemoEntry.COLUMN_NAME_DAY));
                //커서에서 값을 가져옴
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("contents", contents);
                intent.putExtra("day", day);
                //데이터추가해서 넘기기
                startActivityForResult(intent, REQUEST_CODE_INSERT);
                finish();
                //화면 즉시전환을위해 스타트엑티비티 포리절트사용
            }
        });
//////////////////////////////////오픈소스 스와이프 메뉴바//////////////////
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "Close" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(200);
                // set item title
                openItem.setTitle("수정");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(200);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
//////////////////////////////오픈소스///////////////////////////////
        listView.setMenuCreator(creator);
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
                listView.smoothOpenMenu(position);
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
                listView.smoothOpenMenu(position);
            }
        });
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
///////////////////////////////////////////////////수정코드 //////////// 수정할수있는 화면으로 넘겨준다 + 하지만 인텐트로 저장된값을 넘겨주어야하는 작업 필수! 없다면 빈화면
                        Intent intent2 = new Intent(MemolistActivity.this, MemoActivity.class);
                        Cursor cursor0 = (Cursor) mAdapter.getItem(position);
                        long id = cursor0.getLong(cursor0.getColumnIndexOrThrow(DdayContract.MemoEntry._ID));
                        String title = cursor0.getString(cursor0.getColumnIndexOrThrow(DdayContract.MemoEntry.COLUMN_NAME_TITLE));
                        String contents = cursor0.getString(cursor0.getColumnIndexOrThrow(DdayContract.MemoEntry.COLUMN_NAME_CONTENTS));
                        String day = cursor0.getString(cursor0.getColumnIndexOrThrow(DdayContract.MemoEntry.COLUMN_NAME_DAY));
                        //커서에서 값을 가져옴
                        intent2.putExtra("id", id);
                        intent2.putExtra("title", title);
                        intent2.putExtra("contents", contents);
                        intent2.putExtra("day", day);
                        ////그위치에 데이터를 넣어주기위해서 값을 가져온후 넣어줍니다 없으면 수정이 안됩니다.
                        startActivity(intent2);
                        finish();
                        setResult(RESULT_OK);
                        break;
                    case 1:

                        ///////////////////////////딜리트문
                        Cursor cursor = (Cursor) mAdapter.getItem(position);
                        SQLiteDatabase db = DdayDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                        String mMemoId = cursor.getString(cursor.getColumnIndexOrThrow(DdayContract.MemoEntry._ID));//아이디값 받아오기
                        db.delete(DdayContract.MemoEntry.TABLE_NAME, DdayContract.MemoEntry._ID + "=" + mMemoId, null);//아이디값받아와서 그위치에있는 아이템 삭제
                        setResult(RESULT_OK);//리절트 ok를 셋팅
                        onActivityResult(1000, RESULT_OK, null);//바로바로 실행시켜주기위해

                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
/////////////////////////오픈소스/////////////////////////////////////////////


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INSERT && resultCode == RESULT_OK) {
            mAdapter.swapCursor(getMemoCursor());//새로운값이 생겻을때 바로바로 화면이 전환되기위해 스왑커서를 이용
        }
    }

    private Cursor getMemoCursor() {
        DdayDBHelper dbHelper = DdayDBHelper.getInstance(this);
        return dbHelper.getReadableDatabase().query(DdayContract.MemoEntry.TABLE_NAME, null, null, null, null, null, DdayContract.MemoEntry.COLUMN_NAME_DAY);
        //전체값을 가져오고 정렬을 함 날짜에따라 정렬할 예정임
    }


}

package com.example.myaccount.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myaccount.Adapter.BoardAdapter;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Helper.BoardDBHelper;
import com.example.myaccount.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class BoardActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_INSERT = 1000;
    private BoardAdapter mAdapter;
    private Button button_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

       Intent intent=getIntent();
       final String userID=intent.getStringExtra("userID");

       FloatingActionButton board_write_button = findViewById(R.id.board_write_button);
        button_company=findViewById(R.id.button_company);

        board_write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createintent=new Intent(BoardActivity.this, BoardWriteActivity.class);
                createintent.putExtra("userID",userID);
                startActivityForResult(createintent,
                        REQUEST_CODE_INSERT);///
                finish();
            }
        });
        button_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent companyintent=new Intent(BoardActivity.this,CompanyActivity.class);
                companyintent.putExtra("userID",userID);
                startActivity(companyintent);
                finish();


            }
        });




        ListView listView;

        listView = findViewById(R.id.ListView_Board);

        Cursor cursor = getBoardCursor();

        mAdapter = new BoardAdapter(this, cursor, true);
        listView.setAdapter(mAdapter);
        //리스트뷰가 어뎁터에 연결이 되있음 출력을 해주기위해서
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) mAdapter.getItem(position);

                if (userID.equals(cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_USERID)))) {
                    //그위치에있는 값을 가져오기위해 포지션 사용
                    Intent intent = new Intent(BoardActivity.this, BoardMyCheckActivity.class);//추후수정
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_TITLE));
                    String contents = cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_CONTENTS));
                    String day = cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_DAY));
                    String boardid =cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_USERID));
                    String boardphoto=cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_PHOTOJSON));
                    Log.d("사진테스트14",cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_PHOTOJSON)));
                    //커서에서 값을 가져옴
                    intent.putExtra("id", id);
                    intent.putExtra("title", title);
                    intent.putExtra("contents", contents);
                    intent.putExtra("day", day);
                    intent.putExtra("boardid",boardid);
                    intent.putExtra("userID", userID);
                    intent.putExtra("photo",boardphoto);
                    //데이터추가해서 넘기기
                    startActivityForResult(intent, REQUEST_CODE_INSERT);

                    //화면 즉시전환을위해 스타트엑티비티 포리절트사용
                }
                else{
                    Intent intent = new Intent(BoardActivity.this, BoardCheckActivity.class);//추후수정
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_TITLE));
                    String contents = cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_CONTENTS));
                    String day = cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_DAY));
                    String boardid =cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_USERID));
                    String boardphoto=cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.BoardEntry.COLUMN_NAME_PHOTOJSON));
                    //커서에서 값을 가져옴
                    intent.putExtra("id", id);
                    intent.putExtra("title", title);
                    intent.putExtra("contents", contents);
                    intent.putExtra("day", day);
                    intent.putExtra("userID", userID);
                    intent.putExtra("boardid",boardid);
                    intent.putExtra("photo",boardphoto);
                    Log.d("여기유져",userID+boardid);
                    //데이터추가해서 넘기기
                    startActivityForResult(intent, REQUEST_CODE_INSERT);

                }
            }
        });



    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INSERT && resultCode == RESULT_OK) {
            mAdapter.swapCursor(getBoardCursor());//새로운값이 생겻을때 바로바로 화면이 전환되기위해 스왑커서를 이용
        }
    }

    private Cursor getBoardCursor() {
        BoardDBHelper dbHelper = BoardDBHelper.getInstance(this);
        return dbHelper.getReadableDatabase().query(BoardContract.BoardEntry.TABLE_NAME, null, null, null, null, null, BoardContract.BoardEntry._ID +" DESC");
        //전체값을 가져오고 정렬을 함 날짜에따라 정렬할 예정임
    }


}

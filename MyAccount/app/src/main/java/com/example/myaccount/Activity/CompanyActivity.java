package com.example.myaccount.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myaccount.Adapter.BoardAdapter;
import com.example.myaccount.Adapter.CompanyBoardAdapter;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.CompanyBoardContract;
import com.example.myaccount.Contract.EntrepreneurContract;
import com.example.myaccount.Helper.BoardDBHelper;
import com.example.myaccount.Helper.CompanyBoardDBHelper;
import com.example.myaccount.Helper.EntrepreneurDBHelper;
import com.example.myaccount.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class CompanyActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_INSERT = 1000;
    private CompanyBoardAdapter mAdapter;
    private Button button_board, button_entrepreneur;
    private String data,testdata3,testdata4,number,place;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");


        SharedPreferences map = getSharedPreferences("map", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = map.edit();
        //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
        editor.clear();
        editor.commit();


        FloatingActionButton Companyboard_write_button = findViewById(R.id.Companyboard_write_button);
        button_board = findViewById(R.id.button_board);
        button_entrepreneur = findViewById(R.id.button_entrepreneur);
        Companyboard_write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = EntrepreneurDBHelper.getInstance(getApplicationContext()).getReadableDatabase();
                String query = "SELECT * FROM " + EntrepreneurContract.EntrepreneurEntry.TABLE_NAME
                        + " WHERE " + EntrepreneurContract.EntrepreneurEntry.COLUMN_NAME_ENTREPRENEURID + " Like '%" + userID + "%'";
                Cursor cursor = db.rawQuery(query, null);
                db.getPageSize();
                cursor.moveToLast();
                if (cursor.getCount() != 0) {
                    testdata3 = cursor.getString(3);
                }

                while (cursor.moveToPrevious()) {
                    testdata4 += cursor.getString(3);

                }
                Log.d("testdata3",testdata3+"");

                if(testdata3!=null) {
                    if(testdata3.equals("false")) {
                        Intent createintent = new Intent(CompanyActivity.this, CompanyWriteActivity.class);//수정해야함 아직안만들어짐
                        createintent.putExtra("userID", userID);
                        startActivityForResult(createintent,
                                REQUEST_CODE_INSERT);///
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"이미 작성된 글이 존재합니다.",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"권한이 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent companyintent = new Intent(CompanyActivity.this, BoardActivity.class);
                companyintent.putExtra("userID", userID);
                startActivity(companyintent);
                finish();


            }
        });
        button_entrepreneur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(CompanyActivity.this);

                ad.setTitle("사업자등록");       // 제목 설정


// EditText 삽입하기
                et = new EditText(CompanyActivity.this);
                et.setHint("사업자번호를 입력해주세요");
                ad.setView(et);

// 확인 버튼 설정
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        number = et.getText().toString();

                        String testdata=null;
                        String testdata2=null;
                        String place=null;
                        SQLiteDatabase db = EntrepreneurDBHelper.getInstance(getApplicationContext()).getReadableDatabase();
                        String query = "SELECT * FROM " + EntrepreneurContract.EntrepreneurEntry.TABLE_NAME
                                + " WHERE " + EntrepreneurContract.EntrepreneurEntry.COLUMN_NAME_ENTREPRENEURID + " Like '%" + userID + "%'";
                        Cursor cursor = db.rawQuery(query, null);
                        db.getPageSize();
                        cursor.moveToLast();
                        if (cursor.getCount() != 0) {
                            if(cursor.getString(0).equals(userID)){
                                testdata = cursor.getString(0);
                                place+=cursor.getString(1);
                            }


                        }

                        while (cursor.moveToPrevious()) {
                            if(cursor.getString(0).equals(userID)){
                                testdata += cursor.getString(0);

                            }
                        }
                        db.close();
                        SQLiteDatabase db2 = EntrepreneurDBHelper.getInstance(getApplicationContext()).getReadableDatabase();
                        String query2 = "SELECT * FROM " + EntrepreneurContract.EntrepreneurEntry.TABLE_NAME
                                + " WHERE " + EntrepreneurContract.EntrepreneurEntry.COLUMN_NAME_ENTREPRENEURNUMBER + " Like '%" + number + "%'";
                        Cursor cursor2 = db2.rawQuery(query2, null);
                        db2.getPageSize();
                        cursor2.moveToLast();
                        if (cursor2.getCount() != 0) {
                            if(cursor2.getString(2).equals(number)){
                                testdata2 = cursor2.getString(2);
                            }
                        }

                        while (cursor2.moveToPrevious()) {
                            if(cursor2.getString(2).equals(number)){
                                testdata2 = cursor2.getString(2);
                            }
                        }
                        db2.close();

                        Log.d("뭐가문제지",testdata+testdata2+"");
                        if (testdata == null) {
                            if(testdata2 == null) {
                              new Thread(new Runnable() {

                                    @Override

                                    public void run() {

                                        data = getData(); // 하단의 getData 메소드를 통해 데이터를 파싱

                                        if (data.length() <= 0) {
                                            CompanyActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(CompanyActivity.this, "존재하지 않는 사업자번호입니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        } else {
                                            ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
                                            contentValues.put(EntrepreneurContract.EntrepreneurEntry.COLUMN_NAME_ENTREPRENEURID, userID);
                                            contentValues.put(EntrepreneurContract.EntrepreneurEntry.COLUMN_NAME_ENTREPRENEURPLACE, data + "");
                                            contentValues.put(EntrepreneurContract.EntrepreneurEntry.COLUMN_NAME_ENTREPRENEURNUMBER, number);
                                            contentValues.put(EntrepreneurContract.EntrepreneurEntry.COLUMN_NAME_ENTREPRENEURBOARD, "false");

                                            SQLiteDatabase db = EntrepreneurDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                                            db.insert(EntrepreneurContract.EntrepreneurEntry.TABLE_NAME, null, contentValues);
                                            CompanyActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(CompanyActivity.this, "사업자 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                }).start();

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"이미사용된 번호입니다..",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),place+"로 인증되어있습니다.",Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();     //닫기
                        // Event
                    }
                });


// 취소 버튼 설정
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

// 창 띄우기
                ad.show();


            }
        });

        ListView listView;

        listView = findViewById(R.id.ListView_CompanyBoard);

        Cursor cursor = getCompanyBoardCursor();

        mAdapter = new CompanyBoardAdapter(this, cursor, true);
        listView.setAdapter(mAdapter);
        //리스트뷰가 어뎁터에 연결이 되있음 출력을 해주기위해서
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = (Cursor) mAdapter.getItem(position);
                if (userID.equals(cursor.getString(cursor.getColumnIndexOrThrow(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_COMPANYID)))) {

                    Intent intent = new Intent(CompanyActivity.this, CompanyBoardCheckActivity.class);//추후수정
                    //커서에서 값을 가져옴
                    intent.putExtra("id", id);

                    intent.putExtra("userID", userID);

                    //데이터추가해서 넘기기
                    startActivityForResult(intent, REQUEST_CODE_INSERT);

                    //그위치의 아이템까지만 가져옴
                } else {
                    Intent intent = new Intent(CompanyActivity.this, CompanyBoardUserCheckActivity.class);//추후수정
                    intent.putExtra("id", id);

                    intent.putExtra("userID", userID);

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
            mAdapter.swapCursor(getCompanyBoardCursor());//새로운값이 생겻을때 바로바로 화면이 전환되기위해 스왑커서를 이용
        }
    }

    private Cursor getCompanyBoardCursor() {
        CompanyBoardDBHelper dbHelper = CompanyBoardDBHelper.getInstance(this);
        return dbHelper.getReadableDatabase().query(CompanyBoardContract.ComponyBoardEntry.TABLE_NAME, null, null, null, null, null, BoardContract.BoardEntry._ID + " DESC");
        //전체값을 가져오고 정렬을 함 날짜에따라 정렬할 예정임
    }

    String getData() {

        StringBuffer buffer = new StringBuffer();


        String str = et.getText().toString();

        String location = URLEncoder.encode(str);


        String queryUrl = "http://apis.data.go.kr/B552015/NpsBplcInfoInqireService/getDetailInfoSearch?"

                + "seq=" + location

                + "&serviceKey=u7H29jJvMVMDdsdVo5l%2BbJomOrjSnQrP3tpCGOAyc%2BWPP4SNQXAwC58UbLAIKStWcKjuGDRu2pYUTH43x7Q4Pg%3D%3D";


        try {

            URL url = new URL(queryUrl); // 문자열로 된 요청 url을 URL 객체로 생성.

            InputStream is = url.openStream(); // url 위치로 인풋스트림 연결


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            // inputstream 으로부터 xml 입력받기

            xpp.setInput(new InputStreamReader(is, "UTF-8"));


            String tag;


            xpp.next();

            int eventType = xpp.getEventType();


            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {

                    case XmlPullParser.START_DOCUMENT:


                        break;


                    case XmlPullParser.START_TAG:

                        tag = xpp.getName(); // 태그 이름 얻어오기


                        if (tag.equals("item")) ;

                        else if (tag.equals("wkplNm")) {

                            buffer.append("wkplNm : ");

                            xpp.next();

                            // addr 요소의 TEXT 읽어와서 문자열버퍼에 추가

                            buffer.append(xpp.getText());

                            buffer.append("\n"); // 줄바꿈 문자 추가

                        } else if (tag.equals("회사 :")) {

                            buffer.append("고유번호 : ");

                            xpp.next();

                            buffer.append(xpp.getText());

                            buffer.append("\n");

                        }
                        break;


                    case XmlPullParser.TEXT:

                        break;


                    case XmlPullParser.END_TAG:

                        tag = xpp.getName(); // 태그 이름 얻어오기

                        if (tag.equals("item")) buffer.append("\n"); // 첫번째 검색결과종료 후 줄바꿈

                        break;

                }

                eventType = xpp.next();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }


        return buffer.toString(); // 파싱 다 종료 후 StringBuffer 문자열 객체 반환

    }

}
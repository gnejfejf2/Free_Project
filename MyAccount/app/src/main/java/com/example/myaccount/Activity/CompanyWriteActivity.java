package com.example.myaccount.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.CompanyBoardContract;
import com.example.myaccount.Contract.EntrepreneurContract;
import com.example.myaccount.Helper.BoardDBHelper;
import com.example.myaccount.Helper.CompanyBoardDBHelper;
import com.example.myaccount.Helper.EntrepreneurDBHelper;
import com.example.myaccount.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CompanyWriteActivity extends AppCompatActivity {


    public TextView mdayEditText;

    private EditText mTitleEditText;
    private EditText mContentsEditText;
    private ImageView test;
    private Button Button_setbutton, Button_image, Button_mapbutton;
    private String userID;

    private Uri selectmyphoto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyboardwrite);

        mTitleEditText = findViewById(R.id.title_edit);
        mContentsEditText = findViewById(R.id.content_edit);
        mdayEditText = findViewById(R.id.day_edit);
        Button_setbutton = findViewById(R.id.Button_setbutton);
        Button_image = findViewById(R.id.Button_image);
        Button_mapbutton = findViewById(R.id.Button_mapbutton);
        test = findViewById(R.id.test);

        Intent intent = getIntent();

////////////////////////////////////////////////////////////////////////////
        Button_mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyWriteActivity.this, MapselectActivity.class);
                startActivity(intent);
            }
        });


///////////////////////////////////////////////////////////////////////////
        Button_setbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEditText.getText().toString();
                String contents = mContentsEditText.getText().toString();
                //////////오늘날자 가져오기///////////
                Date currentTime = Calendar.getInstance().getTime();
                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(currentTime);//오늘날짜 가져오기
                String day = date_text;

                Intent intent = getIntent();
                userID = intent.getStringExtra("userID");

                if (title.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (contents.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (selectmyphoto == null) {
                    Toast.makeText(getApplicationContext(), "대표이미지를 넣어주세요", Toast.LENGTH_SHORT).show();
                } else {

                    SQLiteDatabase db2 = EntrepreneurDBHelper.getInstance(getApplicationContext()).getWritableDatabase();
                    ContentValues contentValues2 = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
                    contentValues2.put(EntrepreneurContract.EntrepreneurEntry.COLUMN_NAME_ENTREPRENEURBOARD, "true");
                    db2.update(EntrepreneurContract.EntrepreneurEntry.TABLE_NAME,contentValues2,
                            EntrepreneurContract.EntrepreneurEntry.COLUMN_NAME_ENTREPRENEURID+"='"+userID+"'",null);




                    SharedPreferences map = getSharedPreferences("map", Activity.MODE_PRIVATE);
                    //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
                    // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
                    // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
                    String latitude = map.getString("latitude", null);//위도를 가져옴
                    String longitude = map.getString("longitude", null);//경도를 가져옴
                    String streetid = map.getString("streetid", null);//도로명 주소를 가져옴
                    ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
                    contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_TITLE, title);
                    contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_CONTENTS, contents);
                    contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_DAY, day);
                    contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_COMPANYID, userID);
                    contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_PHOTOJSON, selectmyphoto + "");
                    contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_LATITUDE, latitude);
                    contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_LONGITUDE, longitude);
                    contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_STREETID, streetid);
                    SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                    db.insert(CompanyBoardContract.ComponyBoardEntry.TABLE_NAME, null, contentValues);//데이터베이스에 컨텐트벨류값을 넣어준다
                    setResult(RESULT_OK);//이게있어야 바로바로 변함
                    Intent intent2 = new Intent(CompanyWriteActivity.this, CompanyActivity.class);
                    intent2.putExtra("userID", userID);

                    db.close();



                    startActivity(intent2);
                    finish();
                }
            }
        });
        //////////////삽입문

        Button_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, 1);
            }
        });


    }
    /////////////////////////////////이미지가 변하면 바로 보여주기위해 온엑티비티 리절트

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            // Make sure the request was successful
            selectmyphoto = selectmyphoto(data);
            /////////////////////////////////////////////////////////
            ImageView title_ImageView = findViewById(R.id.title_ImageView);
            Log.d("사진 실행 2", "사진 실행2");

            title_ImageView.setImageURI(selectmyphoto);
        } else {
            return;
        }
//////////////////////////////////////////////////


    }


    public Uri selectmyphoto(Intent data) {


        Uri photoUri = data.getData();//인텐트에 저장된값으로 스트림을 만들고
        return photoUri;

    }


}

package com.example.myaccount.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.CompanyBoardContract;
import com.example.myaccount.Contract.Shoppingbasketcontract;
import com.example.myaccount.Helper.CompanyBoardDBHelper;
import com.example.myaccount.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CompanyReWriteActivity extends AppCompatActivity {


    public TextView mdayEditText;

    private EditText mTitleEditText;
    private EditText mContentsEditText;
    private ImageView test;
    private Button Button_setbutton, Button_image,Button_mapbutton;
    private String title,contents,day,photojson,useuserID,itemlist,latitude,longitude;

    private Uri selectmyphoto=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyboardwrite);

        mTitleEditText = findViewById(R.id.title_edit);
        mContentsEditText = findViewById(R.id.content_edit);
        mdayEditText = findViewById(R.id.day_edit);
        Button_setbutton = findViewById(R.id.Button_setbutton);
        Button_image = findViewById(R.id.Button_image);
        Button_mapbutton=findViewById(R.id.Button_mapbutton);
        ImageView title_ImageView = findViewById(R.id.title_ImageView);
        test = findViewById(R.id.test);

        Intent intent = getIntent();
        final long mMemoId = intent.getLongExtra("id", -1);


        SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getReadableDatabase();
        String sql = "select * from ComponyBoard where _id = "+mMemoId+";";
        Cursor result = db.rawQuery(sql, null);

        result.moveToFirst();


        title = result.getString(1);
        contents=result.getString(2);
        day = result.getString(4);
        photojson=result.getString(5);
        useuserID = result.getString(3);
        itemlist = result.getString(6);
        latitude = result.getString(7);
        longitude = result.getString(8);
        result.close();

        mTitleEditText.setText(title);
        mContentsEditText.setText(contents);
        /////////////////////////////////////////////
        Uri uri = Uri.parse(photojson);
        Glide.with(getApplicationContext()).load(uri).into(title_ImageView);



////////////////////////////////////////////////////////////////////////////
        Button_mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CompanyReWriteActivity.this,MapselectActivity.class);
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


                if (title.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (contents.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {


                    SharedPreferences map = getSharedPreferences("map", Activity.MODE_PRIVATE);
                    String relatitude = map.getString("latitude", null);//위도를 가져옴
                    String relongitude = map.getString("longitude", null);//경도를 가져옴
                    String restreetid=map.getString("streetid",null);
                    //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
                    // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
                    // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
                    if(relatitude==null&&relongitude==null){
                        ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_TITLE, title);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_CONTENTS, contents);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_DAY, day);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_COMPANYID, useuserID);


                        if(selectmyphoto!=null) {
                            photojson = selectmyphoto + "";
                        }
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_PHOTOJSON, photojson);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_LATITUDE,latitude);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_ITEMLIST,itemlist);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_LONGITUDE,longitude);

                        SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                        db.update(CompanyBoardContract.ComponyBoardEntry.TABLE_NAME, contentValues, CompanyBoardContract.ComponyBoardEntry._ID + "=" + mMemoId, null);
                        db.close();
                        setResult(RESULT_OK);//이게있어야 바로바로 변함
                        Intent intent2 = new Intent(CompanyReWriteActivity.this, CompanyActivity.class);
                        intent2.putExtra("userID", useuserID);



                        startActivity(intent2);
                        finish();
                    }
                    else {
                        ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_TITLE, title);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_CONTENTS, contents);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_DAY, day);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_COMPANYID, useuserID);


                        if(selectmyphoto!=null) {
                            photojson = selectmyphoto + "";
                        }
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_PHOTOJSON, photojson);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_ITEMLIST,itemlist);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_LATITUDE,relatitude);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_LONGITUDE,relongitude);
                        contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_STREETID,restreetid);
                        SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                        db.update(CompanyBoardContract.ComponyBoardEntry.TABLE_NAME, contentValues, CompanyBoardContract.ComponyBoardEntry._ID + "=" + mMemoId, null);
                        db.close();
                        setResult(RESULT_OK);//이게있어야 바로바로 변함
                        Intent intent2 = new Intent(CompanyReWriteActivity.this, CompanyActivity.class);
                        intent2.putExtra("userID", useuserID);



                        startActivity(intent2);
                        finish();



                    }



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
        if(data!=null) {
            // Make sure the request was successful
            selectmyphoto = selectmyphoto(data);
            /////////////////////////////////////////////////////////
            ImageView title_ImageView = findViewById(R.id.title_ImageView);
            Log.d("사진 실행 2", "사진 실행2");

            title_ImageView.setImageURI(selectmyphoto);
        }
        else{
            return;
        }
//////////////////////////////////////////////////


    }


    public Uri selectmyphoto(Intent data) {


            Uri photoUri = data.getData();//인텐트에 저장된값으로 스트림을 만들고
            return photoUri;

    }


}

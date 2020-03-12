package com.example.myaccount.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.PhotoContract;
import com.example.myaccount.Helper.BoardDBHelper;
import com.example.myaccount.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BoardWriteActivity extends AppCompatActivity {


    public TextView mdayEditText;
    public Date selectedDate;
    private EditText mTitleEditText;
    private EditText mContentsEditText;
    private ImageView test;
    private Button Button_setbutton, Button_image;
    private ArrayList<String> picArr = new ArrayList<String>();
    private  JSONArray jsonArray = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardwrite);

        mTitleEditText = findViewById(R.id.title_edit);
        mContentsEditText = findViewById(R.id.content_edit);
        mdayEditText = findViewById(R.id.day_edit);
        Button_setbutton = findViewById(R.id.Button_setbutton);
        Button_image = findViewById(R.id.Button_image);
        test = findViewById(R.id.test);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");

        Log.d("userID", userID);
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
                String userID = intent.getStringExtra("userID");

                Log.d("userID", userID);


                ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
                contentValues.put(BoardContract.BoardEntry.COLUMN_NAME_TITLE, title);
                contentValues.put(BoardContract.BoardEntry.COLUMN_NAME_CONTENTS, contents);
                contentValues.put(BoardContract.BoardEntry.COLUMN_NAME_DAY, day);
                contentValues.put(BoardContract.BoardEntry.COLUMN_NAME_USERID, userID);
                contentValues.put(BoardContract.BoardEntry.COLUMN_NAME_PHOTOJSON, jsonArray.toString());



                if(title.getBytes().length <= 0){
                    Toast.makeText(getApplicationContext(),"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(contents.getBytes().length <= 0){
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    SQLiteDatabase db = BoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                    db.insert(BoardContract.BoardEntry.TABLE_NAME, null, contentValues);//데이터베이스에 컨텐트벨류값을 넣어준다
                    setResult(RESULT_OK);//이게있어야 바로바로 변함
                    Intent intent2 = new Intent(BoardWriteActivity.this, BoardActivity.class);
                    intent2.putExtra("userID", userID);
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
       if(data!=null) {
           Uri selectmyphoto = selectmyphoto(data);
           /////////////////////////////////////////////////////////
           LinearLayout write_contentlayout = findViewById(R.id.write_contentlayout);
           Log.d("사진 실행 2", "사진 실행2");
           ImageView imageView = new ImageView(getApplicationContext());
           imageView.setImageURI(selectmyphoto);
           write_contentlayout.addView(imageView);
//////////////////////////////////////////////////


           picArr.add(selectmyphoto + "");
           Log.d("사진테스트 10", picArr.get(0) + "");

           JSONObject jsonObject = new JSONObject();
           for (int i = 0; i < picArr.size(); i++) {
               try {

                   Log.d("사진테스트 12", picArr.get(i) + "");
                   jsonObject.put("photo", picArr.get(i));

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
           jsonArray.put(jsonObject);
           Log.d("사진테스트 11", jsonArray.toString());


           //내사진 변경시
           /////////////내용에 사진넣어주는거 바로 적용해야함
       }
       else return;
    }


    public Uri selectmyphoto(Intent data) {

            Uri photoUri = data.getData();//인텐트에 저장된값으로 스트림을 만들고
            Log.d("사진테스트24",photoUri+"");
        return photoUri;
    }


}

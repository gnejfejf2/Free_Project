package com.example.myaccount.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.Adapter.PhotoAdapter;
import com.example.myaccount.Adapter.ReWritePhotoAdapter;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Helper.BoardDBHelper;
import com.example.myaccount.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BoardReWriteActivity extends AppCompatActivity {


    public TextView title_edit, content_edit;

    public Button Button_resetbutton, Button_delete, Button_reimage;
    private long mMemoId = -1;
    ArrayList<String> Photolist = new ArrayList<String>();
    private JSONArray mArray, recreateArray;
    private ReWritePhotoAdapter reWritePhotoAdapter;
    private int mposition;
    private  RecyclerView PhotoRecyclerView;
    private  LinearLayoutManager PhotoLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardrewrite);

        title_edit = findViewById(R.id.title_edit);
        content_edit = findViewById(R.id.content_edit);
        Button_resetbutton = findViewById(R.id.Button_resetbutton);
        Button_delete = findViewById(R.id.Button_delete);
        Button_reimage = findViewById(R.id.Button_reimage);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");


        mMemoId = intent.getLongExtra("id", -1);
        String title = intent.getStringExtra("title");
        String contents = intent.getStringExtra("contents");
        final String photo = intent.getStringExtra("photo");
        String day = intent.getStringExtra("day");
        String id = intent.getStringExtra("userID");

        title_edit.setText(title);

        content_edit.setText(contents);

        //////////////////////////////////////////////////////////////////////
        if (photo.equals(null)) {
            return;
        } else {
            try {
                mArray = new JSONArray(photo);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



            //////////////////////////////////////////////////////////////////////

            for (int i = 0; i < mArray.length(); i++) {
                try {
                    JSONObject jObject = mArray.getJSONObject(i);
                    Log.d("사진테스트20", jObject.getString("photo"));
                    String comment = jObject.getString("photo");
                    Photolist.add(comment);
                    Log.d("사진테스트18", Photolist.get(i) + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            //json으로 가져온 사진 가져와서 어레이리스트
            RecyclerView PhotoRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_rewritephoto);
            LinearLayoutManager PhotoLinearLayoutManager = new LinearLayoutManager(this);
            PhotoRecyclerView.setLayoutManager(PhotoLinearLayoutManager);

            reWritePhotoAdapter = new ReWritePhotoAdapter(this, Photolist, mMemoId);

            PhotoRecyclerView.setAdapter(reWritePhotoAdapter);


        }


        ////////////////////////////////////////////////////////////////////////////


        reWritePhotoAdapter.setOnItemClickListener(new ReWritePhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mposition = position;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent,3);
            }
        });


        ////////////////////////////////클릭리스너
        reWritePhotoAdapter.setOnItemLongClickListener(new ReWritePhotoAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {


                Photolist.remove(position);

                Log.d("사진테스트50", Photolist + "");
                JSONArray jArray2 = new JSONArray();//배열이 필요할때


                for (int i = 0; i < Photolist.size(); i++) {
                    try {
                        JSONObject secondjsonObject = new JSONObject();
                        Log.d("사진테스트 51", Photolist.get(i) + "");
                        secondjsonObject.put("photo", Photolist.get(i));
                        jArray2.put(secondjsonObject);
                        Log.d("사진테스트 52", jArray2.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("사진테스트 53", jArray2.toString());
                Log.d("사진테스트54", mMemoId + "");
                ContentValues contentValues = new ContentValues();
                contentValues.put(BoardContract.BoardEntry.COLUMN_NAME_PHOTOJSON, jArray2.toString());
                SQLiteDatabase db = BoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                db.update(BoardContract.BoardEntry.TABLE_NAME, contentValues, BoardContract.BoardEntry._ID + "=" + mMemoId, null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문


                onActivityResult(RESULT_OK,2,null);
                db.close();


            }


        });


        ///////////////////////////////////////
        Button_resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_edit.getText().toString();
                String contents = content_edit.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put(BoardContract.BoardEntry.COLUMN_NAME_TITLE, title);
                contentValues.put(BoardContract.BoardEntry.COLUMN_NAME_CONTENTS, contents);
                SQLiteDatabase db = BoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언

                db.update(BoardContract.BoardEntry.TABLE_NAME, contentValues, BoardContract.BoardEntry._ID + "=" + mMemoId, null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문
                setResult(RESULT_OK);
                Intent intent = new Intent(BoardReWriteActivity.this, BoardActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                finish();
            }
        });//수정하기

        Button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = BoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                db.delete(BoardContract.BoardEntry.TABLE_NAME, BoardContract.BoardEntry._ID + "=" + mMemoId, null);//데이터삭제를 도와주는 db.delete
                setResult(RESULT_OK);//setResult()를 통해 돌려줄 결과를 저장
                Intent intent = new Intent(BoardReWriteActivity.this, BoardActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                finish();
            }
        });

        Button_reimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, 1);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.d("사진 실행 1", "사진 실행1");
            // Make sure the request was successful
            Uri selectmyphoto = selectmyphoto(data);
            /////////////////////////////////////////////////////////

//////////////////////////////////////////////////


            Photolist.add(selectmyphoto + "");
            Log.d("사진테스트 10", Photolist.get(0) + "");

            recreateArray = new JSONArray();
            for (int i = 0; i < Photolist.size(); i++) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    Log.d("사진테스트 12", Photolist.get(i) + "");
                    jsonObject.put("photo", Photolist.get(i));
                    recreateArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.d("사진테스트 11", recreateArray.toString());


            ContentValues contentValues = new ContentValues();
            contentValues.put(BoardContract.BoardEntry.COLUMN_NAME_PHOTOJSON, recreateArray.toString());
            SQLiteDatabase db = BoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
            db.update(BoardContract.BoardEntry.TABLE_NAME, contentValues, BoardContract.BoardEntry._ID + "=" + mMemoId, null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문



            onActivityResult(RESULT_OK,2,null);
            /////////////내용에 사진넣어주는거 바로 적용해야함
        } else if (resultCode == 2 && requestCode==RESULT_OK) {
            Toast.makeText(getApplicationContext(), "사진수정이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
            Log.d("사진테스트105","사진테스트");
            PhotoRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_rewritephoto);
            PhotoRecyclerView.setAdapter(reWritePhotoAdapter);

        } else if (requestCode == 3) {
            if (data != null) {
                Log.d("사진 실행 1", "사진 실행1");
                // Make sure the request was successful
                Uri selectmyphoto = selectmyphoto(data);
                /////////////////////////////////////////////////////////

//////////////////////////////////////////////////


                Photolist.set(mposition, selectmyphoto + "");
                Log.d("사진테스트 10", Photolist.get(0) + "");

                JSONArray photorecreateArray = new JSONArray();
                for (int i = 0; i < Photolist.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        Log.d("사진테스트 12", Photolist.get(i) + "");
                        jsonObject.put("photo", Photolist.get(i));
                        photorecreateArray.put(jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("사진테스트 11", photorecreateArray.toString());


                ContentValues contentValues = new ContentValues();

                contentValues.put(BoardContract.BoardEntry.COLUMN_NAME_PHOTOJSON, photorecreateArray.toString());
                SQLiteDatabase db = BoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언

                db.update(BoardContract.BoardEntry.TABLE_NAME, contentValues, BoardContract.BoardEntry._ID + "=" + mMemoId, null);
                db.close();



                onActivityResult(RESULT_OK,2,null);

            }
        }
        else {
            return;
        }
    }


    public Uri selectmyphoto(Intent data) {

        Uri photoUri = data.getData();//인텐트에 저장된값으로 스트림을 만들고
        Log.d("사진테스트24", photoUri + "");
        return photoUri;
    }

}

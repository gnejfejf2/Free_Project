package com.example.myaccount.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.CompanyBoardContract;
import com.example.myaccount.Contract.ItemList;
import com.example.myaccount.Helper.CompanyBoardDBHelper;
import com.example.myaccount.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.bootpay.model.Item;

public class ItemEditActivity extends AppCompatActivity {


    private EditText itemtitle_edit, itemcontent_edit, itemprice_edit;
    private ArrayList<ItemList> arrayitemLists;
    private ImageView item_ImageView;
    private Button Button_itemimage, Button_itemsetbutton;
    private JSONArray jsonitemList, editjArray;
    private Uri selectmyphoto = null;
    private ItemList edititemList;
    private String editphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyitemedit);


        itemtitle_edit = findViewById(R.id.itemtitle_edit);
        itemcontent_edit = findViewById(R.id.itemcontent_edit);
        itemprice_edit = findViewById(R.id.itemprice_edit);
        item_ImageView = findViewById(R.id.item_ImageView);
        Button_itemimage = findViewById(R.id.Button_itemimage);
        Button_itemsetbutton = findViewById(R.id.Button_itemsetbutton);
        /////////////////////////////////////////////////////////////

        Intent intent = getIntent();

        final Long boardnum = intent.getLongExtra("boardnum", -1);
        final String userID = intent.getStringExtra("userID");
        final String itemlist = intent.getStringExtra("list");
        final int position = intent.getIntExtra("position", -1);


        if (itemlist != null) {

            try {
                jsonitemList = new JSONArray(itemlist);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//스트링을 제이슨어레이로 바꿔준후
        } else {
            jsonitemList = new JSONArray();

        }

        try {
            JSONObject jsonObject = (JSONObject) jsonitemList.get(position);//배열 내에 들어갈 json
            String settitle = (String) jsonObject.get("title");
            String setcontent = (String) jsonObject.get("content");
            String setprice = (String) jsonObject.get("price");
            String setphoto = (String) jsonObject.get("photo");
            itemtitle_edit.setText(settitle);
            itemcontent_edit.setText(setcontent);
            itemprice_edit.setText(setprice);
            Uri uri = Uri.parse(setphoto);


            Glide.with(getApplicationContext()).load(uri).into(item_ImageView);

        } catch (JSONException e) {
            e.printStackTrace();
        }


////////////////////////////////////////////////사진추가
        Button_itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, 1);
            }
        });
////////////////////////////////////////////////////
        Button_itemsetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = itemtitle_edit.getText().toString();
                String contents = itemcontent_edit.getText().toString();
                String price = itemprice_edit.getText().toString();
                //////////오늘날자 가져오기///////////

                if (title.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (contents.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (price.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "금액을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {

                    arrayitemLists = new ArrayList<>();
                    if (jsonitemList != null) {

                        for (int i = 0; i < jsonitemList.length(); i++) {

                            try {
                                JSONObject jsonObject = (JSONObject) jsonitemList.get(i);//배열 내에 들어갈 json
                                String edittitle = (String) jsonObject.get("title");
                                String editcontent = (String) jsonObject.get("content");
                                String editprice = (String) jsonObject.get("price");
                                editphoto = (String) jsonObject.get("photo");
                                ItemList itemList = new ItemList(edittitle, editcontent, editprice, editphoto);
                                arrayitemLists.add(itemList);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if(selectmyphoto!=null) {

                        edititemList = new ItemList(title, contents, price, selectmyphoto + "");
                    }
                    else{
                        edititemList = new ItemList(title, contents, price, editphoto);
                    }


                    arrayitemLists.set(position, edititemList);

                    editjArray = new JSONArray();
                    try {
                        for (int i = 0; i < arrayitemLists.size(); i++)//배열
                        {
                            JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                            sObject.put("title", arrayitemLists.get(i).getTitle());
                            sObject.put("content", arrayitemLists.get(i).getContent());
                            sObject.put("price", arrayitemLists.get(i).getPrice());
                            sObject.put("photo", arrayitemLists.get(i).getPhoto());
                            editjArray.put(sObject);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.d("editjArray", editjArray + "");
                    Log.d("boardnum", boardnum + "");


                    ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
                    contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_ITEMLIST, editjArray + "");
                    SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                    db.update(CompanyBoardContract.ComponyBoardEntry.TABLE_NAME, contentValues, BoardContract.BoardEntry._ID + "=" + boardnum, null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문
                    Intent intent2 = new Intent(ItemEditActivity.this, CompanyBoardCheckActivity.class);
                    intent2.putExtra("userID", userID);
                    intent2.putExtra("id",boardnum);
                    startActivity(intent2);
                    finish();
                }
            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }
    /////////////////////////////////이미지가 변하면 바로 보여주기위해 온엑티비티 리절트

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
    if(data!=null) {
        selectmyphoto = selectmyphoto(data);
        /////////////////////////////////////////////////////////
        ImageView item_ImageView = findViewById(R.id.item_ImageView);
        Log.d("사진 실행 2", "사진 실행2");

        item_ImageView.setImageURI(selectmyphoto);
    }
    else
        return;;

    }


    public Uri selectmyphoto(Intent data) {

        Uri photoUri = data.getData();//인텐트에 저장된값으로 스트림을 만들고
        Log.d("사진테스트24", photoUri + "");

        return photoUri;
    }


}
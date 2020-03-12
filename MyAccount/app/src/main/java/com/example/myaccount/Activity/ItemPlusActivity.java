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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.CompanyBoardContract;
import com.example.myaccount.Contract.ItemList;
import com.example.myaccount.Helper.CompanyBoardDBHelper;
import com.example.myaccount.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ItemPlusActivity extends AppCompatActivity {



    private EditText itemtitle_edit, itemcontent_edit, itemprice_edit;

    private ImageView test;
    private Button Button_itemimage,Button_itemsetbutton;
    private ArrayList<ItemList> itemlist = new ArrayList<ItemList>();
    private JSONArray jsonitemList;
    private Uri selectmyphoto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyitemwrite);

        itemtitle_edit=findViewById(R.id.itemtitle_edit);
        itemcontent_edit=findViewById(R.id.itemcontent_edit);
        itemprice_edit=findViewById(R.id.itemprice_edit);
        Button_itemimage=findViewById(R.id.Button_itemimage);
        Button_itemsetbutton=findViewById(R.id.Button_itemsetbutton);
        /////////////////////////////////////////////////////////////

        Intent intent=getIntent();
        final String boardnum=intent.getStringExtra("boardnum");
        final String userID=intent.getStringExtra("userID");
        final String itemlist=intent.getStringExtra("itemlist");

        if(itemlist!=null) {

            try {
                jsonitemList = new JSONArray(itemlist);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//스트링을 제이슨어레이로 바꿔준후
        }
        else{
          jsonitemList=new JSONArray();
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
                String price= itemprice_edit.getText().toString();
                //////////오늘날자 가져오기///////////

                if (title.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (contents.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if (price.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "금액을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (selectmyphoto == null) {
                    Toast.makeText(getApplicationContext(), "대표이미지를 넣어주세요", Toast.LENGTH_SHORT).show();
                } else {
//아이템리스트에 있는 값들을 불러온다음에 어레이 리스트로 변경하고 어레이리스트에 내가 저장해야할값들 추가시켜주고 그걸 json으로 바꿔준후 아이템리스트에 저장시켜논다
//외부에서는 이제 출력을 할수있게 어레이 리스트를 어뎁터에 연결시켜준다.
////////////////////////////////////////어레이 리스트에 먼저 위에 내용들을 추가시켜야한다///
                    //그후에 제이슨으로 만들고

            /*        jsonitemList = new JSONArray();//배열이 필요할때
                    try {

                        for (int i = 0; i < itemlist.size(); i++) {
                            JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                            sObject.put("titel", itemlist.get(i).getTitle());
                            sObject.put("price", itemlist.get(i).getContent());
                            sObject.put("price", itemlist.get(i).getPrice());
                            sObject.put("photo", itemlist.get(i).getPhoto());
                            jsonitemList.put(sObject);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    /////////////////////////////////////////기존에있던것들

                    JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                    try {
                        sObject.put("title", title);
                        sObject.put("content", contents);
                        sObject.put("price", price);
                        sObject.put("photo", selectmyphoto+"");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonitemList.put(sObject);



                    ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
                    contentValues.put(CompanyBoardContract.ComponyBoardEntry.COLUMN_NAME_ITEMLIST, jsonitemList+"");

                    Log.d("이거뭐냐",jsonitemList+"");
                    SQLiteDatabase db = CompanyBoardDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                    db.update(CompanyBoardContract.ComponyBoardEntry.TABLE_NAME, contentValues, CompanyBoardContract.ComponyBoardEntry._ID + "=" + boardnum, null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문
                    setResult(RESULT_OK);//이게있어야 바로바로 변함
                    Intent intent2 = new Intent(ItemPlusActivity.this, CompanyBoardCheckActivity.class);

                    Long number= Long.valueOf(boardnum);
                    intent2.putExtra("userID",userID);
                    intent2.putExtra("id", number);

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
        Log.d("사진 실행 1", "사진 실행1");
        // Make sure the request was successful

        /////////////////////////////////////////////////////////
//////////////////////////////////////////////////
        selectmyphoto = selectmyphoto(data);
        /////////////////////////////////////////////////////////
        ImageView item_ImageView = findViewById(R.id.item_ImageView);
        Log.d("사진 실행 2", "사진 실행2");

        item_ImageView.setImageURI(selectmyphoto);




    }


    public Uri selectmyphoto(Intent data) {

        Uri photoUri = data.getData();//인텐트에 저장된값으로 스트림을 만들고
        Log.d("사진테스트24",photoUri+"");
        return photoUri;
    }


}
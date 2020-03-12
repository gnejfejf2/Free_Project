package com.example.myaccount.Activity;


import android.content.ContentValues;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;

import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import com.example.myaccount.Adapter.ShoppingbasketAdapter;
import com.example.myaccount.Contract.Shoppingbasketcontract;
import com.example.myaccount.Helper.ShoppingbasketDBHelper;
import com.example.myaccount.R;
import com.example.myaccount.Request.MoneyCheckRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShoppingbasketActivity extends AppCompatActivity {

    ShoppingbasketAdapter mAdapter;
    FloatingActionButton shoppingbasket_floating_buy, shoppingbasket_floating_delete;
    private String selectQuery = "SELECT  * FROM " + Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME;
    private String title;
    private int price;
    private ArrayList<Integer> removelist, itemremovelist, pricelist, numberlist;
    private ArrayList<String> titlelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingbasket);
        Intent intent = getIntent();
        final String useuserID = intent.getStringExtra("userID");

        SQLiteDatabase shoppingdb = ShoppingbasketDBHelper.getInstance(getApplicationContext()).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_ISSELECTED, false);
        contentValues.put(Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_BUYNUMBER, 1);

        shoppingdb.update(Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME, contentValues, Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_BUYUSERID + "=" + "'" + useuserID + "'", null);//데이터베이스에있는 값을 업데이트시켜주기위해 사용한 업데티느문
        shoppingbasket_floating_buy = findViewById(R.id.shoppingbasket_floating_buy);
        shoppingbasket_floating_delete = findViewById(R.id.shoppingbasket_floating_delete);


        ListView listView;

        listView = findViewById(R.id.shoppingbasket_listview);
        Cursor cursor = getShoppingbasketCursor();
        mAdapter = new ShoppingbasketAdapter(this, cursor, true);
        listView.setAdapter(mAdapter);


////////////////////////////////////////////////////////////////////////////////////////////////////////////
        shoppingbasket_floating_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemremovelist = new ArrayList<>();
                SQLiteDatabase db = ShoppingbasketDBHelper.getInstance(getApplicationContext()).getReadableDatabase();//데이터베이스에 쓰기위해 선언
                Cursor testcursor = db.rawQuery(selectQuery, null);

                if (testcursor.getCount() <= 0) {
                    Toast.makeText(getApplicationContext(), "선택된 값이 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    testcursor.moveToLast();
                    if (testcursor.getString(6).equals("true")) {

                        itemremovelist.add(testcursor.getInt(0));

                    }
                    while (testcursor.moveToPrevious()) {
                        if (testcursor.getString(6).equals("true")) {

                            itemremovelist.add(testcursor.getInt(0));

                        }
                    }

                    for (int i = 0; i < itemremovelist.size(); i++) {
                        int mMemoId = itemremovelist.get(i);

                        SQLiteDatabase writableDatabasedb = ShoppingbasketDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                        writableDatabasedb.delete(Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME, Shoppingbasketcontract.Shoppingbasketentry._ID + "=" + mMemoId, null);//아이디값받아와서 그위치에있는 아이템 삭제


                    }


                    onActivityResult(1000, 1000, null);


                }
            }
        });
//2: 품목
//4: 가격
//6: 트루펄스

        shoppingbasket_floating_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = new String();
                price = 0;
                SQLiteDatabase db = ShoppingbasketDBHelper.getInstance(getApplicationContext()).getReadableDatabase();//데이터베이스에 쓰기위해 선언


                removelist = new ArrayList<>();
                titlelist = new ArrayList<>();
                pricelist = new ArrayList<>();
                numberlist = new ArrayList<>();
                Cursor testcursor = db.rawQuery(selectQuery, null);
                if (testcursor.getCount() <= 0) {
                    Toast.makeText(getApplicationContext(), "선택된 값이 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    testcursor.moveToLast();
                    if (testcursor.getString(6).equals("true")) {


                        removelist.add(testcursor.getInt(0));
                        title = testcursor.getString(2)+ "상품"+ testcursor.getString(7) + "개";
                        titlelist.add(testcursor.getString(2));
                        price += (Integer.valueOf(testcursor.getString(1)) * Integer.valueOf(testcursor.getString(7)));
                        pricelist.add(Integer.valueOf(testcursor.getString(1)));
                        int buynumber = Integer.valueOf(testcursor.getString(7));
                        numberlist.add(buynumber);
                    }

                    while (testcursor.moveToPrevious()) {
                        if (testcursor.getString(6).equals("true")) {
                            removelist.add(testcursor.getInt(0));
                            title=title+"외 상품들";
                            titlelist.add(testcursor.getString(2));
                            price += (Integer.valueOf(testcursor.getString(1)) * Integer.valueOf(testcursor.getString(7)));
                            pricelist.add(Integer.valueOf(testcursor.getString(1)));
                            int buynumber2 = Integer.valueOf(testcursor.getString(7));
                            numberlist.add(buynumber2);

                        }

                    }


                    ///////////////////////////////결제페이지로 넘기기
                    Response.Listener<String> responseListner2 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                boolean success = jsonObject.getBoolean("success");
                                if (success) {//데이터찾기 실패
                                    String userMoney = String.valueOf(jsonObject.getInt("userMoney"));
                                    String itemprice = String.valueOf(price);
                                    String itemtitle = title;
                                    Intent buyintent = new Intent(ShoppingbasketActivity.this, ItemPaybillActivity.class);
                                    buyintent.putExtra("price", itemprice);
                                    buyintent.putExtra("title", itemtitle);
                                    buyintent.putExtra("userMoney", userMoney);
                                    buyintent.putExtra("userID", useuserID);
                                    buyintent.putIntegerArrayListExtra("removelist", removelist);
                                    buyintent.putStringArrayListExtra("titlelist", titlelist);
                                    buyintent.putIntegerArrayListExtra("pricelist", pricelist);
                                    buyintent.putIntegerArrayListExtra("numberlist", numberlist);
                                    startActivity(buyintent);
                                    finish();
                                } else {//데이터찾기 성공

                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    MoneyCheckRequest MoneyCheckRequest = new MoneyCheckRequest(useuserID, useuserID, responseListner2);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(MoneyCheckRequest);


                    return;
                    /////////////////////////////////////////////////////////////
                }
            }
        });


    }

    //체크박스를 입력을 하면 SQL테이블에 Shoppingbasketcontract.Shoppingbasketentry.COLUMN_NAME_ISSELECTED가 true나 널값 둘중에 하나가 들어가게되는데
    //만약 트루라면 가격과 데이터를 더해주고 널이라면 더해주지않는다
    //체크박스를 입력하면 트루가되고 입력을 취소하면 널값으로 변경한다
    //장바구니를 시작을 할때 모든값을 폴스로 한다
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1000) {
            ListView listView;

            listView = findViewById(R.id.shoppingbasket_listview);
            Cursor cursor = getShoppingbasketCursor();
            mAdapter = new ShoppingbasketAdapter(this, cursor, true);
            listView.setAdapter(mAdapter);

        }
    }

    private Cursor getShoppingbasketCursor() {
        ShoppingbasketDBHelper dbHelper = ShoppingbasketDBHelper.getInstance(this);
        return dbHelper.getReadableDatabase().query(Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME, null, null, null, null, null, Shoppingbasketcontract.Shoppingbasketentry._ID);
        //전체값을 가져오고 정렬을 함 날짜에따라 정렬할 예정임
    }
}

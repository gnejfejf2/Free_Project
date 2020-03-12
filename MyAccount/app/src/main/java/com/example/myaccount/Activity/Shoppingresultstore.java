package com.example.myaccount.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.example.myaccount.Adapter.BoardAdapter;
import com.example.myaccount.Adapter.ShoppingResultstoreAdapter;
import com.example.myaccount.Contract.BoardContract;
import com.example.myaccount.Contract.ShoppingContract;
import com.example.myaccount.Helper.BoardDBHelper;
import com.example.myaccount.Helper.ShoppingDBHelper;
import com.example.myaccount.R;

public class Shoppingresultstore extends AppCompatActivity {

    ShoppingResultstoreAdapter shoppingResultstoreAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingresultstore);

        Intent intent=getIntent();
        String userID=intent.getStringExtra("userID");

        ListView result_listView=findViewById(R.id.result_listView);
        Cursor cursor = getShoppingCursor();

        shoppingResultstoreAdapter = new ShoppingResultstoreAdapter(this, cursor, true);
        result_listView.setAdapter(shoppingResultstoreAdapter);
    }
    private Cursor getShoppingCursor() {
        ShoppingDBHelper dbHelper = ShoppingDBHelper.getInstance(this);
        return dbHelper.getReadableDatabase().query(ShoppingContract.ShoppingEntry.TABLE_NAME, null, null, null, null, null, null);
        //전체값을 가져오고 정렬을 함 날짜에따라 정렬할 예정임
    }
}

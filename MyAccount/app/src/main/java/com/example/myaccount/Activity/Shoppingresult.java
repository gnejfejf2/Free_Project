package com.example.myaccount.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myaccount.Adapter.CustomAdapter;
import com.example.myaccount.Adapter.ItemAdapter;
import com.example.myaccount.Adapter.ShoppingresultAdapter;
import com.example.myaccount.Contract.DdayContract;
import com.example.myaccount.Contract.ShoppingContract;
import com.example.myaccount.Contract.Shoppingresultcontract;
import com.example.myaccount.Helper.DdayDBHelper;
import com.example.myaccount.Helper.ShoppingDBHelper;
import com.example.myaccount.R;

import java.util.ArrayList;

public class Shoppingresult extends AppCompatActivity {
    private ArrayList<Integer> pricelist, numberlist;
    private ArrayList<String> titlelist;
    private ArrayList<Shoppingresultcontract> shoppingresultcontractArrayList;
    private ShoppingresultAdapter mAdapter;
    private Shoppingresultcontract shoppingresultcontract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingresult);
        TextView textView_result=findViewById(R.id.textView_result);
        Button button_shoppingend=findViewById(R.id.button_shoppingend);
        Button button_shoppingcheck=findViewById(R.id.button_shoppingcheck);
        Intent intent=getIntent();
        int money= intent.getIntExtra("money",-1);
        final String userID= intent.getStringExtra("userID");
        titlelist=intent.getStringArrayListExtra("titlelist");
        pricelist=intent.getIntegerArrayListExtra("pricelist");
        numberlist=intent.getIntegerArrayListExtra("numberlist");

        textView_result.setText(userID+"님 총 결제금액은 "+money+"원 입니다.");






        shoppingresultcontractArrayList=new ArrayList<>();
        for(int i=0;i<pricelist.size();i++) {

            shoppingresultcontract = new Shoppingresultcontract(titlelist.get(i), pricelist.get(i), numberlist.get(i),  pricelist.get(i)*numberlist.get(i));
            shoppingresultcontractArrayList.add(shoppingresultcontract);


            SQLiteDatabase db= ShoppingDBHelper.getInstance(getApplicationContext()).getWritableDatabase();

            ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
            contentValues.put(ShoppingContract.ShoppingEntry.COLUMN_NAME_BUYID, userID);
            contentValues.put(ShoppingContract.ShoppingEntry.COLUMN_NAME_ITEMTITLE, titlelist.get(i));
            contentValues.put(ShoppingContract.ShoppingEntry.COLUMN_NAME_ITEMPRICE, pricelist.get(i));
            contentValues.put(ShoppingContract.ShoppingEntry.COLUMN_NAME_ITEMCOUNTER, numberlist.get(i));

            db.insert(ShoppingContract.ShoppingEntry.TABLE_NAME, null, contentValues);//데이터베이스에 컨텐트벨류값을 넣어준다

        }


        RecyclerView recyclerview_shoppingresult = (RecyclerView) findViewById(R.id.recyclerview_shoppingresult);
        LinearLayoutManager LinearLayoutManager = new LinearLayoutManager(this);
        recyclerview_shoppingresult.setLayoutManager(LinearLayoutManager);
        mAdapter = new ShoppingresultAdapter(getApplicationContext(), shoppingresultcontractArrayList);
        recyclerview_shoppingresult.setAdapter(mAdapter);


        button_shoppingend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Shoppingresult.this,ShoppingbasketActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
                finish();
            }
        });

        button_shoppingcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Shoppingresult.this,Shoppingresultstore.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
                finish();
            }
        });



//////////////////////////////////////////////////////
    }
}

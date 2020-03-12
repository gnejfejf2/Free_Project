package com.example.myaccount.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myaccount.Helper.DBHelper;
import com.example.myaccount.R;


public class CheckActivity extends AppCompatActivity {


TextView TextView_ckeck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Button button_shoppingresult=findViewById(R.id.button_shoppingresult);

        Intent intent = getIntent();//인텐트 받아오고
        final String userID = intent.getStringExtra("userID");
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "MoneyBook.db", null, 1);
        TextView_ckeck=findViewById(R.id.TextView_check);
        TextView_ckeck.setText(dbHelper.getResult(userID));


        button_shoppingresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckActivity.this,Shoppingresultstore.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
                finish();

            }
        });
    }



}

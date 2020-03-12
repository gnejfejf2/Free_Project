package com.example.myaccount.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myaccount.Request.FindRequest;
import com.example.myaccount.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class FindActivity extends AppCompatActivity {

    private TextInputEditText find_ID, find_Name;
    private LinearLayout btn_find;

    protected void onCreate(Bundle savedInstanceState) {//엑티비티 시작하는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        find_ID = findViewById(R.id.find_ID);
        find_Name = findViewById(R.id.find_Name);
        btn_find = findViewById(R.id.btn_find);
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit text에 입력된 값을 가져오는것
                String userID = find_ID.getText().toString();
                String userName = find_Name.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                String userPassword = jsonObject.getString("userPassword");
                                Toast.makeText(getApplicationContext(), "회원님의 비밀번호는" + userPassword + "입니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FindActivity.this, StartActivity.class);
                                startActivity(intent);
                                finish();
                            } else {

                                return;
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "없는 사용자 정보입니다.", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }


                };
                //서버로 Volley를 요청해서 함
                FindRequest findRequest = new FindRequest(userID, userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindActivity.this);
                queue.add(findRequest);

            }
        });
    }
}
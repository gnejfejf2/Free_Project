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
import com.example.myaccount.R;
import com.example.myaccount.Request.RegisterRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateActivity extends AppCompatActivity {

    private TextInputEditText et_id, et_password, et_age, et_name;
    private LinearLayout btn_create;

    protected void onCreate(Bundle savedInstanceState) {//엑티비티 시작하는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        et_id = findViewById(R.id.et_id);
        et_password = findViewById(R.id.et_password);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        btn_create = findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit text에 입력된 값을 가져오는것
                String userID = et_id.getText().toString();
                String userPassword = et_password.getText().toString();
                int userAge = Integer.parseInt(et_age.getText().toString());
                String userName = et_name.getText().toString();
                int userMoney = 0;


                if (userID.length() != 0 && userPassword.length() != 0 && userAge !=
                0 && userName.length() != 0){

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getApplicationContext(), "회원등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CreateActivity.this, StartActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원등록에 실패하였습니다..", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "회원등록에 실패하였습니다..", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }


                    };
                    //서버로 Volley를 요청해서 함
                    RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, userMoney, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(CreateActivity.this);
                    queue.add(registerRequest);

                }
            }
        });
}
}
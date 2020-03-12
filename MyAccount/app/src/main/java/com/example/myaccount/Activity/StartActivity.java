package com.example.myaccount.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myaccount.R;
import com.example.myaccount.Request.LoginRequest;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import org.json.JSONObject;


public class StartActivity extends AppCompatActivity {

    private TextInputEditText TextInputEditText_Password, TextInputEditText_ID;
    private LinearLayout LinearLayout_Login;
    private TextView TextView_create,TextView_Findfunction;
    private static int REQUEST_SMS_RECEIVE = 1000;
    private static int REQUEST_READSTORAGE_RECEIVE = 1000;
    private static int REQUEST_WRITESTORAGE_RECEIVE = 1000;
    private static int REQUEST_INTERNET_RECEIVE = 1000;
    private String loginId, loginPwd;
    private CheckBox Checkbox_AutoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //////////////////////////////////////////

        int permissionCheck = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                // no permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_SMS_RECEIVE);
            } else {
                // already have permission
            }

        } else {
            // OS version is lower than marshmallow
        }
        int readpermissionCheck = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            readpermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (readpermissionCheck == PackageManager.PERMISSION_DENIED) {
                // no permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READSTORAGE_RECEIVE);
            } else {
                // already have permission
            }

        } else {
            // OS version is lower than marshmallow
        }
        int writepermissionCheck = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            writepermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (writepermissionCheck == PackageManager.PERMISSION_DENIED) {
                // no permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITESTORAGE_RECEIVE);
            } else {
                // already have permission
            }

        } else {
            // OS version is lower than marshmallow
        }
        int MANAGE_DOCUMENTSpermissionCheck = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MANAGE_DOCUMENTSpermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_DOCUMENTS);
            if (MANAGE_DOCUMENTSpermissionCheck == PackageManager.PERMISSION_DENIED) {
                // no permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_DOCUMENTS}, REQUEST_WRITESTORAGE_RECEIVE);
            } else {
                // already have permission
            }

        } else {
            // OS version is lower than marshmallow
        }
        ////////////////시작하면서 퍼미션 받아오기


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TextInputEditText_ID = findViewById(R.id.TextInputEditText_ID);
        TextInputEditText_Password = findViewById(R.id.TextInputEditText_Password);
        TextInputEditText_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);//비밀번호 별표시
        LinearLayout_Login = findViewById(R.id.LinearLayout_Login);
        TextView_create = findViewById(R.id.TextView_create);
        TextView_Findfunction = findViewById(R.id.TextView_Findfunction);
        Checkbox_AutoLogin = (CheckBox) findViewById(R.id.Checkbox_AutoLogin) ;
        Checkbox_AutoLogin.setChecked(true) ;///체크박스의 기본상태는 체크된상태
        ///////////////////////////////////////////////////////////////////
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
        // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
        loginId = auto.getString("inputId", null);//아이디를 가져옴
        loginPwd = auto.getString("inputPwd", null);//비밀번호를 가져옴
        ////////////////////////////////////////////////////////////////
        //회원가입버튼 클릭이벤트
        TextView_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createintent = new Intent(StartActivity.this, CreateActivity.class);
                startActivity(createintent);
            }
        });
        ///////////////////////////////////////////////////////////////
        TextView_Findfunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findintent = new Intent(StartActivity.this, FindActivity.class);
                startActivity(findintent);
            }
        });

        if (loginId != null && loginPwd != null) {

            String userID = loginId;
            String userPassword = loginPwd;
            Response.Listener<String> responseListner = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("여기는뭐냐그럼", response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {//로그인 성공
                            String userID = jsonObject.getString("userID");//유저아이디 비밀번호 가져오기 추후에 넘겨주기위해
                            String userPassword = jsonObject.getString("userPassword");
                            String userName = jsonObject.getString("userName");
                            String userMoney = jsonObject.getString("userMoney");
                            Toast.makeText(StartActivity.this, loginId + "님 자동로그인 입니다.", Toast.LENGTH_SHORT).show();
                            Intent startintent = new Intent(StartActivity.this, MainActivity.class);
                            startintent.putExtra("userID", userID);//인텐트에 유저아이디 비밀번호 이름 같이 넘기기
                            startintent.putExtra("userPassword", userPassword);
                            startintent.putExtra("userName", userName);//
                            startintent.putExtra("userMoney", userMoney);//
                            startActivity(startintent);
                            finish();
                        } else {//로그인실패
                            Toast.makeText(getApplicationContext(), "자동로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListner);
            RequestQueue queue = Volley.newRequestQueue(StartActivity.this);
            queue.add(loginRequest);

        } else if (loginId == null && loginPwd == null) {
            ////////////////////////////////////////////정상적인 로그인버튼 /////추후 수정해야함 자동로그인 저장코드 넣어야함
            LinearLayout_Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userID = TextInputEditText_ID.getText().toString();
                    String userPassword = TextInputEditText_Password.getText().toString();

                    Response.Listener<String> responseListner = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Log.d("여기는뭐냐그럼", response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {//로그인 성공
                                    ////////////////////////////체크박스에 선택시
                                    if (Checkbox_AutoLogin.isChecked()) {
                                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                        //auto의 loginId와 loginPwd에 값을 저장해 줍니다.
                                        SharedPreferences.Editor autoLogin = auto.edit();
                                        autoLogin.putString("inputId", TextInputEditText_ID.getText().toString());
                                        autoLogin.putString("inputPwd", TextInputEditText_Password.getText().toString());
                                        //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ
                                        autoLogin.commit(); //자동로그인 저장코드
                                    } else {
                                        // TODO : CheckBox is unchecked.
                                    }
                                    /////////////////////////////////////////////////////////////////////////


                                    String userID = jsonObject.getString("userID");//유저아이디 비밀번호 가져오기 추후에 넘겨주기위해
                                    String userPassword = jsonObject.getString("userPassword");
                                    String userName = jsonObject.getString("userName");
                                    String userMoney = jsonObject.getString("userMoney");
                                    Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent startintent = new Intent(StartActivity.this, MainActivity.class);
                                    startintent.putExtra("userID", userID);//인텐트에 유저아이디 비밀번호 이름 같이 넘기기
                                    startintent.putExtra("userPassword", userPassword);
                                    startintent.putExtra("userName", userName);//
                                    startintent.putExtra("userMoney", userMoney);//
                                    startActivity(startintent);
                                    finish();
                                } else {//로그인실패
                                    Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListner);
                    RequestQueue queue = Volley.newRequestQueue(StartActivity.this);
                    queue.add(loginRequest);
                }
            });
            ///////////////////////////////////////////////////////로그인버튼//////////////////////////////
        }
    }
}
package com.example.myaccount.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myaccount.Contract.UserData;
import com.example.myaccount.Request.FindRequest;
import com.example.myaccount.Request.MoneyCheckRequest;
import com.example.myaccount.Calendar.MyGridViewCalendar;
import com.example.myaccount.Contract.ProfilContract;
import com.example.myaccount.Helper.ProfilDBHelper;
import com.example.myaccount.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    public TextView TextView_Dday;
    private TextView TextView_Account;
    private Button btn_reset, btn_Account, btn_Shoppingbasket, btn_Board, btn_Check, Button_daysetting, Button_paybill, Button_Logout;
    private ImageView myphoto, couplephoto,alarmbutton;
    private byte[] myphotobyte, couplephotobyte;
    private String selectQuery = "SELECT  * FROM " + ProfilContract.ProfilEntry.TABLE_NAME;
    private String token;
    private FirebaseDatabase mFirebaseDatabase;//데이터베이스 만들기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmbutton=findViewById(R.id.alarmbutton);
        TextView_Account = findViewById(R.id.TextView_Account);
        btn_reset = findViewById(R.id.btn_reset);
        btn_Account = findViewById(R.id.btn_Account);
        btn_Shoppingbasket = findViewById(R.id.btn_Shoppingbasket);
        btn_Board = findViewById(R.id.btn_Board);
        btn_Check = findViewById(R.id.btn_Check);
        Button_Logout = findViewById(R.id.Button_Logout);
        TextView_Dday = findViewById(R.id.TextView_Dday);
        myphoto = (ImageView) findViewById(R.id.myphoto);
        couplephoto = (ImageView) findViewById(R.id.couplephoto);
        Button_daysetting = findViewById(R.id.Button_daysetting);
        Button_paybill = findViewById(R.id.Button_paybill);

        //////////////////////////////////////////////////////////////////////
        SQLiteDatabase db = ProfilDBHelper.getInstance(getApplicationContext()).getReadableDatabase();
        ;//데이터베이스에 읽기위해 선언//여기가 오류였네 쓰기위해로 만들엇어야햇는데 멍충이
        if (db.rawQuery(selectQuery, null) == null) {
            SQLiteDatabase db2 = ProfilDBHelper.getInstance(getApplicationContext()).getWritableDatabase();
            ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
            db2.insert(ProfilContract.ProfilEntry.TABLE_NAME, null, contentValues);//최초설정이였을때 넣어준다
            Drawable drawable = getResources().getDrawable(R.drawable.photo1);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            byte[] image = bitmapToByteArray(bitmap);
            contentValues.put(ProfilContract.ProfilEntry.COLUMN_NAME_DAY, "0");
            Log.d("이거실행 1", String.valueOf(image));
            contentValues.put(ProfilContract.ProfilEntry.COLUMN_NAME_MYPHOTO, image);
            Log.d("이거실행 2", String.valueOf(image));
            contentValues.put(ProfilContract.ProfilEntry.COLUMN_NAME_GIRLPHOTO, image);
            Log.d("이거실행 3", String.valueOf(contentValues));
            db2.insert(ProfilContract.ProfilEntry.TABLE_NAME, null, contentValues);
            Log.d("이거들어있냐", "1111");
        }
///////////////////////////////////////////////////////////////////////////////////
        SharedPreferences alarm = getSharedPreferences("alarm", Activity.MODE_PRIVATE);

        boolean test = alarm.getBoolean("test",true);//아이디를 가져옴
        if(test){

            alarmbutton.setImageResource(R.drawable.ic_alarm_on_black_24dp);

        }
        else{

            alarmbutton.setImageResource(R.drawable.ic_alarm_off_black_24dp);


        }


        ////////////////////////////////////////////////////
      /*else {

            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            String Dday = cursor.getString(1);
            myphotobyte = cursor.getBlob(2);
            couplephotobyte = cursor.getBlob(3);
            Bitmap mybitmap = byteArrayToBitmap(myphotobyte);
            TextView_Dday.setText(Dday);
            myphoto.setImageBitmap(mybitmap);
            Bitmap couplebitmap = byteArrayToBitmap(couplephotobyte);
            couplephoto.setImageBitmap(couplebitmap);
        }
      */
      //////////////////////////////////////////////////////////////////////////////
      FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        Intent intent = getIntent();//인텐트 받아오고
                        String userID = intent.getStringExtra("userID");
                        if (!task.isSuccessful()) {
                            Log.w("Fcm log", "getInstanceID failed", task.getException());
                            return;
                        }
                        token = task.getResult().getToken();
                        Log.d("Fcm Log", "FCM 토큰" + token);
                        mFirebaseDatabase = FirebaseDatabase.getInstance();//데이터베이스 인스텐스를 선언
                        UserData userData = new UserData();//유저데이터를 새로만들고
                        userData.userEmailID = userID;//그안에 유저아이디랑
                        userData.fcmToken = token;//그유저의 토큰값을 넣어주고
                        mFirebaseDatabase.getReference("users").child(userData.userEmailID).setValue(userData);//그걸 데이터베이스에 셋시켜준다



                    }
                });
        /////////////////////////////////////////////////////Button_Logout
////////////////////////////////////////로그아웃/////////////////////////////
        Button_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferences에 저장된 값들을 로그아웃 버튼을 누르면 삭제하기 위해
                //SharedPreferences를 불러옵니다. 메인에서 만든 이름으로
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //editor.clear()는 auto에 들어있는 모든 정보를 기기에서 지웁니다.
                editor.clear();
                editor.commit();
                Toast.makeText(MainActivity.this, "로그아웃.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
///////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////내프로필 사진 변경 코드/////////////////////////////////////////////////
        myphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        ////////////////////////커플  프로필 사진 변경 코드/////////////////////////////////////////
        couplephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });
        //////////////////////////////////////////////////////////////////
        Button_daysetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemolistActivity.class);
                startActivity(intent);
            }
        });//그리드뷰 캘린더랑 연동해서 day를 채운다
        ////////////////////////////////////////////////////////////////////////
        ////////////////////알람버튼////
        alarmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences alarm = getSharedPreferences("alarm", Activity.MODE_PRIVATE);

                boolean test = alarm.getBoolean("test",true);//아이디를 가져옴
                SharedPreferences.Editor alarmedit = alarm.edit();
                if(test) {//flase로 바꿔줘야함
                    //auto의 loginId와 loginPwd에 값을 저장해 줍니다.

                    alarmedit.putBoolean("test", false);

                    //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ
                    onActivityResult(4,4,null);
                    Toast.makeText(getApplicationContext(),"알람이 꺼졌습니다",Toast.LENGTH_SHORT).show();
                }
                //트로바꿔줘야함
                else{

                    //auto의 loginId와 loginPwd에 값을 저장해 줍니다.

                    alarmedit.putBoolean("test", true);

                    //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ
                    onActivityResult(3,3,null);
                     Toast.makeText(getApplicationContext(),"알람이 켜졌습니다",Toast.LENGTH_SHORT).show();

                }
                alarmedit.commit(); //자동로그인 저장코드

            }
        });





        ///////////////
        /////////////충전버튼////////////////////////////////////
        Button_paybill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();//인텐트 받아오고
                String userID = intent.getStringExtra("userID");
                String userName = intent.getStringExtra("userName");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {

                                String userID = jsonObject.getString("userID");
                                String userMoney = jsonObject.getString("userMoney");
                                Intent payintent = new Intent(MainActivity.this, PaybillActivity.class);
                                payintent.putExtra("userID", userID);//인텐트에 유저아이디 유저 금액 넘기기 추후에 2중으로 확인할예정
                                payintent.putExtra("userMoney", userMoney);//인텐트에 유저아이디 금액넘기기 최초금액 확인시켜주기위해
                                startActivity(payintent);

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
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(findRequest);

            }

        });
        ////////////////////////////////////////////////////////충전버튼////////////////////

        //////////////////////////////////////결제내역 확인 버튼///////////////////////////////////////////////////////////////////
        btn_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();//인텐트 받아오고
                String userID = intent.getStringExtra("userID");
                String userName = intent.getStringExtra("userName");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {

                                String userID = jsonObject.getString("userID");

                                Intent checkintent = new Intent(MainActivity.this, CheckActivity.class);
                                checkintent.putExtra("userID", userID);//인텐트에 유저아이디 비밀번호 이름 같이 넘기기
                                startActivity(checkintent);

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
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(findRequest);

            }
        });
        //////////////////////////////////////결제내역 확인 버튼///////////////////////////////////////////////////////////////////
        //////////////////////////////////////사랑 통장 잔액 확인버튼///////////////////////////////////////////////////////////////////
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();//인텐트 받아오고
                String userID = intent.getStringExtra("userID");
                String userName = intent.getStringExtra("userName");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {

                                String userMoney = jsonObject.getString("userMoney");
                                TextView_Account.setText("사랑 통장 잔액:" + userMoney);

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
                MoneyCheckRequest findRequest = new MoneyCheckRequest(userID, userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(findRequest);

            }
        });
        //////////////////////////////////////사랑 통장 잔액 확인버튼///////////////////////////////////////////////////////////////////
        ///////////////////////////////////내통장 가는 코드/////////////////////////////
        btn_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();//인텐트 받아오고
                String userID = intent.getStringExtra("userID");
                String userName = intent.getStringExtra("userName");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {

                                String userMoney = jsonObject.getString("userMoney");
                                String userID = jsonObject.getString("userID");//유저아이디 비밀번호 가져오기 추후에 넘겨주기위해
                                String userPassword = jsonObject.getString("userPassword");
                                String userName = jsonObject.getString("userName");
                                Intent Accountintent = new Intent(MainActivity.this, MainActivity.class);
                                Accountintent.putExtra("userID", userID);//인텐트에 유저아이디 비밀번호 이름 같이 넘기기
                                Accountintent.putExtra("userPassword", userPassword);
                                Accountintent.putExtra("userName", userName);//
                                Accountintent.putExtra("userMoney", userMoney);//
                                startActivity(Accountintent);
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
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(findRequest);

            }
        });
////////////////////////////////////////////////////////////내통장 가는 코드////////////////////////////

        //////////////////////////미구현 알람버튼/////////잠깐 쇼핑버튼으로 교체함
        btn_Shoppingbasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();//인텐트 받아오고
                String userID = intent.getStringExtra("userID");
                String userName = intent.getStringExtra("userName");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {


                                String userID = jsonObject.getString("userID");//유저아이디 비밀번호 가져오기 추후에 넘겨주기위해

                                Intent Shoppingbasketintent = new Intent(MainActivity.this, ShoppingbasketActivity.class);
                                Shoppingbasketintent.putExtra("userID", userID);//인텐트에 유저아이디 비밀번호 이름 같이 넘기기
                                startActivity(Shoppingbasketintent);
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
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(findRequest);

            }
        });
        //////////////////////////미구현 알람버튼
///////////////////////////////게시판 버튼///////////////////////////////////////
        btn_Board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();//인텐트 받아오고
                String userID = intent.getStringExtra("userID");
                String userName = intent.getStringExtra("userName");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {

                                String userMoney = jsonObject.getString("userMoney");
                                String userID = jsonObject.getString("userID");//유저아이디 비밀번호 가져오기 추후에 넘겨주기위해
                                String userPassword = jsonObject.getString("userPassword");
                                String userName = jsonObject.getString("userName");
                                Intent Boardintent = new Intent(MainActivity.this, BoardActivity.class);
                                Boardintent.putExtra("userID", userID);//인텐트에 유저아이디 비밀번호 이름 같이 넘기기
                                Boardintent.putExtra("userPassword", userPassword);
                                Boardintent.putExtra("userName", userName);//
                                Boardintent.putExtra("userMoney", userMoney);//
                                startActivity(Boardintent);
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
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(findRequest);

            }
        });
///////////////////////////////게시판 버튼///////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////
        TextView_Dday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resultrot = 2000;
                MyGridViewCalendar myGridViewCalendar = new MyGridViewCalendar();//선언
                myGridViewCalendar.setSelectedDate(new Date(), resultrot);//수정을 할때 필요함
                myGridViewCalendar.show(getSupportFragmentManager(), "grid_view_calendar");//그리고 보여주기
            }
        });
////////////////////////////////////그 뭐냐 이벤트를 메모할수있는 공간으로 가는 버튼


    }

    /////////////////////////////////이미지가 변하면 바로 보여주기위해 온엑티비티 리절트
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // Make sure the request was successful

            myphoto.setImageBitmap(selectmyphoto(data));
            //내사진 변경시

        } else if (requestCode == 2) { //리퀘스트코드로 구분할수있다 이걸 몰랐다.
            // Make sure the request was successful
            Log.d("이거실행된거맞냐", "하위");
            couplephoto.setImageBitmap(selectgirlphoto(data));
            //여자친구 사진 변경시
        }
        else if(requestCode==3){

            alarmbutton.setImageResource(R.drawable.ic_alarm_on_black_24dp);
        }
        else if(requestCode==4){

            alarmbutton.setImageResource(R.drawable.ic_alarm_off_black_24dp);
        }
    }

    ////비트맵을 바이트배열로 변경해주는 코드
    public byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    public Bitmap selectmyphoto(Intent data) {
        try {
            // 선택한 이미지에서 비트맵 생성

            InputStream in = getContentResolver().openInputStream(data.getData());//인텐트에 저장된값으로 스트림을 만들고
            Bitmap bitmap = BitmapFactory.decodeStream(in);//그 스트림으로 비트맵을 제작

            byte[] image = bitmapToByteArray(bitmap);

            ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하는값을 집어넣는다
            contentValues.put(ProfilContract.ProfilEntry.COLUMN_NAME_MYPHOTO, image);//내사진이라는 db에 사진을 넣어주고
            SQLiteDatabase db = ProfilDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언

            //특정위치 확인하시고!
            Cursor cursor = db.rawQuery(selectQuery, null);//커서로 일단 테이블 전체를 선언
            cursor.moveToFirst();

            if (myphotobyte == null) {//비어있다면 인서트
                db.insert(ProfilContract.ProfilEntry.TABLE_NAME, null, contentValues);
                in.close();
                myphoto.setImageBitmap(bitmap);
                Log.d("뭐가들었냐", "나는");
                return bitmap;
            } else {//애초에 있었다면 가지고있는 이미지를 업데이트 해주는 형식
                cursor.moveToFirst();//첫번째 줄로 이동시켜주기
                db.update(ProfilContract.ProfilEntry.TABLE_NAME, contentValues, ProfilContract.ProfilEntry._ID + "=" + 1, null);
                Log.d("이거실행이냐", "테스트중이다");
                in.close();
                // 이미지 표시
                myphoto.setImageBitmap(bitmap);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public Bitmap selectgirlphoto(Intent data) {//위와 동일한 코드 데이터를 넣어주는 위치만 달라졌다
        try {
            // 선택한 이미지에서 비트맵 생성

            InputStream in = getContentResolver().openInputStream(data.getData());//갤러리에 있는 사진을 가져오고
            Bitmap bitmap = BitmapFactory.decodeStream(in);

            byte[] image = bitmapToByteArray(bitmap);
            SQLiteDatabase db = ProfilDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언

            ContentValues contentValues = new ContentValues();//컨텐트벨류를 이용하여 db에 내가원하느값을 집어넣는다
            contentValues.put(ProfilContract.ProfilEntry.COLUMN_NAME_GIRLPHOTO, image);

            //특정위치 확인하시고!
            Cursor cursor = db.rawQuery(selectQuery, null);


            if (cursor == null) {//첫번째 열만 사용하기때문에 커서가 자체 즉 테이블 자체가 비어있어야만 한다 //이거때문에 한시간을썻냐!!!!
                db.insert(ProfilContract.ProfilEntry.TABLE_NAME, null, contentValues);//최초설정이였을때 넣어준다
                couplephoto.setImageBitmap(bitmap);
                Log.d("뭐가들었냐", "안녕");
                return bitmap;
            } else {//그이외의 값은 첫번째의 위치를 수정해주기 위해 이렇게 사용하였다!
                cursor.moveToFirst();
                db.update(ProfilContract.ProfilEntry.TABLE_NAME, contentValues, ProfilContract.ProfilEntry._ID + "=" + 1, null);
                Log.d("왜이거지", "안녕");
                in.close();
                // 이미지 표시
                couplephoto.setImageBitmap(bitmap);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }





}

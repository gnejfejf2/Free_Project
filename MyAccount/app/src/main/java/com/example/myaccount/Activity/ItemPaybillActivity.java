package com.example.myaccount.Activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myaccount.Contract.Shoppingbasketcontract;
import com.example.myaccount.Helper.ShoppingbasketDBHelper;
import com.example.myaccount.R;
import com.example.myaccount.Request.LoginRequest;
import com.example.myaccount.Request.MoneyCheckRequest;
import com.example.myaccount.Request.MoneyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;


public class ItemPaybillActivity extends AppCompatActivity {
    private int stuck = 10;
    private ArrayList<Integer> removelist, pricelist, numberlist;
    private ArrayList<String> titlelist;
    TextView ItemPaybill_checkmoney, ItemPaybill_money, ItemPaybill_title;
    private String userPassword, userID;
    Button ItemPaybill_Button_Phone, ItemPaybill_Button_KaKao, ItemPaybill_Button_Payco, ItemPaybill_Button_LoveMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itempaybill);
        ///////////////////////////전달된값 가져오기
        final Intent intent = getIntent();

        removelist = intent.getIntegerArrayListExtra("removelist");
        pricelist = intent.getIntegerArrayListExtra("pricelist");
        numberlist = intent.getIntegerArrayListExtra("numberlist");
        titlelist = intent.getStringArrayListExtra("titlelist");
        userID = intent.getStringExtra("userID");
        final String userMoney = intent.getStringExtra("userMoney");
        final String price = intent.getStringExtra("price");
        final String itemtitle = intent.getStringExtra("title");
        //////////////////////////////////////////////
        ItemPaybill_title = findViewById(R.id.ItemPaybill_title);
        ItemPaybill_checkmoney = findViewById(R.id.ItemPaybill_checkmoney);
        ItemPaybill_money = findViewById(R.id.ItemPaybill_money);
        ItemPaybill_Button_LoveMoney = findViewById(R.id.ItemPaybill_Button_LoveMoney);
        ItemPaybill_Button_Phone = findViewById(R.id.ItemPaybill_Button_Phone);
        ItemPaybill_Button_KaKao = findViewById(R.id.ItemPaybill_Button_KaKao);
        ItemPaybill_Button_Payco = findViewById(R.id.ItemPaybill_Button_Payco);

        ItemPaybill_title.setText("구매 상품 :" + itemtitle);
        ItemPaybill_checkmoney.setText("현재 잔액은 : " + userMoney + "원");//구해야함 직접
        ItemPaybill_money.setText("결제 금액 : " + price + "원");

        BootpayAnalytics.init(this, "59a4d326396fa607cbe75de5");
        //////////////////////현재 잔액 표시 완료

        ////////////////////////
        ItemPaybill_Button_LoveMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(ItemPaybillActivity.this);

                ad.setTitle("비밀번호를 입력해주세요");       // 제목 설정
                ad.setMessage("(로그인 비밀번호)");   // 내용 설정

// EditText 삽입하기
                final EditText et = new EditText(ItemPaybillActivity.this);
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ad.setView(et);

// 확인 버튼 설정
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        // Text 값 받아서 로그 남기기
                        userPassword = et.getText().toString();

                        Response.Listener<String> responseListner = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                final int chargemoney = Integer.valueOf(price);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) {//로그인 성공

                                        int userMoney = jsonObject.getInt("userMoney");

                                        final int resultmoney = userMoney - chargemoney;
                                        if (resultmoney >= 0) {
                                            Response.Listener<String> responseListner = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {

                                                        JSONObject jsonObject = new JSONObject(response);

                                                        boolean success = jsonObject.getBoolean("success");
                                                        if (success) {//충전완료


                                                            for (int i = 0; i < removelist.size(); i++) {
                                                                int mMemoId = removelist.get(i);

                                                                SQLiteDatabase db = ShoppingbasketDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                                                                db.delete(Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME, Shoppingbasketcontract.Shoppingbasketentry._ID + "=" + mMemoId, null);//아이디값받아와서 그위치에있는 아이템 삭제
                                                            }


                                                                Intent intent2 = new Intent(ItemPaybillActivity.this, Shoppingresult.class);
                                                                intent2.putExtra("money", chargemoney);
                                                                intent2.putExtra("userID", userID);
                                                                intent2.putIntegerArrayListExtra("pricelist", pricelist);
                                                                intent2.putIntegerArrayListExtra("numberlist", numberlist);
                                                                intent2.putStringArrayListExtra("titlelist", titlelist);
                                                                Log.d("테스트중이다신발1",pricelist.get(0)+"");
                                                                startActivity(intent2);
                                                                finish();


                                                        } else {//충전그만
                                                            return;
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };

                                            MoneyRequest MoneyRequest = new MoneyRequest(resultmoney, userID, responseListner);
                                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                            queue.add(MoneyRequest);
                                            Log.d("시작했냐", "시작해라");


                                        } else {
                                            Toast.makeText(getApplicationContext(), "금액이 부족합니다.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    } else {//로그인실패
                                        Toast.makeText(getApplicationContext(), "비밀번호를 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        Log.d("여기는?", userID + userPassword + "");

                        LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListner);
                        RequestQueue queue = Volley.newRequestQueue(ItemPaybillActivity.this);
                        queue.add(loginRequest);

                        dialog.dismiss();     //닫기
                        // Event
                    }
                });


// 취소 버튼 설정
                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

// 창 띄우기
                ad.show();


                //여기에 다이얼로그로 비밀번호를 입력해주세요를만들고 에디트 텍스트에있는값을


            }
        });


        /////////////////////////////////////////////////////////
        ItemPaybill_Button_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ItemPaybill_money.getText().toString().getBytes().length > 0) {
                    final int chargemoney = Integer.valueOf(price);

                    BootUser bootUser = new BootUser().setPhone("010-1234-5678");
                    BootExtra bootExtra = new BootExtra().setQuotas(new int[]{0, 2, 3});
                    Bootpay.init(getFragmentManager())
                            .setApplicationId("59a4d326396fa607cbe75de5") // 해당 프로젝트(안드로이드)의 application id 값
                            .setPG(PG.KCP) // 결제할 PG 사
                            .setMethod(Method.PHONE) // 결제수단
                            .setContext(getApplicationContext())
                            .setBootUser(bootUser)
                            .setBootExtra(bootExtra)
                            .setUX(UX.PG_DIALOG)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
                            .setName(itemtitle) // 결제할 상품명
                            .setOrderId("1234") // 결제 고유번호expire_month
                            .setPrice(chargemoney) // 결제할 금액
                            .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                                @Override
                                public void onConfirm(@Nullable String message) {

                                    if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                                    else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                                    Log.d("confirm", message);
                                }
                            })
                            .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                                @Override
                                public void onDone(@Nullable String message) {
                                    ///////////////////////////////////데이터저장하는코드
                                    Response.Listener<String> responseListner2 = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {

                                                JSONObject jsonObject = new JSONObject(response);

                                                boolean success = jsonObject.getBoolean("success");
                                                if (success) {//데이터찾기 실패
                                                    int userMoney = jsonObject.getInt("userMoney");

                                                    final int resultmoney = userMoney - chargemoney;
                                                    if (resultmoney >= 0) {
                                                        Response.Listener<String> responseListner = new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(response);
                                                                    boolean success = jsonObject.getBoolean("success");
                                                                    if (success) {//충전완료


                                                                        for (int i = 0; i < removelist.size(); i++) {
                                                                            int mMemoId = removelist.get(i);

                                                                            SQLiteDatabase db = ShoppingbasketDBHelper.getInstance(getApplicationContext()).getWritableDatabase();//데이터베이스에 쓰기위해 선언
                                                                            db.delete(Shoppingbasketcontract.Shoppingbasketentry.TABLE_NAME, Shoppingbasketcontract.Shoppingbasketentry._ID + "=" + mMemoId, null);//아이디값받아와서 그위치에있는 아이템 삭제
                                                                        }

                                                                        String money = String.valueOf(resultmoney);
                                                                        Toast.makeText(getApplicationContext(), chargemoney + "원 결제되었습니다.", Toast.LENGTH_SHORT).show();
                                                                        Toast.makeText(getApplicationContext(), itemtitle + "상품이 구매되었습니다.", Toast.LENGTH_SHORT).show();
                                                                        Intent intent2 = new Intent(ItemPaybillActivity.this, ShoppingbasketActivity.class);
                                                                        intent2.putExtra("money", money);
                                                                        intent2.putExtra("userID", userID);
                                                                        startActivity(intent2);

                                                                    } else {//충전그만
                                                                        return;
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        };

                                                        MoneyRequest MoneyRequest = new MoneyRequest(resultmoney, userID, responseListner);
                                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                        queue.add(MoneyRequest);
                                                        Log.d("시작했냐", "시작해라");


                                                    } else {
                                                        return;
                                                    }


                                                } else {//데이터찾기 성공

                                                    return;
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                                    MoneyCheckRequest MoneyCheckRequest = new MoneyCheckRequest(userID, userID, responseListner2);
                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                    queue.add(MoneyCheckRequest);
                                    Log.d("시작했냐", "시작해라");

                                    /////////////////////db를 이용해서 문자를 수신하면 자동으로 저장시킨다 데이터베이스에 이거만들었따!!
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                }
                            })
                            .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                                @Override
                                public void onReady(@Nullable String message) {
                                    Log.d("ready", message);
                                }
                            })
                            .onCancel(new CancelListener() { // 결제 취소시 호출
                                @Override
                                public void onCancel(@Nullable String message) {

                                    Log.d("cancel", message);
                                }
                            })
                            .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                                @Override
                                public void onError(@Nullable String message) {
                                    Log.d("error", message);
                                }
                            })
                            .onClose(
                                    new CloseListener() { //결제창이 닫힐때 실행되는 부분
                                        @Override
                                        public void onClose(String message) {
                                            Log.d("close", "close");
                                        }
                                    })
                            .request();
                } else {
                    Toast.makeText(getApplicationContext(), "잔액이 부족합니다..", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });
/////////////////////////////////////////////////////////////문자결제
/////////////////////////////////////////////////////////////카카오결제
        ItemPaybill_Button_KaKao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////
                if (ItemPaybill_money.getText().toString().getBytes().length > 0) {
                    final int chargemoney = Integer.valueOf(price);
                    BootUser bootUser = new BootUser().setPhone("010-1234-5678");
                    BootExtra bootExtra = new BootExtra().setQuotas(new int[]{0, 2, 3});
                    Bootpay.init(getFragmentManager())
                            .setApplicationId("59a4d326396fa607cbe75de5") // 해당 프로젝트(안드로이드)의 application id 값
                            .setPG(PG.KAKAO) // 결제할 PG 사
                            .setMethod(Method.EASY) // 결제수단
                            .setContext(getApplicationContext())
                            .setBootUser(bootUser)
                            .setBootExtra(bootExtra)
                            .setUX(UX.PG_DIALOG)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
                            .setName(itemtitle) // 결제할 상품명
                            .setOrderId("1234") // 결제 고유번호expire_month
                            .setPrice(chargemoney) // 결제할 금액
                            .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                                @Override
                                public void onConfirm(@Nullable String message) {

                                    if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                                    else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                                    Log.d("confirm", message);
                                }
                            })
                            .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                                @Override
                                public void onDone(@Nullable String message) {
                                    ///////////////////////////////////데이터저장하는코드
                                    Response.Listener<String> responseListner2 = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                Log.d("뭐들어있냐", response);
                                                JSONObject jsonObject = new JSONObject(response);
                                                Log.d("시작했냐", "시작해라");
                                                boolean success = jsonObject.getBoolean("success");
                                                if (success) {//데이터찾기 실패
                                                    int userMoney = jsonObject.getInt("userMoney");

                                                    int resultmoney = userMoney + chargemoney;
                                                    if (resultmoney >= 0) {
                                                        Response.Listener<String> responseListner = new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    Log.d("뭐들어있냐", response);
                                                                    JSONObject jsonObject = new JSONObject(response);
                                                                    Log.d("시작했냐", "시작해라");
                                                                    boolean success = jsonObject.getBoolean("success");
                                                                    if (success) {//충전완료

                                                                        Toast.makeText(getApplicationContext(), chargemoney + "원 충전 되었습니다.", Toast.LENGTH_SHORT).show();

                                                                    } else {//충전그만
                                                                        return;
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        };

                                                        MoneyRequest MoneyRequest = new MoneyRequest(resultmoney, userID, responseListner);
                                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                        queue.add(MoneyRequest);
                                                        Log.d("시작했냐", "시작해라");


                                                    } else {
                                                        return;
                                                    }


                                                } else {//데이터찾기 성공

                                                    return;
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                                    MoneyCheckRequest MoneyCheckRequest = new MoneyCheckRequest(userID, userID, responseListner2);
                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                    queue.add(MoneyCheckRequest);
                                    Log.d("시작했냐", "시작해라");

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                }
                            })
                            .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                                @Override
                                public void onReady(@Nullable String message) {
                                    Log.d("ready", message);
                                }
                            })
                            .onCancel(new CancelListener() { // 결제 취소시 호출
                                @Override
                                public void onCancel(@Nullable String message) {

                                    Log.d("cancel", message);
                                }
                            })
                            .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                                @Override
                                public void onError(@Nullable String message) {
                                    Log.d("error", message);
                                }
                            })
                            .onClose(
                                    new CloseListener() { //결제창이 닫힐때 실행되는 부분
                                        @Override
                                        public void onClose(String message) {
                                            Log.d("close", "close");
                                        }
                                    })
                            .request();
                } else {
                    Toast.makeText(getApplicationContext(), "충전 금액을 입력해주세요.", Toast.LENGTH_SHORT).show();

                }


            }
        });
/////////////////////////////////////////////////////////////카카오결제
/////////////////////////////////////////////////////////////페이코결제
        ItemPaybill_Button_Payco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 결제호출
                if (ItemPaybill_money.getText().toString().getBytes().length > 0) {
                    final int chargemoney = Integer.valueOf(price);
                    BootUser bootUser = new BootUser().setPhone("010-1234-5678");
                    BootExtra bootExtra = new BootExtra().setQuotas(new int[]{0, 2, 3});
                    Bootpay.init(getFragmentManager())
                            .setApplicationId("59a4d326396fa607cbe75de5") // 해당 프로젝트(안드로이드)의 application id 값
                            .setPG(PG.PAYCO) // 결제할 PG 사
                            .setMethod(Method.EASY) // 결제수단
                            .setContext(getApplicationContext())
                            .setBootUser(bootUser)
                            .setBootExtra(bootExtra)
                            .setUX(UX.PG_DIALOG)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
                            .setName(itemtitle) // 결제할 상품명
                            .setOrderId("1234") // 결제 고유번호expire_month
                            .setPrice(chargemoney) // 결제할 금액
                            .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                                @Override
                                public void onConfirm(@Nullable String message) {

                                    if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                                    else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                                    Log.d("confirm", message);
                                }
                            })
                            .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                                @Override
                                public void onDone(@Nullable String message) {
                                    ///////////////////////////////////데이터저장하는코드
                                    Response.Listener<String> responseListner2 = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                Log.d("뭐들어있냐", response);
                                                JSONObject jsonObject = new JSONObject(response);
                                                Log.d("시작했냐", "시작해라");
                                                boolean success = jsonObject.getBoolean("success");
                                                if (success) {//데이터찾기 실패
                                                    int userMoney = jsonObject.getInt("userMoney");

                                                    int resultmoney = userMoney - chargemoney;
                                                    if (resultmoney >= 0) {
                                                        Response.Listener<String> responseListner = new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    Log.d("뭐들어있냐", response);
                                                                    JSONObject jsonObject = new JSONObject(response);
                                                                    Log.d("시작했냐", "시작해라");
                                                                    boolean success = jsonObject.getBoolean("success");
                                                                    if (success) {//충전완료

                                                                        Toast.makeText(getApplicationContext(), chargemoney + "원 충전 되었습니다.", Toast.LENGTH_SHORT).show();

                                                                    } else {//충전그만
                                                                        return;
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        };

                                                        MoneyRequest MoneyRequest = new MoneyRequest(resultmoney, userID, responseListner);
                                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                        queue.add(MoneyRequest);


                                                    } else {
                                                        return;
                                                    }


                                                } else {//데이터찾기 성공

                                                    return;
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                                    MoneyCheckRequest MoneyCheckRequest = new MoneyCheckRequest(userID, userID, responseListner2);
                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                    queue.add(MoneyCheckRequest);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                }
                            })
                            .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                                @Override
                                public void onReady(@Nullable String message) {
                                    Log.d("ready", message);
                                }
                            })
                            .onCancel(new CancelListener() { // 결제 취소시 호출
                                @Override
                                public void onCancel(@Nullable String message) {

                                    Log.d("cancel", message);
                                }
                            })
                            .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                                @Override
                                public void onError(@Nullable String message) {
                                    Log.d("error", message);
                                }
                            })
                            .onClose(
                                    new CloseListener() { //결제창이 닫힐때 실행되는 부분
                                        @Override
                                        public void onClose(String message) {
                                            Log.d("close", "close");
                                        }
                                    })
                            .request();
                } else {
                    Toast.makeText(getApplicationContext(), "충전 금액을 입력해주세요.", Toast.LENGTH_SHORT).show();

                }
            }
        });
/////////////////////////////////////////////////////////////페이코결제

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {

            String money = data.getStringExtra("money");
            Log.d("스타트", "스타트");
            ItemPaybill_checkmoney.setText("현재 잔액은 : " + money + "원");
        }
    }
}

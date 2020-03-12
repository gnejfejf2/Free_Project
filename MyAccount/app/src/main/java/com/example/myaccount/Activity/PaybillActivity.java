package com.example.myaccount.Activity;


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.View;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.toolbox.Volley;
        import com.example.myaccount.R;
        import com.example.myaccount.Request.MoneyCheckRequest;
        import com.example.myaccount.Request.MoneyRequest;

        import org.json.JSONException;
        import org.json.JSONObject;

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


public class PaybillActivity extends AppCompatActivity {
    private int stuck = 10;
    TextView Paybill_checkmoney;
    EditText Paybill_money;
    Button Button_Phone, Button_KaKao, Button_Payco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paybill);
        ///////////////////////////전달된값 가져오기
        final Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        String userMoney = intent.getStringExtra("userMoney");
        //////////////////////////////////////////////
        Paybill_checkmoney = findViewById(R.id.Paybill_checkmoney);
        Paybill_money = findViewById(R.id.Paybill_money);
        Button_Phone = findViewById(R.id.Button_Phone);
        Button_KaKao = findViewById(R.id.Button_KaKao);
        Button_Payco = findViewById(R.id.Button_Payco);
        Paybill_checkmoney.setText("현재 잔액은 : " + userMoney + "원");
        BootpayAnalytics.init(this, "59a4d326396fa607cbe75de5");
        //////////////////////현재 잔액 표시 완료


        ////////////////////////
        Button_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Paybill_money.getText().toString().getBytes().length > 0) {
                    final int chargemoney = Integer.valueOf(Paybill_money.getText().toString());

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
                            .setName("사랑머니") // 결제할 상품명
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

                                                    final int resultmoney = userMoney + chargemoney;
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
                                                                        String money= String.valueOf(resultmoney);
                                                                        Toast.makeText(getApplicationContext(), chargemoney + "원 충전 되었습니다.", Toast.LENGTH_SHORT).show();
                                                                        Intent intent2=new Intent();
                                                                        intent2.putExtra("money",money);
                                                                        Log.d("넘겨지는돈",money);
                                                                        setResult(RESULT_OK);//리절트 ok를 셋팅
                                                                        onActivityResult(1000, RESULT_OK, intent2);//바로바로 실행시켜주기위해

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
                } else{
                    Toast.makeText(getApplicationContext(), "충전 금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });
/////////////////////////////////////////////////////////////문자결제
/////////////////////////////////////////////////////////////카카오결제
        Button_KaKao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 결제호출

                if (Paybill_money.getText().toString().getBytes().length > 0) {
                    final int chargemoney = Integer.valueOf(Paybill_money.getText().toString());
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
                            .setName("사랑머니") // 결제할 상품명
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
        Button_Payco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 결제호출
                if (Paybill_money.getText().toString().getBytes().length > 0) {
                    final int chargemoney = Integer.valueOf(Paybill_money.getText().toString());
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
                            .setName("사랑머니") // 결제할 상품명
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
/////////////////////////////////////////////////////////////페이코결제
        Paybill_money.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Paybill_money.getWindowToken(), 0);    //hide keyboard
                    return true;
                }
                return false;
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {

            String money=data.getStringExtra("money");
            Log.d("스타트","스타트");
            Paybill_checkmoney.setText("현재 잔액은 : " + money + "원");
        }
    }
}

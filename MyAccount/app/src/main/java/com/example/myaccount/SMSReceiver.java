package com.example.myaccount;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myaccount.Helper.DBHelper;
import com.example.myaccount.Request.MoneyCheckRequest;
import com.example.myaccount.Request.MoneyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class SMSReceiver extends BroadcastReceiver {


    private static final String TAG = "SmsReceiver";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public void onReceive (Context context, Intent intent) {
        ////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////
        // sms가 오면 onReceive() 가 호출된다. 여기에 처리하는 코드 작성하면 된다.
        // Log.d(T326AG, "onReceive() 호출됨.");
        Bundle bundle = intent.getExtras();
        final Context mcontext=context;

        // parseSmsMessage() 메서드의 코드들은 SMS문자의 내용을 뽑아내는 정형화된 코드이다.
        SmsMessage[] messages = parseSmsMessage(bundle);


        if (messages.length > 0) {
            // 문자메세지에서 송신자와 관련된 내용을 뽑아낸다.
            String sender = messages[0].getOriginatingAddress();
            int testsender = Integer.parseInt(sender.replaceAll("[^0-9]", ""));//그중에서 숫자만가져온다
            Log.d(TAG, "sender: " + sender);

            if (testsender == 15889955) {
                Paybillcheck paybillcheck=new Paybillcheck(mcontext,messages);
                paybillcheck.setDaemon(true);
                paybillcheck.start();
            } else {
                return;
            }


        }

    }

    class Paybillcheck extends Thread{

        private Context context=null;
        private SmsMessage[] msmsMessages=null;
        public Paybillcheck(Context mcontext, SmsMessage[] messages) {
        context=mcontext;
        msmsMessages=messages;
        }
        @Override
        public void run(){
            String contents = msmsMessages[0].getMessageBody().toString();
            Log.d(TAG, "contents: " + contents);

            final DBHelper dbHelper = new DBHelper(context.getApplicationContext(), "MoneyBook.db", null, 1);

            // 인텐트에서 전달된 데이터를 추출하여
            String string = contents;
            String[] change_target = string.split("\\n");//특정 행만가져오기위해개행
            int paymoney = Integer.parseInt(change_target[3].replaceAll("[^0-9]", ""));//문자의 4번째줄을 가져온다
            //특정줄에서 숫자만 추출해온다
            String date = change_target[4];
            final String item = change_target[0];
            final int price = paymoney;
            String place = change_target[5];
            dbHelper.insert(date, item, price, place);

            Log.d("시작했냐", "시작해라");
///////////////////////////////////////////////////////////////
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
                            int resultmoney = userMoney - price;
                            if (resultmoney >= 0) {
                                Response.Listener<String> responseListner = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.d("뭐들어있냐", response);
                                            JSONObject jsonObject = new JSONObject(response);
                                            Log.d("시작했냐", "시작해라");
                                            boolean success = jsonObject.getBoolean("success");
                                            if (success) {//로그인 성공


                                            } else {//로그인실패

                                                return;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                MoneyRequest MoneyRequest = new MoneyRequest(resultmoney, item, responseListner);
                                RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
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

            MoneyCheckRequest MoneyCheckRequest = new MoneyCheckRequest(item, item, responseListner2);
            RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
            queue.add(MoneyCheckRequest);
            Log.d("시작했냐", "시작해라");

            /////////////////////db를 이용해서 문자를 수신하면 자동으로 저장시킨다 데이터베이스에 이거만들었따!!
        }


    }



    // 정형화된 코드. 그냥 가져다 쓰면 된다parseSmsMessage.
    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for (int i = 0; i < objs.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }

        }
        return messages;




    }





}

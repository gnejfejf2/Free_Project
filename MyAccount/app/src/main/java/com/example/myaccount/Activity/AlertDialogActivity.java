package com.example.myaccount.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.myaccount.R;


public class AlertDialogActivity extends Activity {


    private String test;
    private Long messagedata;

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent intent=getIntent();

        messagedata = Long.valueOf(intent.getStringExtra("messagedata"));


        setContentView(R.layout.alertdialog);


        TextView notitext = (TextView) findViewById(R.id.notitext);

        notitext.setText("알람이 왔습니다.");


        Button button_cancel = (Button) findViewById(R.id.button_cancel);


        button_cancel.setOnClickListener(new cancleOnClickListener());

        Button button_alertckeck=(Button) findViewById(R.id.button_alertckeck);
        button_alertckeck.setOnClickListener(new ckeckOnClickListener());
    }

    private class cancleOnClickListener implements OnClickListener {



        public void onClick(View v) {

            finish();


        }

    }


    private class ckeckOnClickListener implements OnClickListener {


        public void onClick(View v) {
           Intent intent=new Intent(AlertDialogActivity.this,AlertBoard.class);
           intent.putExtra("messagedata",messagedata);
           startActivity(intent);
           finish();
        }

    }

}

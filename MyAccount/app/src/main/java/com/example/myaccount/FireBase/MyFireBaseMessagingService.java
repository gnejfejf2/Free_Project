package com.example.myaccount.FireBase;


import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.myaccount.Activity.AlertDialogActivity;
import com.example.myaccount.Activity.MainActivity;
import com.example.myaccount.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFireBaseMessagingService extends FirebaseMessagingService {



    @Override

    public void onNewToken(String token) {
        Log.d("FCM Log", "Refreshed token:" + token);

    }

    ////////////////////fcm 토큰이 오면 만들어지는것 위에 노티페이션이 오게된다. 여기서 이제 추가로 게시판 고유넘버,Boardid를 입력을 받은후 다이얼로그로만들어서 알람이오면 그 게시물로 가게 만들면된다.
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        SharedPreferences alarm = getSharedPreferences("alarm", Activity.MODE_PRIVATE);
        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
        // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
        boolean test = alarm.getBoolean("test", true);//아이디를 가져옴


        if (test) {
            if (remoteMessage.getNotification() != null) { //포그라운드
                sendNotification(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"), remoteMessage.getData().get("test"));

            } else if (remoteMessage.getData().size() > 0) { //백그라운드
                sendNotification(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"), remoteMessage.getData().get("test"));


            }
        }
        else return;
    }






        private void sendNotification(String messageBody, String messageTitle,String messagedata)  {
            //////////////////////////// 포그라운드 및 백그라운드 푸시알림 처리 ////////////////////////////
            Intent popupIntent = new Intent(getApplicationContext(), AlertDialogActivity.class);
            popupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            popupIntent.putExtra("messagedata", messagedata);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, popupIntent, PendingIntent.FLAG_ONE_SHOT);
            String channelID = "Channel ID";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("데이트 통장")
                            .setContentText("알람이 왔습니다.")
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String channelName = "Channel Name";
                NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }


            notificationManager.notify(0, notificationBuilder.build());


            PendingIntent pie = PendingIntent.getActivity(getApplicationContext(), 0, popupIntent, PendingIntent.FLAG_ONE_SHOT);

            try {
                pie.send();

            } catch (PendingIntent.CanceledException e) {


            }
            //////////////////////////// 포그라운드 및 백그라운드 푸시알림 처리 ////////////////////////////
        }

}

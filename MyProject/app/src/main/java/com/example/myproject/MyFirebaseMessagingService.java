package com.example.myproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

        //푸시 알림 설정
        private String title = "";
        private String name = "";
        private String body = "";
        private String color = "";

        @Override
        public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);

            //푸시알림 메세지 분기
            //putData를 사용했을때 data가져오기
            if(remoteMessage.getData().size() > 0){
                title = remoteMessage.getData().get("title");
                name = remoteMessage.getData().get("name");
                body = remoteMessage.getData().get("body");
                color = remoteMessage.getData().get("color");

                if(true){
                    scheduleJob();
                }else{
                    hadleNow();
                }
            }

            //Notification 사용했을때 data 가져오기
            if(remoteMessage.getNotification() != null){
                Log.d("메세지", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }
            sendNotification();
        }

        @Override
        public void onNewToken(@NonNull String token) {
            super.onNewToken(token);
            sendRegistrationToServer(token);
        }

        private void scheduleJob() {
//        Constraints constraints = new Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.UNMETERED)
//                .setRequiresCharging(true)
//                .build();
//
//        PeriodicWorkRequest work = new PeriodicWorkRequest
//                .Builder(AteamWorker.class,15, TimeUnit.MINUTES)
//                .setConstraints(constraints)
//                .setBackoffCriteria(BackoffPolicy.LINEAR,
//                        OneTimeWorkRequest.MAX_BACKOFF_MILLIS,
//                        TimeUnit.MINUTES)
//                .build();
//        WorkManager.getInstance(this).enqueue(work);
        }

        private void hadleNow() {

        }

        private void sendRegistrationToServer(String token) {
            //앱 서버에 토큰을 보내려면 이 메서드를 구현한다.
        }

        private void sendNotification() {
            ////////
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2000 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            String channelId = "튜터스";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle(name)
                            .setContentText(body)
                            .setColor(Color.parseColor(color))
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(2000 /* ID of notification */, notificationBuilder.build());
        }

}

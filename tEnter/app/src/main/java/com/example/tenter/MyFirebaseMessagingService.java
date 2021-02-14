package com.example.tenter;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("tokenn", "Refreshed token: " + s);
        MyProperties.getInstance().fbasekey=s;
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getData() !=null)
        {
            sendnotification(remoteMessage);

        }
    }

    @SuppressLint("WrongConstant")
    private void sendnotification(RemoteMessage remoteMessage) {
        Map<String,String> data= remoteMessage.getData();

        String title=data.get("title");
        String content=data.get("content");

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID="sorusistemi";

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                "sorusistemi Notifikasyon",
                NotificationManager.IMPORTANCE_MAX);

            notificationChannel.setDescription("sorusistemi Notifikasyon Kanali");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);


        }
        NotificationCompat.Builder notiicationBuilder = new NotificationCompat.Builder(
                this,NOTIFICATION_CHANNEL_ID);
        notiicationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker("sorusistemi")
                .setContentTitle(title)
                .setContentText(content)
                .setContentInfo("info");

        notificationManager.notify(1,notiicationBuilder.build());
    }
}

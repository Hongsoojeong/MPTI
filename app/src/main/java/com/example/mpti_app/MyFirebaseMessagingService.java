package com.example.mpti_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.nio.channels.Channel;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService
{
    private Bitmap bmp = null;

@Override
    public  void onNewToken(@NonNull String s){
    super.onNewToken(s);
    Log.d("hi","Token : "+s);
}

@RequiresApi(api = Build.VERSION_CODES.O)
@Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage){
    super.onMessageReceived(remoteMessage);
    if (remoteMessage.getNotification() !=null){
        sendNotification(remoteMessage.getNotification());
    }
}

@RequiresApi(api = Build.VERSION_CODES.O)
private  void sendNotification(RemoteMessage.Notification notification){
    Intent notificationIntent = new Intent ( this, LoginActivity.class);
    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    NotificationCompat.Builder builder = getNotificationBuilder(notificationManager, "channel id", "첫번째 채널입니다");

    builder.setContentTitle(notification.getTitle())
            .setContentText(notification.getBody())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);
}

@RequiresApi(api = Build.VERSION_CODES.O)
protected  NotificationCompat.Builder getNotificationBuilder (NotificationManager notificationManager, String channelId, CharSequence channelName){
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);

        notificationManager.createNotificationChannel(channel);
        builder.setSmallIcon(R.drawable.logo);
        return builder;
    } else {
        builder.setSmallIcon(R.drawable.logo);
        return builder;
    }
}

}
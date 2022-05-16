package com.example.finish1m.Data.Other;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.finish1m.Domain.Interfaces.NotificationRepository;

public class NotificationRepositoryImpl implements NotificationRepository {

    private Context context;

    public NotificationRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void sendNotification(String title, String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent=new Intent();
            String CHANNEL_ID="MYCHANNEL";
            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_LOW);
            PendingIntent pendingIntent= PendingIntent.getActivity(context,1,intent,0);
            Notification notification=new Notification.Builder(context,CHANNEL_ID)
                    .setContentText(title)
                    .setContentTitle(message)
                    .setContentIntent(pendingIntent)
                    .addAction(android.R.drawable.sym_action_chat,title,pendingIntent)
                    .setChannelId(CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.sym_action_chat)
                    .build();

            NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(1,notification);
        }
    }
}

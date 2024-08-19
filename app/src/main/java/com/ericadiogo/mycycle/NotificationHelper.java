package com.ericadiogo.mycycle;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class NotificationHelper {

    private final Context context;
    private final String CHANNEL_ID = "REMINDER_NOTIFICATIONS";
    private final int NOTIFICATION_ID;

    public NotificationHelper(Context context) {
        this.context = context;
        String timeString = String.valueOf(System.currentTimeMillis());
        this.NOTIFICATION_ID = Integer.parseInt(timeString.substring(timeString.length() - 5));
    }

    public void createNotification(String title, String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        createNotificationChannel();

        Intent intent = new Intent(context, RemindersActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(R.drawable.baseline_access_alarm_black)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.baseline_access_alarm_black)).setContentTitle(title)
                .setContentText(message).setContentIntent(pendingIntent).setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

        Log.d("NotificationHelper", "Notificação criada. ID: " + NOTIFICATION_ID);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Reminder Notifications",NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Reminder channel to display MyCycle user's scheduled reminders.");

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
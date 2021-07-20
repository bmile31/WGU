package com.bawp.WGU.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bawp.WGU.R;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder startDateBuilder = new NotificationCompat.Builder(context, "startNotifyDate")
                .setSmallIcon(R.drawable.ic_baseline_add_24)
                .setContentTitle("Reminder!")
                .setContentText("Course is starting soon.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationCompat.Builder endDateBuilder = new NotificationCompat.Builder(context, "endNotifyDate")
                .setSmallIcon(R.drawable.ic_baseline_add_24)
                .setContentTitle("Reminder!")
                .setContentText("Course is ending soon.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationCompat.Builder endDateAssessmentBuilder = new NotificationCompat.Builder(context, "endNotifyDateAssessment")
                .setSmallIcon(R.drawable.ic_baseline_add_24)
                .setContentTitle("Reminder!")
                .setContentText("Assessment is ending soon.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, startDateBuilder.build());
        notificationManager.notify(201, endDateBuilder.build());
        notificationManager.notify(202, endDateAssessmentBuilder.build());
    }
}

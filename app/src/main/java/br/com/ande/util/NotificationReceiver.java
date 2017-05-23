package br.com.ande.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import br.com.ande.R;
import br.com.ande.ui.view.activity.SplashScreenActivity;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, SplashScreenActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(SplashScreenActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        String localTitle = intent.getStringExtra(context.getString(R.string.local_notification_param_title));
        String localMsg = intent.getStringExtra(context.getString(R.string.local_notification_param_msg));

        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notification = builder.setContentTitle(localTitle)
                    .setContentText(localMsg)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(localMsg))
                    .setSmallIcon(R.drawable.ic_walk_finished)
                    .setColor(context.getColor(R.color.colorPrimaryDark))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();
        }else{
            notification = builder.setContentTitle(localTitle)
                    .setContentText(localMsg)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(localMsg))
                    .setSmallIcon(R.drawable.ic_walk_finished)
                    .setColor(context.getResources().getColor(R.color.colorPrimaryDark))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(intent.getIntExtra(context.getString(R.string.local_notification_param_id), 0), notification);
    }
}

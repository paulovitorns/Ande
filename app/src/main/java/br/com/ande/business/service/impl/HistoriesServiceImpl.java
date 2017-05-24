package br.com.ande.business.service.impl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.HashMap;
import java.util.List;

import br.com.ande.Ande;
import br.com.ande.R;
import br.com.ande.business.service.HistoriesService;
import br.com.ande.business.service.SessionManagerService;
import br.com.ande.model.Session;
import br.com.ande.common.StepCountListener;
import br.com.ande.sqlLite.entity.History;
import br.com.ande.ui.view.activity.DashBoardActivity;
import br.com.ande.util.DateUtils;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class HistoriesServiceImpl implements HistoriesService {

    @Override
    public int initCountHistories() {

        List<History> histories = Ande.getControllerBD().getHistories();

        int historyLastId = 1;

        if(histories.size() > 0) {
            History history = histories.get(histories.size() - 1);
            historyLastId = history.getId() + 1;
        }

        return historyLastId;
    }

    @Override
    public void saveHistory(StepCountListener listener, int lastId, int steps, long initialTimeStamp, long finalTimeStamp) {

        History history = new History();

        history.setId(lastId);
        history.setSteps(steps);
        history.setStartTime(String.valueOf(initialTimeStamp));
        history.setFinishTime(String.valueOf(finalTimeStamp));

        HashMap<DateUtils.DATE_DIFFERENCE, Object> data = DateUtils.printDifference(
                DateUtils.getDateFromTimestamp(initialTimeStamp),
                DateUtils.getDateFromTimestamp(finalTimeStamp)
        );

        history.setDurationTime(String.valueOf(data.get(DateUtils.DATE_DIFFERENCE.STRING)));

        if(Ande.getControllerBD().insereDados(history)){
            listener.onInsertHistorySuccess(history);
        }

    }

    @Override
    public void shouldSendNotification(History history) {

        HashMap<DateUtils.DATE_DIFFERENCE, Object> data = DateUtils.printDifference(
                DateUtils.getDateFromTimestamp(Long.parseLong(history.getStartTime())),
                DateUtils.getDateFromTimestamp(Long.parseLong(history.getFinishTime()))
        );

        if(Long.parseLong(String.valueOf(data.get(DateUtils.DATE_DIFFERENCE.MINUTES))) >= Ande.getContext().getResources().getInteger(R.integer.min_walked_time_to_send_notification))
            startLocalNotification(history);
    }

    @Override
    public void startLocalNotification(History history) {

        Context context = Ande.getContext();

        SessionManagerService service = new SessionManagerServiceImpl();

        Session session = service.getCurrentSession();

        String title = context.getString(R.string.local_title);
        title = title.replace("...",
                (session != null && session.getUser() != null && !session.getUser().getName().equalsIgnoreCase(""))
                        ? session.getUser().getName() : "jovem"
        );
        String msgFull = context.getString(R.string.local_msg);
        String msg = context.getString(R.string.local_msg_second_line);

        msgFull = msgFull.replace("{steps}", String.valueOf(history.getSteps()));
        msgFull = msgFull.replace("{time}", history.getDurationTime());

        msg = msg.replace("{steps}", String.valueOf(history.getSteps()));
        msg = msg.replace("{time}", history.getDurationTime());

        NotificationCompat.Builder mBuilder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_walk_finished)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setTicker(title)
                    .setColor(context.getColor(R.color.colorPrimaryDark))
                    .setContentText(msgFull)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        }else{
            mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_walk_finished)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setTicker(title)
                    .setColor(context.getResources().getColor(R.color.colorPrimaryDark))
                    .setContentText(msgFull)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        }

        // Add Big View Specific Configuration
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[2];

        events[0] = new String(context.getString(R.string.local_msg_first_line));
        events[1] = new String(msg);

        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle(title);

        // Moves events into the big view
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }

        mBuilder.setStyle(inboxStyle);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, DashBoardActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder
                .create(context);
        stackBuilder.addParentStack(DashBoardActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        // Adds the Intent that starts the Activity to the top of the stack
        PendingIntent resultPendingIntent = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(history.getId(), mBuilder.build());

    }

}

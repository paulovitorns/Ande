package br.com.ande.business.service.impl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

import br.com.ande.Ande;
import br.com.ande.R;
import br.com.ande.business.service.ActivitiesService;
import br.com.ande.business.service.SessionManagerService;
import br.com.ande.dao.ActivityDAO;
import br.com.ande.dao.HistoryDAO;
import br.com.ande.dao.LocationDAO;
import br.com.ande.model.Session;
import br.com.ande.common.StepCountListener;
import br.com.ande.ui.view.activity.DashBoardActivity;
import br.com.ande.util.DateUtils;
import br.com.ande.util.Utils;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class ActivitiesServiceImpl implements ActivitiesService {

    @Override
    public void saveActivity(StepCountListener listener, HistoryDAO historyDAO, ActivityDAO dao, LocationDAO locationDao) {

        DatabaseReference dbRefActivities   = FirebaseDatabase.getInstance().getReference(Ande.activitiesData).child(historyDAO.getHistoryId());
        DatabaseReference dbRefLocation     = FirebaseDatabase.getInstance().getReference(Ande.locationsData).child(dao.getActivityId());

        String uidInitLoc = dbRefLocation.push().getKey();

        locationDao.setLocationId(uidInitLoc);
        dbRefLocation.child(uidInitLoc).setValue(locationDao);

        HashMap<DateUtils.DATE_DIFFERENCE, Object> data = DateUtils.printDifference(
                DateUtils.getDateFromTimestamp(dao.getStartTime()),
                DateUtils.getDateFromTimestamp(dao.getFinishTime())
        );

        Location initialLoc = new Location("initial");
        initialLoc.setLatitude(locationDao.getLat());
        initialLoc.setLongitude(locationDao.getLng());

        Location finalLoc   = Utils.getUserLocation(Ande.getContext());
        if(finalLoc == null){
            finalLoc.setLatitude(0.0);
            finalLoc.setLongitude(0.0);
        }

        double distance = initialLoc.distanceTo(finalLoc);

        dao.setDistance(distance);
        dao.setDurationTime(String.valueOf(data.get(DateUtils.DATE_DIFFERENCE.STRING)));

        dbRefActivities.child(dao.getActivityId()).setValue(dao);

        LocationDAO finalLocation;
        String uidFinaLoc = dbRefLocation.push().getKey();
        if(finalLoc == null){
            finalLocation = new LocationDAO(uidFinaLoc, 0.0, 0.0, false);
        }else{
            finalLocation = new LocationDAO(uidFinaLoc, finalLoc.getLatitude(), finalLoc.getLongitude(), false);
        }

        dbRefLocation.child(uidFinaLoc).setValue(finalLocation);

        listener.onInsertHistorySuccess(dao);
    }

    @Override
    public void shouldSendNotification(ActivityDAO dao) {

        HashMap<DateUtils.DATE_DIFFERENCE, Object> data = DateUtils.printDifference(
                DateUtils.getDateFromTimestamp(dao.getStartTime()),
                DateUtils.getDateFromTimestamp(dao.getFinishTime())
        );

        if(Long.parseLong(String.valueOf(data.get(DateUtils.DATE_DIFFERENCE.MINUTES))) >= Ande.getContext().getResources().getInteger(R.integer.min_walked_time_to_send_notification))
            startLocalNotification(dao);
    }

    @Override
    public void startLocalNotification(ActivityDAO dao) {

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

        msgFull = msgFull.replace("{steps}", String.valueOf(dao.getSteps()));
        msgFull = msgFull.replace("{time}", dao.getDurationTime());

        msg = msg.replace("{steps}", String.valueOf(dao.getSteps()));
        msg = msg.replace("{time}", dao.getDurationTime());

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_walk_finished)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setTicker(title)
                .setContentText(msgFull)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mBuilder.setColor(context.getColor(R.color.colorPrimaryDark));
        }else{
            mBuilder.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
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

        Random r    = new Random();
        int i1      = r.nextInt(1000 - 1 + 1) + 1;

        // mId allows you to update the notification later on.
        mNotificationManager.notify(i1, mBuilder.build());

    }

}

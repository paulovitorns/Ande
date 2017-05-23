package br.com.ande.business.service.impl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.ande.Ande;
import br.com.ande.R;
import br.com.ande.business.service.HistoriesService;
import br.com.ande.business.service.SessionManagerService;
import br.com.ande.model.Session;
import br.com.ande.service.StepCountListener;
import br.com.ande.sqlLite.entity.History;
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

        history.setDurationTime(
                DateUtils.printDifference(
                        DateUtils.getDateFromTimestamp(initialTimeStamp),
                        DateUtils.getDateFromTimestamp(finalTimeStamp)
                )
        );

        if(Ande.getControllerBD().insereDados(history)){
            listener.onInsertHistorySuccess(history);
        }

    }

    @Override
    public void startLocalNotification(History history) {
        Context context = Ande.getContext();

        SessionManagerService service = new SessionManagerServiceImpl();

        Session session = service.getCurrentSession();

        Date date               = new Date();
        Calendar cal            = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.MINUTE, 1);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        String title = context.getString(R.string.local_title);
        title = title.replace("...",
                (session != null && session.getUser() != null && !session.getUser().getName().equalsIgnoreCase(""))
                        ? session.getUser().getName() : "jovem"
        );

        String msg = context.getString(R.string.local_msg);
        msg = msg.replace("{steps}", String.valueOf(history.getSteps()));
        msg = msg.replace("{time}", history.getDurationTime());

        notificationIntent.putExtra(context.getString(R.string.local_notification_param_title), title);
        notificationIntent.putExtra(context.getString(R.string.local_notification_param_msg), msg);
        notificationIntent.putExtra(context.getString(R.string.local_notification_param_id), (int)cal.getTimeInMillis());

        PendingIntent broadcast = PendingIntent.getBroadcast(context,
                (int)cal.getTimeInMillis(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        }
    }

}

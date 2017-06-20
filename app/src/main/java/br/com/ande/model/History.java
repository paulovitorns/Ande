package br.com.ande.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import br.com.ande.Ande;
import br.com.ande.R;
import br.com.ande.dao.HistoryDao;
import br.com.ande.util.DateUtils;
import br.com.ande.util.Utils;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class History {

    /**
     * reference of HistoryDao
     */
    private long id;

    /**
     * show some text like "segunda-feira, 20 de abr 2017"
     */
    private String  descriptionHistory;

    /**
     * show some text like "passo(s) até o momento" or "passo(s) dados"
     */
    private String  descriptionSteps;

    /**
     * Number of steps
     */
    private int     steps;

    /**
     * show some text like "1,5km"
     */
    private String  descriptionDistance;

    /**
     * Verify if is current day
     */
    private boolean isCurrentDay;

    public History(HistoryDao dao) {
        this.setDecriptionOfSteps(dao.getDate());
        this.setDescriptionHistoryText(dao.getDate());
        this.setDescritionOfDistance(dao);
        this.steps  = dao.getSteps();
        this.id     = dao.getItemId();
    }

    private void setDecriptionOfSteps(Date date){

        if(date.equals(DateUtils.toDate(DateUtils.getCurrentDate()))) {
            this.descriptionSteps = Ande.getContext().getString(R.string.history_description_steps);
            isCurrentDay = true;
        }else {
            this.descriptionSteps = Ande.getContext().getString(R.string.history_description_steps_finished);
            isCurrentDay = false;
        }
    }

    private void setDescriptionHistoryText(Date date){

        String description;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        if(isCurrentDay)
            description = Ande.getContext().getString(R.string.history_description_today);
        else
            description = Ande.getContext().getString(R.string.history_description);

        this.descriptionHistory = String.format(description, calendar);

    }

    private void setDescritionOfDistance(HistoryDao dao){
        HashMap<HistoryDao.METRIC, Object> metrics = HistoryDao.getHistoryMetrics(dao);

        double distance = Utils.getDistanceInKM(Double.parseDouble(String.valueOf(metrics.get(HistoryDao.METRIC.DISTANCE))));

        this.descriptionDistance = Utils.StringToCurrency(distance);
    }

    public long getId() {
        return id;
    }

    public String getDescriptionHistory() {
        return descriptionHistory;
    }

    public String getDescriptionSteps() {
        return descriptionSteps;
    }

    public int getSteps() {
        return steps;
    }

    public String getDescriptionDistance() {
        return descriptionDistance;
    }

    public boolean isCurrentDay() {
        return isCurrentDay;
    }

    public static List<History> histories(){

        List<History> histories = new ArrayList<>();
        List<HistoryDao> daos   = HistoryDao.findWithQuery(HistoryDao.class, "select * from HISTORY_DAO order by date desc");

        if(daos.size() > 0){
            for(HistoryDao dao : daos){
                histories.add(new History(dao));
            }
        }

        return histories;
    }
}

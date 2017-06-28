package br.com.ande.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import br.com.ande.Ande;
import br.com.ande.R;
import br.com.ande.common.OnLoadMetricsFinished;
import br.com.ande.dao.HistoryDAO;
import br.com.ande.util.DateUtils;
import br.com.ande.util.HIstoryUtils;
import br.com.ande.util.Utils;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class History implements OnLoadMetricsFinished {

    /**
     * reference of HistoryDao
     */
    private String id;

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

    public History(HistoryDAO dao) {
        this.setDecriptionOfSteps(DateUtils.toDate(dao.getDate()));
        this.setDescriptionHistoryText(DateUtils.toDate(dao.getDate()));
        this.setDescritionOfDistance(dao);
        this.steps  = dao.getSteps();
        this.id     = dao.getHistoryId();
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

    private void setDescritionOfDistance(HistoryDAO dao){
        HIstoryUtils.getHistoryMetrics(dao, this);
    }

    public String getId() {
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

    @Override
    public void loadedMetrics(HashMap<HIstoryUtils.METRIC, Object> metrics) {

        double distance = Utils.getDistanceInKM(Double.parseDouble(String.valueOf(metrics.get(HIstoryUtils.METRIC.DISTANCE))));

        this.descriptionDistance = Utils.StringToCurrency(distance);
    }
}

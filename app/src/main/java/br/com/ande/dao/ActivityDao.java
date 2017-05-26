package br.com.ande.dao;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.List;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class ActivityDao extends SugarRecord {

    @Unique
    long        id;
    int         steps;
    long        startTime;
    long        finishTime;
    String      durationTime;
    double      lat;
    double      lng;

    /**
     * Define a relationship with HistoryDao
     */
    HistoryDao  history;

    public ActivityDao() {
    }

    public ActivityDao(long id, int steps, long startTime, long finishTime, String durationTime, double lat, double lng, HistoryDao history) {

        this.id             = id;
        this.steps          = steps;
        this.startTime      = startTime;
        this.finishTime     = finishTime;
        this.durationTime   = durationTime;
        this.lat            = lat;
        this.lng            = lng;
        this.history        = history;
    }

    public static ActivityDao lastActivity(){
        List<ActivityDao> daos = ActivityDao.listAll(ActivityDao.class);

        if(daos.size() > 0)
            return daos.get(daos.size()-1);
        else
            return null;
    }

    public static long nextId(){
        List<ActivityDao> daos = ActivityDao.listAll(ActivityDao.class);

        if(daos.size() > 0)
            return daos.get(daos.size()-1).getItemId()+1;
        else
            return 1;
    }

    public long getItemId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public HistoryDao getHistory() {
        return history;
    }

    public void setHistory(HistoryDao history) {
        this.history = history;
    }
}

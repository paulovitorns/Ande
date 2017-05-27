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
    double      distance;
    int         lostKal;

    /**
     * Define a relationship with HistoryDao
     */
    HistoryDao  history;

    public ActivityDao() {
    }

    public ActivityDao(int steps, long startTime, long finishTime, String durationTime, double distance, HistoryDao history) {

        this.id             = nextId();
        this.steps          = steps;
        this.startTime      = startTime;
        this.finishTime     = finishTime;
        this.durationTime   = durationTime;
        this.distance       = distance;
        this.history        = history;
    }

    public static ActivityDao lastActivity(){
        List<ActivityDao> daos = ActivityDao.listAll(ActivityDao.class);

        if(daos.size() > 0)
            return daos.get(daos.size()-1);
        else
            return null;
    }

    private long nextId(){
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getLostKal() {
        return lostKal;
    }

    public void setLostKal(int lostKal) {
        this.lostKal = lostKal;
    }

    public HistoryDao getHistory() {
        return history;
    }

    public void setHistory(HistoryDao history) {
        this.history = history;
    }
}

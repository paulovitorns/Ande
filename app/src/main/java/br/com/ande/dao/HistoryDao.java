package br.com.ande.dao;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class HistoryDao extends SugarRecord {

    @Unique
    long    id;
    Date    date;
    int     steps;

    public HistoryDao() {
    }

    public HistoryDao(Date date, int steps) {
        this.id     = nextId();
        this.date   = date;
        this.steps  = steps;
    }

    public static HistoryDao lastHistory(){
        List<HistoryDao> daos = HistoryDao.listAll(HistoryDao.class);

        if(daos.size() > 0)
            return daos.get(daos.size()-1);
        else
            return null;
    }

    private long nextId(){
        List<HistoryDao> daos = HistoryDao.listAll(HistoryDao.class);

        if(daos.size() > 0)
            return daos.get(daos.size()-1).getItemId()+1;
        else
            return 1;
    }

    public static HashMap<METRIC, Object> getHistoryMetrics(HistoryDao history){

        HashMap<METRIC, Object> metrics = new HashMap<>();

        List<ActivityDao> daos = ActivityDao.find(ActivityDao.class, "history = ?", String.valueOf(history.getItemId()));

        int     steps       = 0;
        double  distance    = 0;
        int     kal         = 0;

        for (ActivityDao activity : daos){
            steps       = steps     + activity.getSteps();
            distance    = distance  + activity.getDistance();
            kal         = kal       + activity.getLostKal();
        }

        metrics.put(METRIC.STEPS, steps);
        metrics.put(METRIC.DISTANCE, distance);
        metrics.put(METRIC.KAL, kal);
        return metrics;
    }

    public long getItemId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public static  enum METRIC{
        DISTANCE,
        STEPS,
        KAL
    }
}

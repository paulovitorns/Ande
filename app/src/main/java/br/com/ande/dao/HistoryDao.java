package br.com.ande.dao;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Date;
import java.util.List;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class HistoryDao extends SugarRecord {

    @Unique
    long     id;
    Date     date;
    int      steps;
    int      lostKal;

    public HistoryDao() {
    }

    public HistoryDao(long id, Date date, int steps) {

        this.id     = id;
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

    public static long nextId(){
        List<HistoryDao> daos = HistoryDao.listAll(HistoryDao.class);

        if(daos.size() > 0)
            return daos.get(daos.size()-1).getItemId()+1;
        else
            return 1;
    }

    public static int countSteps(HistoryDao history){
        List<ActivityDao> daos = ActivityDao.find(ActivityDao.class, "history = ?", String.valueOf(history.getItemId()));

        int steps = 0;
        for (ActivityDao activity : daos){
            steps = steps + activity.getSteps();
        }

        return steps;
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

    public int getLostKal() {
        return lostKal;
    }

    public void setLostKal(int lostKal) {
        this.lostKal = lostKal;
    }
}

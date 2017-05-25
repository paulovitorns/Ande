package br.com.ande.dao;

import com.orm.SugarRecord;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class ActivityDao extends SugarRecord<ActivityDao>{

    public long        id;
    public int         steps;
    public long        startTime;
    public long        finishTime;
    public String      durationTime;
    public double      lat;
    public double      lng;

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

    public static long nextId(){
        ActivityDao dao = ActivityDao.findWithQuery(ActivityDao.class, "SELECT * FROM ActivityDao ORDER BY id DESC LIMIT 1").get(0);
        if(dao == null)
            return 1;
        else
            return dao.id+1;
    }
}

package br.com.ande.dao;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class HistoryDao extends SugarRecord<HistoryDao> {

    public long     id;
    public Date     date;
    public int      steps;
    public int      lostKal;

    public HistoryDao() {
    }

    public HistoryDao(long id, Date date, int steps) {

        this.id     = id;
        this.date   = date;
        this.steps  = steps;
    }

    public static long lastId(){
        HistoryDao dao = HistoryDao.findWithQuery(HistoryDao.class, "SELECT * FROM HistoryDao ORDER BY id DESC LIMIT 1").get(0);
        if(dao == null)
            return 1;
        else
            return dao.id;
    }

    public static long nextId(){
        HistoryDao dao = HistoryDao.findWithQuery(HistoryDao.class, "SELECT * FROM HistoryDao ORDER BY id DESC LIMIT 1").get(0);
        if(dao == null)
            return 1;
        else
            return dao.id+1;
    }
}

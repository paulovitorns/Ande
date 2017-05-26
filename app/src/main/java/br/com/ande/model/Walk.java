package br.com.ande.model;

import java.util.ArrayList;
import java.util.List;

import br.com.ande.dao.ActivityDao;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class Walk {

    /**
     * reference of ActivityDao
     */
    private long id;

    /**
     * show some text like "1h 10m 5s"
     */
    private String  durationTime;

    /**
     * Number of steps
     */
    private int     steps;

    public Walk(ActivityDao dao) {
        this.id             = dao.getItemId();
        this.durationTime   = dao.getDurationTime();
        this.steps          = dao.getSteps();
    }

    public long getId() {
        return id;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public int getSteps() {
        return steps;
    }

    public static List<Walk> getWalksFromHistory(History history){

        List<Walk> walks = new ArrayList<>();

        List<ActivityDao> daos = ActivityDao.find(ActivityDao.class, "history = ?", String.valueOf(history.getId()));

        if(daos.size() > 0){
            for (ActivityDao dao : daos){
                walks.add(new Walk(dao));
            }
        }

        return walks;
    }
}

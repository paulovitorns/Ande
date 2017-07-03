package br.com.ande.model;

import br.com.ande.dao.ActivityDAO;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class Walk {

    /**
     * reference of ActivityDao
     */
    private String id;

    /**
     * show some text like "1h 10m 5s"
     */
    private String  durationTime;

    /**
     * Number of steps
     */
    private int     steps;

    public Walk(ActivityDAO dao) {
        this.id             = dao.getActivityId();
        this.durationTime   = dao.getDurationTime();
        this.steps          = dao.getSteps();
    }

    public String getId() {
        return id;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public int getSteps() {
        return steps;
    }

}

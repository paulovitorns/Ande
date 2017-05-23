package br.com.ande.sqlLite.entity;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class History {

    private int id;
    private int steps;
    private String startTime;
    private String finishTime;
    private String durationTime;

    public History() {
    }

    public History(int id, int steps, String startTime, String finishTime) {
        this.id = id;
        this.steps = steps;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public History(int id, int steps, String startTime, String finishTime, String durationTime) {
        this.id = id;
        this.steps = steps;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.durationTime = durationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }
}

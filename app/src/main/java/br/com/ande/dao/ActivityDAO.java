package br.com.ande.dao;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class ActivityDAO {

    String  activityId;
    int     steps;
    long    startTime;
    long    finishTime;
    String  durationTime;
    double  distance;
    int     lostKal;

    public ActivityDAO() {}

    public ActivityDAO(String activityId, int steps, long startTime, long finishTime, String durationTime, double distance, int lostKal) {
        this.activityId     = activityId;
        this.steps          = steps;
        this.startTime      = startTime;
        this.finishTime     = finishTime;
        this.durationTime   = durationTime;
        this.distance       = distance;
        this.lostKal        = lostKal;
    }

    public String getActivityId() {
        return activityId;
    }

    public int getSteps() {
        return steps;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public double getDistance() {
        return distance;
    }

    public int getLostKal() {
        return lostKal;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setLostKal(int lostKal) {
        this.lostKal = lostKal;
    }
}

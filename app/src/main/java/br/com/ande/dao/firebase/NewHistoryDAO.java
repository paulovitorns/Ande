package br.com.ande.dao.firebase;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class NewHistoryDAO {

    String  historyId;
    String  date;
    int     steps;

    public NewHistoryDAO() {}

    public NewHistoryDAO(String historyId, String date, int steps) {
        this.historyId  = historyId;
        this.date       = date;
        this.steps      = steps;
    }

    public String getHistoryId() {
        return historyId;
    }

    public String getDate() {
        return date;
    }

    public int getSteps() {
        return steps;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

}

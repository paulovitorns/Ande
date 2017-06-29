package br.com.ande.ui.presenter;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface HistoryPresenter extends BasePresenter {

    void loadMetrics();
    void finishedLoadHistories();
    void removerHistoriesListener();
}

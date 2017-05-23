package br.com.ande.ui.presenter;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface AndeDashPresenter extends BasePresenter {

    void showStepCounter(int steps, int totalSteps, boolean isMoving);

    void updateLastHistory();

}

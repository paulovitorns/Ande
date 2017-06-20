package br.com.ande.service;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface CountStepsListener {

    /**
     * onDataChanged method
     * <p>
     *     Method used to send new steps count to main interface
     * </p>
     */
    void onDataChanged(boolean isMoving, int steps);
}

package br.com.ande.service;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface StepCountService {

    /**
     * verifyHasDayChanged method
     * <p>
     *     Method used to verify if day has change
     * </p>
     */
    void verifyHasDayChanged();

    /**
     * verifyHasDayChangedBeforeSave method
     * <p>
     *     Method used to verify if day has change before save new activity
     * </p>
     */
    void verifyHasDayChangedBeforeSave();

    /**
     * loadLastHistory method
     * <p>
     *     Method used to load last History
     * </p>
     */
    void loadLastHistory();

    /**
     * loadLastHistoryBeforeSave method
     * <p>
     *     Method used to load last History before save new activity
     * </p>
     */
    void loadLastHistoryBeforeSave();

    /**
     * createHistory method
     * <p>
     *     Method used to create a new History
     * </p>
     */
    void createHistory();

    /**
     * resetCurrentTimer method
     * <p>
     *     Method used to reset current timer
     * </p>
     */
    void resetCurrentTimer();

    /**
     * pushNewHistory method
     * <p>
     *     Method used to push a new history into SqlLite
     * </p>
     */
    void pushNewHistory();


}

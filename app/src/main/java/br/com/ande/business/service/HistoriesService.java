package br.com.ande.business.service;

import br.com.ande.service.StepCountListener;
import br.com.ande.sqlLite.entity.History;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface HistoriesService {

    /**
     * initCountHistories method
     * <p>
     *     Method used to get last history id
     * </p>
     */
    int initCountHistories();

    /**
     * saveHistory method
     * <p>
     *     Method used to save hisories into local database
     * </p>
     */
    void saveHistory(StepCountListener listener, int lastId, int steps, long initialTimeStamp, long finalTimeStamp);

    /**
     * showCurrentWalkInfos method
     * <p>
     *     Method used to send a local notification with last walk data resumed
     * </p>
     */
    void startLocalNotification(History history);

}

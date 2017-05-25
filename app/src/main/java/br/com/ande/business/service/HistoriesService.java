package br.com.ande.business.service;

import br.com.ande.common.StepCountListener;
import br.com.ande.dao.ActivityDao;

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
     *
     * @param       listener    listener para retornar a confirmação do salvament
     * @param       dao         objeto da última atividade do usuário
     */
    void saveHistory(StepCountListener listener, ActivityDao dao);

    /**
     * shouldSendNotification method
     * <p>
     *     metodo valida o tempo de caminha e identifica o tempo e se precisa enviar uma
     *     notificação ao usuário.
     * </p>
     *
     * @param       dao atividade a ser validada
     */
    void shouldSendNotification(ActivityDao dao);

    /**
     * showCurrentWalkInfos method
     * <p>
     *     Method used to send a local notification with last walk data resumed
     * </p>
     *
     * @param       dao atividade a ser validada
     */
    void startLocalNotification(ActivityDao dao);

}

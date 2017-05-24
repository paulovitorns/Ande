package br.com.ande.business.service;

import br.com.ande.common.StepCountListener;
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
     *
     * @param       listener listener para retornar a confirmação do salvament
     * @param       lastId inteiro com o último id no banco
     * @param       steps inteiro com a quantidade de passos da historia
     * @param       initialTimeStamp long com o timestamp do início da caminhada
     * @param       finalTimeStamp long com o timestamp do final da caminhada
     */
    void saveHistory(StepCountListener listener, int lastId, int steps, long initialTimeStamp, long finalTimeStamp);

    /**
     * shouldSendNotification method
     * <p>
     *     metodo valida o tempo de caminha e identifica o tempo e se precisa enviar uma
     *     notificação ao usuário.
     * </p>
     *
     * @param       history história a ser validada
     */
    void shouldSendNotification(History history);

    /**
     * showCurrentWalkInfos method
     * <p>
     *     Method used to send a local notification with last walk data resumed
     * </p>
     *
     * @param       history história a ser validada
     */
    void startLocalNotification(History history);

}

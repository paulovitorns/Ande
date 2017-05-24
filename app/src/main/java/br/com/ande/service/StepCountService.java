package br.com.ande.service;

import br.com.ande.sqlLite.entity.History;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface StepCountService {

    /**
     * resetCurrentTimer method
     * <p>
     *     Method used to reset current timer
     * </p>
     */
    void resetCurrentTimer();

    /**
     * showCurrentWalkInfos method
     * <p>
     *     Method used to show information data from current user walk
     * </p>
     */
    void showCurrentWalkInfos(boolean isMoving);

    /**
     * pushNewHistory method
     * <p>
     *     Method used to push a new history into SqlLite
     * </p>
     */
    void pushNewHistory();

}

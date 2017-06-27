package br.com.ande.business.service;

import br.com.ande.common.OnLoadHistoriesFinished;
import br.com.ande.model.User;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface CountHistoriesService extends BaseDataBaseServices {

    void countHistories(OnLoadHistoriesFinished listener, User user);
}

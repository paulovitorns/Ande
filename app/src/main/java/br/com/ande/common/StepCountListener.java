package br.com.ande.common;

import br.com.ande.dao.ActivityDao;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface StepCountListener {
    void onInsertHistorySuccess(ActivityDao dao);
}

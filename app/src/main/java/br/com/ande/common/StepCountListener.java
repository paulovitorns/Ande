package br.com.ande.common;

import br.com.ande.sqlLite.entity.History;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface StepCountListener {
    void onInsertHistorySuccess(History history);
}

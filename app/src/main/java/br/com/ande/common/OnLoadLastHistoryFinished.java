package br.com.ande.common;

import br.com.ande.dao.firebase.NewHistoryDAO;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface OnLoadLastHistoryFinished {

    void loadedHistory(NewHistoryDAO historyDAO, boolean isBeforeSave);
}

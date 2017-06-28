package br.com.ande.common;

import br.com.ande.dao.HistoryDAO;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface OnLoadLastHistoryFinished {

    void loadedHistory(HistoryDAO historyDAO, boolean isBeforeSave);
}

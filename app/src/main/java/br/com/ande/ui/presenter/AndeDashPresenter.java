package br.com.ande.ui.presenter;

import br.com.ande.sqlLite.entity.Histoty;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface AndeDashPresenter extends BasePresenter {

    void insertNewHistory(Histoty histoty);

    void updateLastHistory();

}

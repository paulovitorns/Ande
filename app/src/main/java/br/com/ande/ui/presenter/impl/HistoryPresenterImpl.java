package br.com.ande.ui.presenter.impl;

import java.util.List;

import br.com.ande.Ande;
import br.com.ande.sqlLite.entity.History;
import br.com.ande.ui.adapter.HistoryAdapter;
import br.com.ande.ui.presenter.HistoryPresenter;
import br.com.ande.ui.view.HistoryView;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class HistoryPresenterImpl implements HistoryPresenter {

    private HistoryView view;

    public HistoryPresenterImpl(HistoryView view) {
        this.view = view;
        this.init();
    }

    @Override
    public void init() {
        this.view.showLoading();

        List<History> histoties = Ande.getControllerBD().getHistories();

        if(histoties.size() > 0){
            this.view.loadAdapter();
            this.view.setAdapter(new HistoryAdapter(histoties, this.view.getContext()));
        }else{
            this.view.showEmptyState();
        }

        this.view.hideLoading();
    }

    @Override
    public void tryAgain() {

    }

    @Override
    public void goBack() {

    }
}

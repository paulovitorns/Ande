package br.com.ande.ui.presenter.impl;

import java.util.List;

import br.com.ande.business.service.CountHistoriesService;
import br.com.ande.business.service.SessionManagerService;
import br.com.ande.business.service.impl.CountHistoriesServiceImpl;
import br.com.ande.business.service.impl.SessionManagerServiceImpl;
import br.com.ande.common.OnLoadHistoriesFinished;
import br.com.ande.model.History;
import br.com.ande.model.Session;
import br.com.ande.ui.adapter.HistoryAdapter;
import br.com.ande.ui.presenter.HistoryPresenter;
import br.com.ande.ui.view.HistoryView;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class HistoryPresenterImpl implements
        HistoryPresenter,
        OnLoadHistoriesFinished {

    private HistoryView view;
    private SessionManagerService sessionManagerService;
    private CountHistoriesService countHistoriesService;

    public HistoryPresenterImpl(HistoryView view) {
        this.view = view;
        this.init();
    }

    @Override
    public void init() {
        this.view.showLoading();

        this.sessionManagerService = new SessionManagerServiceImpl();
        this.countHistoriesService = new CountHistoriesServiceImpl();
    }

    @Override
    public void onCreate() {
        Session session = this.sessionManagerService.getCurrentSession();
        this.countHistoriesService.countHistories(this, session.getUser());
    }

    @Override
    public void tryAgain() {}

    @Override
    public void goBack() {}

    @Override
    public void historiesLoaded(List<History> histories) {
        if(histories.size() > 0){
            view.loadAdapter();
            view.setAdapter(new HistoryAdapter(histories, view.getContext()));
            view.hideLoading();
        }else{
            view.showEmptyState();
            view.hideLoading();
        }
    }

    @Override
    public void removerHistoriesListener() {
        this.countHistoriesService.removeRef();
    }
}

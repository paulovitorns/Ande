package br.com.ande.ui.presenter.impl;

import java.util.List;

import br.com.ande.Ande;
import br.com.ande.business.service.SessionManagerService;
import br.com.ande.business.service.impl.SessionManagerServiceImpl;
import br.com.ande.model.Session;
import br.com.ande.sqlLite.entity.Histoty;
import br.com.ande.ui.presenter.AndeDashPresenter;
import br.com.ande.ui.view.AndeDashView;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class AndeDashPresenterImpl implements AndeDashPresenter {

    private AndeDashView view;
    private SessionManagerService sessionManagerService;

    public AndeDashPresenterImpl(AndeDashView view) {
        this.view = view;
        this.init();
    }

    @Override
    public void init() {
        this.sessionManagerService = new SessionManagerServiceImpl();

        Session session = this.sessionManagerService.getCurrentSession();

        if(session != null && session.getUser() != null){
            this.view.showInfoUser(session.getUser());
        }

        this.view.startWalkListeners();

        List<Histoty> histoties = Ande.getControllerBD().getHistories();

        if(histoties.size() > 0) {
            this.view.loadLastHistory(histoties.get(histoties.size() - 1));
            this.view.updateCountHistories(histoties.size());
        }else {
            this.view.loadLastHistory(null);
            this.view.setNullCountHistories();
        }

    }

    @Override
    public void tryAgain() {

    }

    @Override
    public void goBack() {

    }

    @Override
    public void insertNewHistory(Histoty histoty) {

        if(Ande.getControllerBD().insereDados(histoty)){
            this.updateLastHistory();
        }
    }

    @Override
    public void updateLastHistory() {

        List<Histoty> histoties = Ande.getControllerBD().getHistories();

        if(histoties.size() > 0) {
            this.view.loadLastHistory(histoties.get(histoties.size() - 1));
            this.view.updateCountHistories(histoties.size());
        }else {
            this.view.loadLastHistory(null);
        }
    }
}

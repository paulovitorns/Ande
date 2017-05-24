package br.com.ande.ui.presenter.impl;

import java.util.List;

import br.com.ande.Ande;
import br.com.ande.business.service.SessionManagerService;
import br.com.ande.business.service.impl.SessionManagerServiceImpl;
import br.com.ande.model.Session;
import br.com.ande.sqlLite.entity.History;
import br.com.ande.ui.presenter.AndeDashPresenter;
import br.com.ande.ui.view.AndeDashView;
import br.com.ande.util.DateUtils;

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

        List<History> histoties = Ande.getControllerBD().getHistories();

        if(histoties.size() > 0) {
            this.view.loadLastHistory(histoties.get(histoties.size() - 1));
            this.view.updateCountHistories(histoties.size());
        }else {
            this.view.loadLastHistory(null);
            this.view.setNullCountHistories();
        }

    }

    @Override
    public void tryAgain() {}

    @Override
    public void goBack() {}

    @Override
    public void showStepCounter(int steps, int totalSteps, boolean isMoving) {
        if(isMoving)
            this.view.setCurrentSteps(steps);
        else
            this.view.setStopedWalk(totalSteps);
    }

    @Override
    public void updateLastHistory() {

        List<History> histories = Ande.getControllerBD().getHistories();

        if(histories.size() > 0) {
            this.view.loadLastHistory(histories.get(histories.size() - 1));
            this.view.updateCountHistories(histories.size());
        }else {
            this.view.loadLastHistory(null);
        }
    }
}

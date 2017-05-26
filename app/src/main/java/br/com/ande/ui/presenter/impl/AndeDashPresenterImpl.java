package br.com.ande.ui.presenter.impl;

import java.util.List;

import br.com.ande.business.service.SessionManagerService;
import br.com.ande.business.service.impl.SessionManagerServiceImpl;
import br.com.ande.model.History;
import br.com.ande.model.Session;
import br.com.ande.model.Walk;
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

        List<History> histories = History.histories();

        if(histories.size() > 0) {
            List<Walk> walks = Walk.getWalksFromHistory(histories.get(histories.size() - 1));

            this.view.loadLastHistory(walks.get(walks.size() - 1), histories.get(histories.size()-1).getSteps());
            this.view.updateCountHistories(histories.size());
        }else {
            this.view.loadLastHistory(null, 0);
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

        List<History> histories = History.histories();

        if(histories.size() > 0) {
            List<Walk> walks = Walk.getWalksFromHistory(histories.get(histories.size() - 1));

            this.view.loadLastHistory(walks.get(walks.size() - 1), histories.get(histories.size()-1).getSteps());
            this.view.updateCountHistories(histories.size());
        }else {
            this.view.loadLastHistory(null, 0);
        }
    }
}

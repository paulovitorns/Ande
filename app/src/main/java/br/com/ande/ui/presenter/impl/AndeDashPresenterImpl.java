package br.com.ande.ui.presenter.impl;

import java.util.List;

import br.com.ande.business.service.CountHistoriesService;
import br.com.ande.business.service.SessionManagerService;
import br.com.ande.business.service.impl.CountHistoriesServiceImpl;
import br.com.ande.business.service.impl.SessionManagerServiceImpl;
import br.com.ande.common.OnLoadHistoriesFinished;
import br.com.ande.common.OnUserMovingListener;
import br.com.ande.common.OnWalkFinished;
import br.com.ande.model.History;
import br.com.ande.model.Session;
import br.com.ande.model.Walk;
import br.com.ande.ui.presenter.AndeDashPresenter;
import br.com.ande.ui.view.AndeDashView;
import br.com.ande.util.ActivitiesUtils;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class AndeDashPresenterImpl implements
        AndeDashPresenter,
        OnUserMovingListener,
        OnLoadHistoriesFinished,
        OnWalkFinished {

    private AndeDashView view;
    private SessionManagerService sessionManagerService;
    private CountHistoriesService countHistoriesService;
    private History actHistory;

    public AndeDashPresenterImpl(AndeDashView view) {
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

        if(session != null && session.getUser() != null){
            this.view.showInfoUser(session.getUser());
        }

    }

    @Override
    public void tryAgain() {}

    @Override
    public void goBack() {}

    @Override
    public void notifyUser(boolean isMoving, int steps) {
        if(isMoving)
            this.view.setCurrentSteps(steps);
        else
            this.view.setStopedWalk(steps);
    }

    @Override
    public void historiesLoaded(List<History> histories) {
        if(histories != null && histories.size() > 0) {
            view.updateCountHistories(histories.size());
            actHistory = histories.get(histories.size()-1);
            ActivitiesUtils.lastWalk(this, actHistory);
        }else {
            this.view.hideLoading();
            view.loadLastHistory(0, 0);
            view.setNullCountHistories();
        }
    }

    @Override
    public void walkLoaded(Walk walk) {
        view.loadLastHistory((walk != null) ? walk.getSteps():0, actHistory.getSteps());
        view.hideLoading();
    }

    @Override
    public void loadDashboard() {

        Session session = this.sessionManagerService.getCurrentSession();
        this.countHistoriesService.countHistories(this, session.getUser());
    }

    @Override
    public void removeDashBoardListener() {
        this.countHistoriesService.removeRef();
    }
}

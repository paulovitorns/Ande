package br.com.ande.ui.presenter.impl;

import android.os.AsyncTask;

import java.util.HashMap;
import java.util.List;

import br.com.ande.business.service.SessionManagerService;
import br.com.ande.business.service.impl.SessionManagerServiceImpl;
import br.com.ande.common.OnUserMovingListener;
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

public class AndeDashPresenterImpl implements AndeDashPresenter, OnUserMovingListener {

    public  AndeDashView view;
    private SessionManagerService sessionManagerService;

    public AndeDashPresenterImpl(AndeDashView view) {
        this.view = view;
        this.init();
    }

    @Override
    public void init() {
        this.view.showLoading();
        this.sessionManagerService = new SessionManagerServiceImpl();

        Session session = this.sessionManagerService.getCurrentSession();

        if(session != null && session.getUser() != null){
            this.view.showInfoUser(session.getUser());
        }

        DashTask dashTask = new DashTask();
        dashTask.execute("dash");

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

    private class DashTask extends AsyncTask<String, Void, HashMap<DASHINFODATA, Integer>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.showLoading();
        }

        @Override
        protected HashMap<DASHINFODATA, Integer> doInBackground(String... strings) {

            HashMap<DASHINFODATA, Integer> data = new HashMap<>();

            List<History> histories = History.histories();

            if(histories.size() > 0) {

                History history = histories.get(0);

                Walk walk = Walk.getLastWalkFromHistory(history);

                data.put(DASHINFODATA.COUNT_HISTORIES, histories.size());
                data.put(DASHINFODATA.LAST_STEPS, (walk != null) ? walk.getSteps() : 0);
                data.put(DASHINFODATA.TOTAL_STEPS, history.getSteps());
            }else {

                data.put(DASHINFODATA.COUNT_HISTORIES, 0);
                data.put(DASHINFODATA.LAST_STEPS, 0);
                data.put(DASHINFODATA.TOTAL_STEPS, 0);
            }

            return data;
        }

        @Override
        protected void onPostExecute(HashMap<DASHINFODATA, Integer> infos) {
            super.onPostExecute(infos);

            if(infos.get(DASHINFODATA.COUNT_HISTORIES) > 0) {

                view.loadLastHistory(infos.get(DASHINFODATA.LAST_STEPS), infos.get(DASHINFODATA.TOTAL_STEPS));
                view.updateCountHistories(infos.get(DASHINFODATA.COUNT_HISTORIES));
            }else {

                view.loadLastHistory(0, 0);
                view.setNullCountHistories();
            }

            view.hideLoading();
        }

    }

    public enum DASHINFODATA{
        COUNT_HISTORIES,
        LAST_STEPS,
        TOTAL_STEPS
    }


}

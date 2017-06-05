package br.com.ande.ui.presenter.impl;

import android.os.AsyncTask;

import java.util.List;

import br.com.ande.Ande;
import br.com.ande.model.History;
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
        HistoryTask task = new HistoryTask();
        task.execute("histories");
    }

    @Override
    public void tryAgain() {}

    @Override
    public void goBack() {}

    private class HistoryTask extends AsyncTask<String, Void, List<History>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.showLoading();
        }

        @Override
        protected List<History> doInBackground(String... strings) {
            return History.histories();
        }

        @Override
        protected void onPostExecute(List<History> histories) {
            super.onPostExecute(histories);

            if(histories.size() > 0){
                view.loadAdapter();
                view.setAdapter(new HistoryAdapter(histories, view.getContext()));
            }else{
                view.showEmptyState();
            }

            view.hideLoading();
        }

    }

}

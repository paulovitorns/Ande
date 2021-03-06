package br.com.ande.ui.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;

import br.com.ande.R;
import br.com.ande.ui.presenter.HistoryPresenter;
import br.com.ande.ui.presenter.impl.HistoryPresenterImpl;
import br.com.ande.ui.view.DashBoardView;
import br.com.ande.ui.view.HistoryView;
import br.com.ande.util.ActivitiesUtils;
import br.com.ande.util.AnalyticsUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class HistoryFragment extends Fragment implements HistoryView {

    @Bind(R.id.rvHistory)   RecyclerView recycler;
    @Bind(R.id.emptyState)  LinearLayout emptyState;

    private HistoryPresenter presenter;

    public HistoryFragment(){

    }

    public static HistoryFragment newInstance(){
        return new HistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        HashMap<String, String> track = new HashMap<>();
        track.put(AnalyticsUtils.event_track, AnalyticsUtils.screen_histories);
        AnalyticsUtils.logScreenViewEvent(track, getContext());

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ButterKnife.bind(this, view);

        this.presenter = new HistoryPresenterImpl(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onCreate();
    }

    @Override
    public void onDestroy() {
        presenter.removerHistoriesListener();
        ActivitiesUtils.removeWalkListner();
        super.onDestroy();
    }

    @Override
    public Activity getContext() {
        return getActivity();
    }

    @Override
    public void onBackPressed() {
        ((DashBoardView)getActivity()).onBackPressed();
    }

    @Override
    public void showLoading() {
        ((DashBoardView)getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((DashBoardView)getActivity()).hideLoading();
    }

    @Override
    public void loadAdapter() {

        recycler.setVisibility(View.VISIBLE);
        emptyState.setVisibility(View.GONE);

        recycler.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(mLayoutManager);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        recycler.setAdapter(adapter);
    }

    @Override
    public void showEmptyState() {
        recycler.setVisibility(View.GONE);
        emptyState.setVisibility(View.VISIBLE);
    }
}

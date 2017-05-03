package br.com.ande.ui.presenter.impl;

import br.com.ande.model.DashboardNavigation;
import br.com.ande.ui.presenter.DashboardPresenter;
import br.com.ande.ui.view.DashBoardView;
import br.com.ande.ui.view.fragment.DashBoardFragment;
import br.com.ande.ui.view.fragment.HistoryFragment;
import br.com.ande.ui.view.fragment.ProfileFragment;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class DashboardPresenterImpl implements DashboardPresenter {

    private DashBoardView view;
    private DashboardNavigation dashNav;

    public DashboardPresenterImpl(DashBoardView view) {
        this.view = view;
        init();
    }

    @Override
    public void init() {
        view.loadDefaultFragment();
        view.setupBottomNavigationView();
    }

    @Override
    public void tryAgain() {

    }

    @Override
    public void goBack() {
        this.view.onBackPressed();
    }

    @Override
    public boolean validateChangeFragment() {

        if(dashNav.getFragment() == null){
            return false;
        }
        return true;
    }

    @Override
    public void setFragmentToNavigator() {

        if(dashNav == DashboardNavigation.PROFILE){
            dashNav.setFragment(ProfileFragment.newInstance());
        }

        if(dashNav == DashboardNavigation.DASHBOARD){
            dashNav.setFragment(DashBoardFragment.newInstance());
        }

        if(dashNav == DashboardNavigation.HISTORY){
            dashNav.setFragment(HistoryFragment.newInstance());
        }

        returnFragment(dashNav);
    }

    @Override
    public void returnFragment(DashboardNavigation navigation) {
        this.dashNav = navigation;

        if(validateChangeFragment()){
            view.changeFragment(dashNav.getFragment());
        }else{
            setFragmentToNavigator();
        }

    }

}

package br.com.ande.model;

import android.support.v4.app.Fragment;

import br.com.ande.ui.view.fragment.DashBoardFragment;
import br.com.ande.ui.view.fragment.HistoryFragment;
import br.com.ande.ui.view.fragment.ProfileFragment;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public enum DashboardNavigation {
    PROFILE("PROFILE", ProfileFragment.newInstance()),
    DASHBOARD("DASHBOARD", DashBoardFragment.newInstance()),
    HISTORY("HISTORY", HistoryFragment.newInstance());

    private String      titlePage;
    private Fragment    fragment;

    DashboardNavigation(String titlePage, Fragment fragment) {
        this.titlePage  = titlePage;
        this.fragment   = fragment;
    }

    public String getTitlePage() {
        return titlePage;
    }

    public void setTitlePage(String titlePage) {
        this.titlePage = titlePage;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}

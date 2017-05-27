package br.com.ande.ui.presenter.impl;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import br.com.ande.ui.presenter.LaunchPresenter;
import br.com.ande.ui.view.LauchView;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class LaunchPresenterImpl implements LaunchPresenter {

    private LauchView view;

    public LaunchPresenterImpl(LauchView view) {
        this.view = view;
        this.init();
    }

    @Override
    public void init() {

        if(!this.hasLocationPermission())
            this.view.requestLocationPermission();
        else
            this.view.startDash();
    }

    @Override
    public void tryAgain() {}

    @Override
    public void goBack() {}

    @Override
    public boolean hasLocationPermission() {
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return false;
        else
            return true;
    }

}

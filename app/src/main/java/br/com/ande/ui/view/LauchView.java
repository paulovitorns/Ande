package br.com.ande.ui.view;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface LauchView extends BaseView {

    void requestLocationPermission();

    void showDialogPermission();

    void startDash();
}

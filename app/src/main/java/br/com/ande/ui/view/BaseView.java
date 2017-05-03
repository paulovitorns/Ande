package br.com.ande.ui.view;

import android.content.Context;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface BaseView {

    void showLoading();

    void hideLoading();

    Context getContext();

    void onBackPressed();

}

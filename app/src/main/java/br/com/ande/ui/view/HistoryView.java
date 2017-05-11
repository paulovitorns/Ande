package br.com.ande.ui.view;

import android.support.v7.widget.RecyclerView;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface HistoryView extends BaseView {

    void loadAdapter();

    void setAdapter(RecyclerView.Adapter adapter);

    void showEmptyState();

}

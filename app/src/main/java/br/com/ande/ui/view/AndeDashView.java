package br.com.ande.ui.view;

import br.com.ande.model.User;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface AndeDashView extends BaseView {

    void showInfoUser(User user);

    void startWalkListeners();

    void setPic();
}

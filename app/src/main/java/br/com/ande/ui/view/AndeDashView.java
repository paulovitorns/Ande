package br.com.ande.ui.view;

import br.com.ande.model.User;
import br.com.ande.sqlLite.entity.Histoty;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface AndeDashView extends BaseView {

    void showInfoUser(User user);

    void startWalkListeners();

    void setPic();

    void loadLastHistory(Histoty histoty);

    void updateCountHistories(int histories);

    void setNullCountHistories();

    void sendNewHistory();
}

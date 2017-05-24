package br.com.ande.ui.view;

import br.com.ande.model.User;
import br.com.ande.sqlLite.entity.History;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface AndeDashView extends BaseView {

    void showInfoUser(User user);

    void setPic();

    void loadLastHistory(History history);

    void updateCountHistories(int histories);

    void setNullCountHistories();

    void setCurrentSteps(int steps);

    void setStopedWalk(int totalSteps);

}

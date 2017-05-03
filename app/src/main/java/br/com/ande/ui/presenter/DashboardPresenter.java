package br.com.ande.ui.presenter;

import br.com.ande.model.DashboardNavigation;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface DashboardPresenter extends BasePresenter {

    boolean validateChangeFragment();

    void setFragmentToNavigator();

    void returnFragment(DashboardNavigation navigation);

}

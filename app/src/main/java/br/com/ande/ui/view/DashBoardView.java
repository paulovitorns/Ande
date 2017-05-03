package br.com.ande.ui.view;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface DashBoardView extends BaseView {

    void setupBottomNavigationView();

    void loadDefaultFragment();

    void changeFragment(Fragment fragment);

    View getBottomNavigationView();

}

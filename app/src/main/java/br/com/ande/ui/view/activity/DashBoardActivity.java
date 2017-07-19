package br.com.ande.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.ande.R;
import br.com.ande.model.DashboardNavigation;
import br.com.ande.service.impl.StepCountServiceImpl;
import br.com.ande.ui.presenter.DashboardPresenter;
import br.com.ande.ui.presenter.impl.DashboardPresenterImpl;
import br.com.ande.ui.view.DashBoardView;
import br.com.ande.ui.view.fragment.DashBoardFragment;
import br.com.ande.ui.view.fragment.HistoryFragment;
import br.com.ande.ui.view.fragment.ProfileFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class DashBoardActivity extends BaseActivity implements DashBoardView {

    @Bind(R.id.navigation)    BottomNavigationView bottomNavigationView;

    private boolean confirmedExit = false;
    private DashboardPresenter presenter;
    private Menu bottomNavigationViewMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        ButterKnife.bind(this);
        presenter = new DashboardPresenterImpl(this);

    }

    @Override
    public void setupBottomNavigationView() {

        bottomNavigationViewMenu = bottomNavigationView.getMenu();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem selectedMenuItem) {

                switch (selectedMenuItem.getItemId()) {
                    case R.id.nav_account :
                        presenter.returnFragment(DashboardNavigation.PROFILE);
                        break;
                    case R.id.nav_dash :
                        presenter.returnFragment(DashboardNavigation.DASHBOARD);
                        break;
                    case R.id.nav_history :
                        presenter.returnFragment(DashboardNavigation.HISTORY);
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(confirmedExit) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_exit_alert), Toast.LENGTH_SHORT).show();
            loadDefaultFragment();
            confirmedExit = true;
        }
    }
    @Override
    public void loadDefaultFragment() {

        bottomNavigationViewMenu.findItem(R.id.nav_account).setChecked(false);
        bottomNavigationViewMenu.findItem(R.id.nav_history).setChecked(false);
        bottomNavigationViewMenu.findItem(R.id.nav_dash).setChecked(true);

        DashBoardFragment fragment = DashBoardFragment.newInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContainer, fragment);
        transaction.commit();
    }

    @Override
    public void changeFragment(Fragment fragment) {
        if(confirmedExit)
            confirmedExit = false;

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View getBottomNavigationView() {
        return null;
    }

    @Override
    public void onClickProfile() {
        bottomNavigationViewMenu.findItem(R.id.nav_account).setChecked(true);
        changeFragment(ProfileFragment.newInstance());
    }

    @Override
    public void onClickHistory() {
        bottomNavigationViewMenu.findItem(R.id.nav_history).setChecked(true);
        changeFragment(HistoryFragment.newInstance());
    }
}

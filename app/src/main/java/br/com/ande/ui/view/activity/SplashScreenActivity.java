package br.com.ande.ui.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import br.com.ande.Ande;
import br.com.ande.R;
import br.com.ande.service.impl.StepCountServiceImpl;
import br.com.ande.ui.presenter.LaunchPresenter;
import br.com.ande.ui.presenter.impl.LaunchPresenterImpl;
import br.com.ande.ui.view.LauchView;
import br.com.ande.ui.view.component.CustomDialog;
import br.com.ande.util.AnalyticsUtils;
import br.com.ande.util.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends BaseActivity implements LauchView {

    @Bind(R.id.imgIcon)     ImageView       imgIcon;
    @Bind(R.id.container)   RelativeLayout  container;

    private long            WAIT_DELAY = 1000;
    public static final int STARTUP_DELAY = 200;
    public static final int ANIM_ITEM_DURATION = 500;
    public static final int ITEM_DELAY = 200;

    private boolean         animationStarted = false;

    private static final int LOCATION_REQUEST_CODE  = 200;

    private LaunchPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        AnalyticsUtils.logAppOpenEvent(this);

        setTheme(R.style.AppTheme);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ButterKnife.bind(this);

        this.presenter = new LaunchPresenterImpl(this);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void animate() {

        int margin = Utils.dp2px((int) (getResources().getDimension(R.dimen.margin_large) / getResources().getDisplayMetrics().density));

        margin = margin - (margin * 2);

        ViewCompat.animate(imgIcon)
                .translationY(margin)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            viewAnimator = ViewCompat.animate(v)
                    .translationY(50).alpha(1)
                    .setStartDelay((ITEM_DELAY * i) + 500)
                    .setDuration(1000);

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }

        this.presenter.init();
    }

    @Override
    public void requestLocationPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            CustomDialog dialog = new CustomDialog(
                    this,
                    getString(R.string.permission_location_title),
                    getString(R.string.permission_location_explain),
                    this
            );

            dialog.show();

        }else{
            this.showDialogPermission();
        }

    }

    @Override
    public void showDialogPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_REQUEST_CODE);
    }

    @Override
    public void startDash() {

        if(this.presenter.hasUserRegistered()){

            Ande.logUserIntoFabric();
            Ande.logUserInAnalytics(this);

            startService(new Intent(this, StepCountServiceImpl.class));

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashScreenActivity.this, DashBoardActivity.class));
                            finish();
                        }
                    });
                }
            }, WAIT_DELAY);
        }else{

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashScreenActivity.this, FirstAccessActivity.class));
                            finish();
                        }
                    });
                }
            }, WAIT_DELAY);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                this.startDash();
                return;
            }

        }
    }

}

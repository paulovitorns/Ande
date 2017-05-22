package br.com.ande.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import br.com.ande.R;
import br.com.ande.util.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends BaseActivity {

    @Bind(R.id.imgIcon)     ImageView       imgIcon;
    @Bind(R.id.container)   RelativeLayout  container;

    private long WAIT_DELAY = 3000;
    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;

    private boolean animationStarted = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ButterKnife.bind(this);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreenActivity.this, DashBoardActivity.class));
                    }
                });
            }
        }, WAIT_DELAY);

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

    }

}

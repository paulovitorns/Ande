package br.com.ande.ui.view.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;

import br.com.ande.R;
import br.com.ande.ui.view.FirstAccessView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class FirstAccessActivity extends BaseActivity implements FirstAccessView {

    @Bind(R.id.animation_view)          LottieAnimationView animationView;
    @Bind(R.id.containerAnimation)      LinearLayout        containerAnimation;
    @Bind(R.id.containerIntro)          LinearLayout        containerIntro;
    @Bind(R.id.containerButtons)        LinearLayout        containerButtons;
    @Bind(R.id.containerNewUser)        LinearLayout        containerNewUser;
    @Bind(R.id.containerRecoverUser)    LinearLayout        containerRecoverUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_access);

        ButterKnife.bind(this);

        startAnimationIntro();
    }

    @Override
    public void startAnimationIntro() {

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                showFirstAccessIntro();
            }
            @Override
            public void onAnimationEnd(Animator animator) {}
            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        animationView.playAnimation();
    }

    @Override
    public void showFirstAccessIntro() {
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_long_animation);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                containerAnimation.setAlpha(1.0f);
            }
            @Override
            public void onAnimationEnd(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        containerAnimation.startAnimation(fadeIn);
    }

    @OnClick(R.id.btnIniciar)
    @Override
    public void tapInStartAccess() {
        Animation slideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_out);
        slideOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_long_animation);
                fadeIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        containerButtons.setAlpha(1.0f);
                        showFirstAccessButtons();
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {}
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                containerButtons.startAnimation(fadeIn);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                containerIntro.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        containerIntro.startAnimation(slideOut);
    }

    @Override
    public void showFirstAccessButtons() {
        Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_button_intro);

        containerNewUser.startAnimation(bounce);
        containerRecoverUser.startAnimation(bounce);
    }

    @Override
    public void tapInNewAccess() {

    }

    @Override
    public void tapInRecoverUser() {

    }
}
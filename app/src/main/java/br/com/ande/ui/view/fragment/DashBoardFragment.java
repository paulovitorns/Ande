package br.com.ande.ui.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import br.com.ande.R;
import br.com.ande.model.User;
import br.com.ande.ui.presenter.AndeDashPresenter;
import br.com.ande.ui.presenter.impl.AndeDashPresenterImpl;
import br.com.ande.ui.view.AndeDashView;
import br.com.ande.ui.view.DashBoardView;
import br.com.ande.ui.view.activity.DashBoardActivity;
import br.com.ande.util.ActivitiesUtils;
import br.com.ande.util.AnalyticsUtils;
import br.com.ande.util.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class DashBoardFragment extends Fragment implements
        AndeDashView,
        SensorEventListener{

    @Bind(R.id.txUserName)      TextView    txUserName;
    @Bind(R.id.txWalkRegister)  TextView    txWalkRegister;
    @Bind(R.id.txLastSteps)     TextView    txLastSteps;
    @Bind(R.id.txActualSteps)   TextView    txActualSteps;

    @Bind(R.id.imgProfile)      CircleImageView imageView;
    @Bind(R.id.imgIconAction)   ImageView       imgIconAction;
    @Bind(R.id.containerLast)   LinearLayout    containerLast;
    @Bind(R.id.containerActual) LinearLayout    containerActual;

    private int     targetW;
    private int     targetH;

    private AndeDashPresenter   presenter;
    private User                user;
    private int                 steps = -1;
    private SensorManager       sensorManager;
    private Sensor              mSteps;

    public DashBoardFragment(){

    }

    public static DashBoardFragment newInstance(){
        return new DashBoardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        HashMap<String, String> track = new HashMap<>();
        track.put(AnalyticsUtils.event_track, AnalyticsUtils.screen_dashboard);

        AnalyticsUtils.logScreenViewEvent(track, getContext());

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ButterKnife.bind(this, view);

        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            mSteps = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }else{
            Toast.makeText(getContext(), "Você não tem o sensor de passos", Toast.LENGTH_LONG).show();
        }

        targetH = Utils.dp2px((int) (getResources().getDimension(R.dimen.img_dash_size) / getResources().getDisplayMetrics().density));
        targetW = Utils.dp2px((int) (getResources().getDimension(R.dimen.img_dash_size) / getResources().getDisplayMetrics().density));

        this.presenter = new AndeDashPresenterImpl(this);

        if(savedInstanceState != null){
            showInfoUser((User) savedInstanceState.getSerializable(User.KEY));
            steps = savedInstanceState.getInt("steps");
        }else{
            this.user = new User();
            this.presenter.onCreate();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(User.KEY, user);
        outState.putSerializable("steps", steps);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadDashboard();
    }

    @Override
    public void onDestroy() {
        presenter.removeDashBoardListener();
        ActivitiesUtils.removeWalkListner();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mSteps, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void showInfoUser(User user) {
        this.user = user;

        if(user.getName() != null || !user.getName().isEmpty())
            txUserName.setText("Olá, "+user.getName());
        else
            txUserName.setText(getResources().getString(R.string.user_name));

        if(user.getImgNameResource() != null){
            if(!user.getImgNameResource().isEmpty())
                setPic();
        }

    }

    @Override
    public Activity getContext() {
        return getActivity();
    }

    @Override
    public void onBackPressed() {
        ((DashBoardView)getActivity()).onBackPressed();
    }

    @Override
    public void showLoading() {
        ((DashBoardView)getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((DashBoardActivity)getActivity()).hideLoading();
    }


    @Override
    public void setPic() {
        // Get the dimensions of the View

        if(targetW == 0)
            targetW = imageView.getMeasuredWidth();

        if(targetH == 0)
            targetH = imageView.getMeasuredHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(user.getImgNameResource(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(user.getImgNameResource(), bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void loadLastHistory(int steps, int totalSteps) {

        if(this.steps < totalSteps)
            this.steps = totalSteps;

        if(totalSteps == 0){
            containerLast.setVisibility(View.GONE);
        }else{
            containerLast.setVisibility(View.VISIBLE);
            txLastSteps.setText(String.valueOf(steps));
            txActualSteps.setText(String.valueOf(totalSteps));
        }

    }

    @Override
    public void updateCountHistories(int histories) {
        String str = getString(R.string.steps_count);
        str = str.replace("...", String.valueOf(histories));
        txWalkRegister.setText(str);
    }

    @Override
    public void setNullCountHistories() {
        txWalkRegister.setText(getString(R.string.steps_count_null));
    }

    @Override
    public void setCurrentSteps(int steps) {
        imgIconAction.setImageResource(R.drawable.ic_directions_walk_white_48dp);
        txActualSteps.setText(String.valueOf(steps));
    }

    @Override
    public void setStopedWalk(int totalSteps) {
        imgIconAction.setImageResource(R.drawable.ic_hot_tub_white_48dp);
        txActualSteps.setText(String.valueOf(totalSteps));
    }

    @OnClick(R.id.containerUserInfo)
    public void onClickProfile(){
        ((DashBoardActivity) getActivity()).onClickProfile();
    }

    @OnClick(R.id.containerLast)
    public void onClickHistory(){
        ((DashBoardActivity) getActivity()).onClickHistory();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        steps++;
        txActualSteps.setText(String.valueOf(steps));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}

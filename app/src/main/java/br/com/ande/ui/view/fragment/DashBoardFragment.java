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
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import br.com.ande.R;
import br.com.ande.model.User;
import br.com.ande.sqlLite.entity.Histoty;
import br.com.ande.ui.presenter.AndeDashPresenter;
import br.com.ande.ui.presenter.impl.AndeDashPresenterImpl;
import br.com.ande.ui.view.AndeDashView;
import br.com.ande.ui.view.DashBoardView;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class DashBoardFragment extends Fragment implements AndeDashView, SensorEventListener {

    @Bind(R.id.txUserName)      TextView    txUserName;
    @Bind(R.id.txWalkRegister)  TextView    txWalkRegister;
    @Bind(R.id.txLastSteps)     TextView    txLastSteps;
    @Bind(R.id.txActualSteps)   TextView    txActualSteps;

    @Bind(R.id.imgProfile)      CircleImageView imageView;
    @Bind(R.id.imgIconAction)   ImageView       imgIconAction;
    @Bind(R.id.containerLast)   LinearLayout    containerLast;

    private int     targetW;
    private int     targetH;
    private int     steps;

    private AndeDashPresenter presenter;
    private User    user;
    private SensorManager   sensorManager;
    private Sensor  mSteps;
    private Timer   timer;

//    TODO:: set timer to 1min
//    private long    WAIT_DELAY_FOR_NEXT_STEP = 60000;
    private long    WAIT_DELAY_FOR_NEXT_STEP = 10000;
    private boolean isWaitNextStepIsStarted;
    private boolean isLoadfirtsStep = true;
    private long    initialTimeStamp;
    private long    currentTimeStamp;
    private long    finalTimeStamp;
    private int     lastId;

    public DashBoardFragment(){

    }

    public static DashBoardFragment newInstance(){
        return new DashBoardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ButterKnife.bind(this, view);

        targetH = dp2px((int) (getResources().getDimension(R.dimen.img_dash_size) / getResources().getDisplayMetrics().density));
        targetW = dp2px((int) (getResources().getDimension(R.dimen.img_dash_size) / getResources().getDisplayMetrics().density));

        this.presenter = new AndeDashPresenterImpl(this);
        this.user = new User();

        this.timer = new Timer();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        if(this.timer != null){
            this.timer.cancel();
            this.timer.purge();
            this.timer = null;
        }

        isLoadfirtsStep = true;
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
    public void startWalkListeners() {
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            mSteps = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }else{
            Toast.makeText(getContext(), "Você não tem o sensor de passos", Toast.LENGTH_LONG).show();
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
        ((DashBoardView)getActivity()).hideLoading();
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
    public void loadLastHistory(Histoty histoty) {
        if(histoty == null){

            containerLast.setVisibility(View.GONE);
            lastId = 1;
        }else{

            containerLast.setVisibility(View.VISIBLE);
            txLastSteps.setText(String.valueOf(histoty.getSteps()));
            lastId = histoty.getId() + 1;
        }
    }

    @Override
    public void updateCountHistories(int histories) {
        String str = txWalkRegister.getText().toString();
        str = str.replace("...", String.valueOf(histories));
        txWalkRegister.setText(str);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(!isLoadfirtsStep){

            if(initialTimeStamp == 0)
                initialTimeStamp = sensorEvent.timestamp;

            currentTimeStamp = sensorEvent.timestamp;

            steps++;
            setInfoToCurrentWalk(steps, true);

            if(!isWaitNextStepIsStarted){
                startNewTimer();
            }else{
                if(this.timer != null) {
                    this.timer.cancel();
                    this.timer.purge();
                    this.timer = null;
                }

                startNewTimer();
            }

            Log.d("COUNT_STEPS", String.valueOf(steps));
        }else{
            isLoadfirtsStep = false;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        steps = 0;
    }

    private void startNewTimer(){

        if(this.timer == null)
            this.timer = new Timer();

        isWaitNextStepIsStarted = true;

        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        isWaitNextStepIsStarted = false;
                        finalTimeStamp = currentTimeStamp;
                        sendNewHistory();
                        steps = 0;
                        setInfoToCurrentWalk(steps, false);
                        Log.d("COUNT_STEPS_NEW", "VAMOS REINICIAR");

                    }

                });

            }

        }, WAIT_DELAY_FOR_NEXT_STEP);

    }

    private void setInfoToCurrentWalk(int steps, boolean isMoving){
        if(isMoving)
            imgIconAction.setImageResource(R.drawable.ic_directions_walk_white_48dp);
        else
            imgIconAction.setImageResource(R.drawable.ic_hot_tub_white_48dp);

        txActualSteps.setText(String.valueOf(steps));
    }


    @Override
    public void sendNewHistory() {

        Histoty histoty = new Histoty();

        histoty.setId(lastId);
        histoty.setSteps(steps);
        histoty.setStartTime(String.valueOf(initialTimeStamp));
        histoty.setFinishTime(String.valueOf(finalTimeStamp));

        initialTimeStamp    = 0;
        finalTimeStamp      = 0;
        currentTimeStamp    = 0;

        presenter.insertNewHistory(histoty);
    }

}

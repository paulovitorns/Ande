package br.com.ande.service.impl;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import br.com.ande.Ande;
import br.com.ande.R;
import br.com.ande.business.service.HistoriesService;
import br.com.ande.business.service.impl.HistoriesServiceImpl;
import br.com.ande.common.StepCountListener;
import br.com.ande.dao.ActivityDao;
import br.com.ande.dao.HistoryDao;
import br.com.ande.service.StepCountService;
import br.com.ande.util.DateUtils;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class StepCountServiceImpl extends Service implements SensorEventListener, StepCountService, StepCountListener {

    public static final String TAG = "StepCountService";

    /**
     * Communication with activity by this service
     */
    private LocalBinder walkBinder = new LocalBinder();

    /**
     * Sensor data
     */
    private SensorManager sensorManager;
    private Sensor  mSteps;


    /**
     * Global data to check if service is up and registered the steps
     */
    private boolean started                     = false;
    private boolean registered                  = false;
    private int     steps                       = 0;
    private int     tSteps                      = 0;
    private Timer   timer;
    private long    WAIT_DELAY_FOR_NEXT_STEP    = Ande.getContext().getResources().getInteger(R.integer.wait_delay_for_next_step);
    private boolean isWaitNextStepStart;
    private boolean isLoadfirtsStep;

    /**
     * Time metric
     */
    private long    initialTimeStamp;
    private long    finalTimeStamp;

    /**
     * historyDao object for take any activity created into current day
     */
    private HistoryDao historyDao;

    /**
     * Service to save histories into DB
     */
    private HistoriesService service;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "step service Instantiated");

        service = new HistoriesServiceImpl();

        this.verifyHasDayChanged();

        sensorManager   = null;
        mSteps          = null;
        sensorManager   = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            mSteps = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }else{
            Toast.makeText(this, "Você não tem o sensor de passos", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        synchronized(this) {
            if(started) {
                return START_STICKY;
            }

            registered      = sensorManager.registerListener(this, mSteps, SensorManager.SENSOR_DELAY_FASTEST);
            started         = true;
            isLoadfirtsStep = false;
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return walkBinder;
    }

    public class LocalBinder extends Binder {
        public StepCountServiceImpl getService(){
            return StepCountServiceImpl.this;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() != Sensor.TYPE_STEP_COUNTER){
            return;
        }else{
            if(isLoadfirtsStep){

                if(initialTimeStamp == 0)
                    initialTimeStamp = DateUtils.getCurrentTimeInMillis();

                steps++;
                tSteps++;
                showCurrentWalkInfos(true);

                if(!isWaitNextStepStart){
                    resetCurrentTimer();
                }else{
                    if(this.timer != null) {
                        this.timer.cancel();
                        this.timer.purge();
                        this.timer = null;
                    }

                    resetCurrentTimer();
                }

                Log.d(TAG, String.valueOf(steps));
            }else{
                isLoadfirtsStep = true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        steps = 0;
    }

    @Override
    public void verifyHasDayChanged() {
        if(historyDao == null){
            this.loadLastHistory();
            return;
        }

        if(DateUtils.isCurrentDay(historyDao.getDate())) {
            this.createHistory();
        }else {
            tSteps = HistoryDao.countSteps(historyDao);
        }
    }

    @Override
    public void verifyHasDayChangedBeforeSave() {
        if(historyDao == null){
            this.loadLastHistoryBeforeSave();
            return;
        }

        if(DateUtils.isCurrentDay(historyDao.getDate()))
            this.createHistory();
    }

    @Override
    public void createHistory() {
        //TODO::Implement service to save last history and create a new history

        historyDao  = new HistoryDao(HistoryDao.nextId(), DateUtils.toDate(DateUtils.getCurrentDate()), 0);
        tSteps      = 0;
    }

    @Override
    public void loadLastHistory() {
        historyDao  = HistoryDao.lastHistory();
        if(historyDao == null)
            historyDao = new HistoryDao(HistoryDao.nextId(), DateUtils.toDate(DateUtils.getCurrentDate()), tSteps);

        this.verifyHasDayChanged();
    }

    @Override
    public void loadLastHistoryBeforeSave() {
        historyDao  = HistoryDao.lastHistory();
        if(historyDao == null)
            historyDao = new HistoryDao(HistoryDao.nextId(), DateUtils.toDate(DateUtils.getCurrentDate()), tSteps);

        this.verifyHasDayChangedBeforeSave();
    }

    @Override
    public void resetCurrentTimer() {

        if(this.timer == null)
            this.timer = new Timer();

        isWaitNextStepStart = true;

        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {

                isWaitNextStepStart = false;
                finalTimeStamp      = DateUtils.getCurrentTimeInMillis();
                pushNewHistory();
                steps = 0;
                showCurrentWalkInfos(false);
                Log.d(TAG, "VAMOS REINICIAR");
            }

        }, WAIT_DELAY_FOR_NEXT_STEP);
    }

    @Override
    public void showCurrentWalkInfos(boolean isMoving) {
        if(isMoving){

        }else{

        }
        Log.d(TAG, "Passos dados "+String.valueOf(tSteps));
    }

    @Override
    public void pushNewHistory() {

        this.verifyHasDayChangedBeforeSave();

        historyDao.setSteps(tSteps);
        historyDao.save();

        ActivityDao activityDao = new ActivityDao(
            ActivityDao.nextId(), steps, initialTimeStamp, finalTimeStamp, null, 0.0, 0.0, historyDao
        );

        service.saveHistory(this, activityDao);
        initialTimeStamp    = 0;
        finalTimeStamp      = 0;
        isLoadfirtsStep     = false;
    }

    @Override
    public void onInsertHistorySuccess(ActivityDao dao) {
        service.shouldSendNotification(dao);
    }

}

package br.com.ande.service.impl;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import br.com.ande.Ande;
import br.com.ande.R;
import br.com.ande.business.service.ActivitiesService;
import br.com.ande.business.service.SessionManagerService;
import br.com.ande.business.service.impl.ActivitiesServiceImpl;
import br.com.ande.business.service.impl.SessionManagerServiceImpl;
import br.com.ande.common.OnLoadLastHistoryFinished;
import br.com.ande.common.OnLoadMetricsFinished;
import br.com.ande.common.StepCountListener;
import br.com.ande.dao.firebase.NewActivityDAO;
import br.com.ande.dao.firebase.NewHistoryDAO;
import br.com.ande.dao.firebase.NewLocationDAO;
import br.com.ande.model.Session;
import br.com.ande.service.StepCountService;
import br.com.ande.util.DateUtils;
import br.com.ande.util.HIstoryUtils;
import br.com.ande.util.Utils;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class StepCountServiceImpl extends Service implements
        SensorEventListener,
        StepCountService,
        StepCountListener,
        OnLoadMetricsFinished,
        OnLoadLastHistoryFinished{

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
    private NewHistoryDAO historyDao;

    /**
     * Service to save histories into DB
     */
    private ActivitiesService service;

    /**
     * var used to get initial user position
     */
    private NewLocationDAO initialPosition;

    /**
     * var used to get reference of histories in realtime database
     */
    private DatabaseReference dbRefHistories;

    /**
     * var used to get reference of activities in realtime database
     */
    private DatabaseReference dbRefActivities;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "step service Instantiated");

        SessionManagerService sessionManagerService = new SessionManagerServiceImpl();

        Session session = sessionManagerService.getCurrentSession();

        dbRefHistories  = FirebaseDatabase.getInstance().getReference(Ande.historiesData).child(session.getUser().getUid());

        service     = new ActivitiesServiceImpl();

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

    @Override
    public void loadedMetrics(HashMap<HIstoryUtils.METRIC, Object> metrics) {
        this.tSteps = Integer.parseInt(String.valueOf(metrics.get(HIstoryUtils.METRIC.STEPS)));
    }

    @Override
    public void loadedHistory(NewHistoryDAO historyDAO, boolean isBeforeSave) {

        historyDao  = historyDAO;
        if(historyDao == null) {
            String uid  = dbRefHistories.push().getKey();
            historyDao  = new NewHistoryDAO(uid, DateUtils.getCurrentDate(), tSteps);
            tSteps      = 0;

            dbRefHistories.child(uid).setValue(historyDao);
        }

        if (!isBeforeSave)
            this.verifyHasDayChanged();
        else
            this.verifyHasDayChangedBeforeSave();
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

                if(initialTimeStamp == 0) {
                    initialTimeStamp    = DateUtils.getCurrentTimeInMillis();
                    Location location   = Utils.getUserLocation(Ande.getContext());
                    if(location == null){
                        initialPosition = new NewLocationDAO("", 0.0, 0.0, true);
                    }else{
                        initialPosition = new NewLocationDAO("", location.getLatitude(), location.getLongitude(), true);
                    }

                }

                steps++;
                tSteps++;

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
                Log.d(TAG, "VAMOS REINICIAR");
            }

        }, WAIT_DELAY_FOR_NEXT_STEP);
    }

    @Override
    public void verifyHasDayChanged() {
        if(historyDao == null){
            this.loadLastHistory();
            return;
        }

        if(DateUtils.isCurrentDay(DateUtils.toDate(historyDao.getDate()))) {
            this.createHistory();
        }else {

            HIstoryUtils.getHistoryMetrics(historyDao, this);
        }
    }

    @Override
    public void verifyHasDayChangedBeforeSave() {
        if(historyDao == null){
            this.loadLastHistoryBeforeSave();
            return;
        }

        if(DateUtils.isCurrentDay(DateUtils.toDate(historyDao.getDate())))
            this.createHistory();
    }

    @Override
    public void createHistory() {

        String uid  = dbRefHistories.push().getKey();
        historyDao  = new NewHistoryDAO(uid, DateUtils.getCurrentDate(), 0);
        tSteps      = 0;

        dbRefHistories.child(uid).setValue(historyDao);
    }

    @Override
    public void loadLastHistory() {

        SessionManagerService sessionManagerService = new SessionManagerServiceImpl();
        Session session = sessionManagerService.getCurrentSession();

        HIstoryUtils.lastHistory(this, false, session.getUser().getUid());
    }

    @Override
    public void loadLastHistoryBeforeSave() {

        SessionManagerService sessionManagerService = new SessionManagerServiceImpl();
        Session session = sessionManagerService.getCurrentSession();

        HIstoryUtils.lastHistory(this, true, session.getUser().getUid());
    }

    @Override
    public void pushNewHistory() {

        if(steps > 0){

            this.verifyHasDayChangedBeforeSave();

            historyDao.setSteps(tSteps);
            dbRefHistories.child(historyDao.getHistoryId()).setValue(historyDao);
            tSteps = 0;

            dbRefActivities = FirebaseDatabase.getInstance().getReference(Ande.activitiesData).child(historyDao.getHistoryId());

            String uid = dbRefActivities.push().getKey();

            NewActivityDAO activityDAO = new NewActivityDAO(
                    uid, steps, initialTimeStamp, finalTimeStamp, null, 0, 0
            );

            dbRefActivities.child(uid).setValue(activityDAO);

            service.saveActivity(this, historyDao, activityDAO, initialPosition);
        }

        initialTimeStamp    = 0;
        finalTimeStamp      = 0;
        isLoadfirtsStep     = false;
    }

    @Override
    public void onInsertHistorySuccess(NewActivityDAO dao) {
        service.shouldSendNotification(dao);
    }

}
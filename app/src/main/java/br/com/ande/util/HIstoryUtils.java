package br.com.ande.util;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

import br.com.ande.Ande;
import br.com.ande.common.OnLoadLastHistoryFinished;
import br.com.ande.common.OnLoadMetricsFinished;
import br.com.ande.common.OnLoadMetricsForObjectFinished;
import br.com.ande.dao.ActivityDAO;
import br.com.ande.dao.HistoryDAO;
import br.com.ande.model.History;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class HIstoryUtils {

    public static void lastHistory(final OnLoadLastHistoryFinished listener, final boolean isBeforeSave, String uid){
        Firebase ref    = new Firebase(Ande.historiesUriData).child(uid);
        Query query     = ref.limitToLast(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HistoryDAO historyDAO = null;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    historyDAO = snapshot.getValue(HistoryDAO.class);
                    continue;
                }
                listener.loadedHistory(historyDAO, isBeforeSave);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public static void getHistoryMetrics(HistoryDAO history, final OnLoadMetricsFinished listener){

        Firebase ref    = new Firebase(Ande.activitiesUriData);
        Query query     = ref.child(history.getHistoryId());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<HIstoryUtils.METRIC, Object> metrics = new HashMap<>();

                int     steps       = 0;
                double  distance    = 0;
                int     kal         = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ActivityDAO activity = snapshot.getValue(ActivityDAO.class);

                    steps       = steps     + activity.getSteps();
                    distance    = distance  + activity.getDistance();
                    kal         = kal       + activity.getLostKal();
                }

                metrics.put(HIstoryUtils.METRIC.STEPS, steps);
                metrics.put(HIstoryUtils.METRIC.DISTANCE, distance);
                metrics.put(HIstoryUtils.METRIC.KAL, kal);

                listener.loadedMetrics(metrics);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public static void getHistoryMetrics(History history, final int position, final OnLoadMetricsForObjectFinished listener){

        Firebase ref    = new Firebase(Ande.activitiesUriData);
        Query query     = ref.child(history.getId());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<HIstoryUtils.METRIC, Object> metrics = new HashMap<>();

                int     steps       = 0;
                double  distance    = 0;
                int     kal         = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ActivityDAO activity = snapshot.getValue(ActivityDAO.class);

                    steps       = steps     + activity.getSteps();
                    distance    = distance  + activity.getDistance();
                    kal         = kal       + activity.getLostKal();
                }

                metrics.put(HIstoryUtils.METRIC.STEPS, steps);
                metrics.put(HIstoryUtils.METRIC.DISTANCE, distance);
                metrics.put(HIstoryUtils.METRIC.KAL, kal);

                listener.loadedMetrics(metrics, position);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public enum METRIC{
        DISTANCE,
        STEPS,
        KAL
    }
}

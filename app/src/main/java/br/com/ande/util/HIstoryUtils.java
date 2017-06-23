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
import br.com.ande.dao.firebase.NewActivityDAO;
import br.com.ande.dao.firebase.NewHistoryDAO;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class HIstoryUtils {

    public static void lastHistory(final OnLoadLastHistoryFinished listener, final boolean isBeforeSave){
        Firebase ref    = new Firebase(Ande.historiesUriData);
        Query query     = ref.limitToLast(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                NewHistoryDAO historyDAO = null;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    for (DataSnapshot subSnap : snapshot.getChildren()) {
                        historyDAO = subSnap.getValue(NewHistoryDAO.class);
                        continue;
                    }
                }
                listener.loadedHistory(historyDAO, isBeforeSave);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public static void getHistoryMetrics(NewHistoryDAO history, final OnLoadMetricsFinished listener){

        Firebase ref    = new Firebase(Ande.historiesUriData);
        Query query     = ref.child(history.getHistoryId());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<HIstoryUtils.METRIC, Object> metrics = new HashMap<>();

                int     steps       = 0;
                double  distance    = 0;
                int     kal         = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    NewActivityDAO activity = snapshot.getValue(NewActivityDAO.class);

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

    public enum METRIC{
        DISTANCE,
        STEPS,
        KAL
    }
}

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
import br.com.ande.common.OnWalkFinished;
import br.com.ande.dao.firebase.NewActivityDAO;
import br.com.ande.dao.firebase.NewHistoryDAO;
import br.com.ande.model.History;
import br.com.ande.model.Walk;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class ActivitiesUtils {

    public static void lastWalk(final OnWalkFinished listener, History history){

        Firebase ref    = new Firebase(Ande.activitiesUriData).child(history.getId());
        Query query     = ref.limitToLast(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Walk walk = null;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    walk = new Walk(snapshot.getValue(NewActivityDAO.class));
                    continue;
                }
                listener.walkLoaded(walk);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}

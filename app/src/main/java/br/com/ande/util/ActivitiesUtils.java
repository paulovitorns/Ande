package br.com.ande.util;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import br.com.ande.Ande;
import br.com.ande.common.OnWalkFinished;
import br.com.ande.dao.ActivityDAO;
import br.com.ande.model.History;
import br.com.ande.model.Walk;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class ActivitiesUtils {

    private static ValueEventListener eventListener;
    private static Query query;

    public static void lastWalk(final OnWalkFinished listener, History history){

        Firebase ref    = new Firebase(Ande.activitiesUriData).child(history.getId());
        query           = ref.limitToLast(1);

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Walk walk = null;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    walk = new Walk(snapshot.getValue(ActivityDAO.class));
                    continue;
                }
                listener.walkLoaded(walk);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        query.addValueEventListener(eventListener);
    }

    public static void removeWalkListner(){
        if(query != null)
            if(eventListener != null)
                query.removeEventListener(eventListener);
    }

}

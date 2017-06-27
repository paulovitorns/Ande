package br.com.ande.business.service.impl;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.ande.Ande;
import br.com.ande.business.service.CountHistoriesService;
import br.com.ande.common.OnActivitiesLoaded;
import br.com.ande.common.OnLoadHistoriesFinished;
import br.com.ande.dao.firebase.NewHistoryDAO;
import br.com.ande.model.History;
import br.com.ande.model.User;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class CountHistoriesServiceImpl implements CountHistoriesService {
    private Firebase dbRef;

    @Override
    public void countHistories(final OnLoadHistoriesFinished listener, User user) {
        dbRef = new Firebase(Ande.historiesUriData).child(user.getUid());
        Query query = dbRef.orderByValue();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<History> histories = new ArrayList<History>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    histories.add(new History(snapshot.getValue(NewHistoryDAO.class)));
                }
                listener.historiesLoaded(histories);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.historiesLoaded(null);
            }

        });
    }

    @Override
    public void removeRef() {
        Query query = dbRef.orderByValue();
        query.removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }
}

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
import br.com.ande.common.OnLoadHistoriesFinished;
import br.com.ande.dao.HistoryDAO;
import br.com.ande.model.History;
import br.com.ande.model.User;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class CountHistoriesServiceImpl implements CountHistoriesService {
    private Firebase dbRef;
    private ValueEventListener valueEventListener;
    private Query query;

    @Override
    public void countHistories(final OnLoadHistoriesFinished listener, User user) {
        dbRef = new Firebase(Ande.historiesUriData).child(user.getUid());
        query = dbRef.orderByValue();

        this.valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<History> histories = new ArrayList<History>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    histories.add(new History(snapshot.getValue(HistoryDAO.class)));
                }
                listener.historiesLoaded(histories);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.historiesLoaded(null);
            }

        };

        query.addValueEventListener(this.valueEventListener);
    }

    @Override
    public void removeRef() {
        if(query != null)
            if(valueEventListener != null)
                query.removeEventListener(this.valueEventListener);
    }
}

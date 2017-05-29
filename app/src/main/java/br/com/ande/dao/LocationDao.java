package br.com.ande.dao;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.List;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class LocationDao extends SugarRecord {

    @Unique
    long        id;
    double      lat;
    double      lng;
    boolean     isInitial;
    ActivityDao activity;

    public LocationDao() {
    }

    public LocationDao(double lat, double lng, boolean isInitial, ActivityDao activity) {
        this.id         = nextId();
        this.lat        = lat;
        this.lng        = lng;
        this.isInitial  = isInitial;
        this.activity   = activity;
    }

    private long nextId(){
        List<LocationDao> daos = LocationDao.listAll(LocationDao.class);

        if(daos.size() > 0)
            return daos.get(daos.size()-1).getItemId()+1;
        else
            return 1;
    }

    public long getItemId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public ActivityDao getActivity() {
        return activity;
    }

    public void setActivity(ActivityDao activity) {
        this.activity = activity;
    }
}

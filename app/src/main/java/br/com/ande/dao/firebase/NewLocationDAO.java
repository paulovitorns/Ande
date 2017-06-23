package br.com.ande.dao.firebase;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class NewLocationDAO {

    String locationId;
    double  lat;
    double  lng;
    boolean isInitial;

    public NewLocationDAO() {}

    public NewLocationDAO(String locationId, double lat, double lng, boolean isInitial) {
        this.locationId = locationId;
        this.lat        = lat;
        this.lng        = lng;
        this.isInitial  = isInitial;
    }

    public String getLocationId() {
        return locationId;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }
}

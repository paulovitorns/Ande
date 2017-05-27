package br.com.ande.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;

import java.text.DecimalFormat;

import br.com.ande.Ande;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class Utils {

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Ande.getContext().getResources().getDisplayMetrics());
    }

    public static Location getUserLocation(Context context) {

        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location userLocation   = null;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return userLocation;
        }

        userLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(userLocation == null)
            userLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return userLocation;
    }

    /**
     * Retorna true caso a localização esteja disponível
     * <p>
     * Este metodo é usado nos serviços antes de iniciar a chamada
     * a API. Ele verifica se o GPS ou a localização pela internet esta
     * ativa.
     * </p>
     *
     * @return      boolean informando o status da localização
     * @see         boolean
     */
    public static boolean isLocationAvailable(){

        LocationManager lm = (LocationManager)Ande.getContext().getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled     = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        return (gps_enabled || network_enabled) ? true : false;
    }

    public static double getDistanceInKM(double distance){
        return distance / 1000.0;
    }


    public static String StringToCurrency(double km) {
        DecimalFormat dFormat = new DecimalFormat("######,###0.000");
        return dFormat.format(km)+"km";
    }

}

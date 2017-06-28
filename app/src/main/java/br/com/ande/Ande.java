package br.com.ande;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.firebase.client.Firebase;
import com.crashlytics.android.Crashlytics;

import br.com.ande.business.service.SessionManagerService;
import br.com.ande.business.service.impl.SessionManagerServiceImpl;
import br.com.ande.model.Session;
import io.fabric.sdk.android.Fabric;
/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class Ande extends MultiDexApplication {

    private static Context context;

    public static String usersUriData;
    public static String historiesUriData;
    public static String activitiesUriData;
    public static String locationsUriData;
    public static String usersData;
    public static String historiesData;
    public static String activitiesData;
    public static String locationsData;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        context             = getApplicationContext();
        usersUriData        = context.getString(R.string.user_uri_data);
        historiesUriData    = context.getString(R.string.histories_uri_data);
        activitiesUriData   = context.getString(R.string.activities_uri_data);
        locationsUriData    = context.getString(R.string.locations_uri_data);
        usersData           = context.getString(R.string.user_data);
        historiesData       = context.getString(R.string.histories_data);
        activitiesData      = context.getString(R.string.activities_data);
        locationsData       = context.getString(R.string.locations_data);

        Firebase.setAndroidContext(this);

    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Ande.context = context;
    }

    public static void logUserIntoFabric(){

        SessionManagerService sessionManagerService = new SessionManagerServiceImpl();
        Session session = sessionManagerService.getCurrentSession();

        if(session != null && session.getUser() != null){
            if (Fabric.isInitialized()) {
                Crashlytics.setUserIdentifier(session.getUser().getUid());
                Crashlytics.setUserEmail(session.getUser().getEmail());
                Crashlytics.setUserName(session.getUser().getName());
            }
        }

    }

}

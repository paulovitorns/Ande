package br.com.ande;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.orm.SugarApp;
import com.orm.SugarContext;
/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class Ande extends SugarApp {

    private static Context context;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context         = getApplicationContext();
        SugarContext.init(context);
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Ande.context = context;
    }

}

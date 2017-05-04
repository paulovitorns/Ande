package br.com.ande.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.com.ande.Ande;
import br.com.ande.R;
import br.com.ande.model.Session;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class SharedPreferencesUtils {

    private static final String KEY             = Ande.getContext().getString(R.string.shared_session);
    private static final String SESSION_DATA    = Ande.getContext().getString(R.string.shared_session_data);

    private static SharedPreferences getPreferences(){
        SharedPreferences sharedPreferences = Ande.getContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void saveSessionData(Session session){

        SharedPreferences preferences   = getPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        String dataPersistence          = new Gson().toJson(session, Session.class);

        editor.putString(SESSION_DATA, dataPersistence).commit();
    }

    public static void unsetSessionData(){
        SharedPreferences sharedPreferences = getPreferences();
        SharedPreferences.Editor editor     = sharedPreferences.edit();

        editor.putString(SESSION_DATA, null).commit();
    }

    public static Session getSessionData(){
        SharedPreferences sharedPreferences = getPreferences();
        String sessionStr                   = sharedPreferences.getString(SESSION_DATA, "");
        return new Gson().fromJson(sessionStr, Session.class);
    }


}

package br.com.ande.util;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;
import java.util.Map;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class AnalyticsUtils {

    public static void logAppOpenEvent(Context context){

        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);

        analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null);
    }

    public static void logScreenViewEvent(HashMap<String, String> params, Context context){

        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);

        final Bundle bundle = new Bundle();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }


        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    public static void logActionEvent(HashMap<String, String> params, Context context){

        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);

        Bundle bundle = new Bundle();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }

        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    public static final String event_track = "screen";

    public static final String screen_dashboard     = "screen_dashboard";
    public static final String screen_histories     = "screen_histories";
    public static final String screen_activities    = "screen_activities";
    public static final String screen_profile_dash  = "screen_profile_dash";

}

package br.com.ande.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.ande.service.impl.StepCountServiceImpl;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, StepCountServiceImpl.class));
    }
}

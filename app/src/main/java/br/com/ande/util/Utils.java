package br.com.ande.util;

import android.util.TypedValue;

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
}

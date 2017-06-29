package br.com.ande.common;

import java.util.HashMap;

import br.com.ande.util.HIstoryUtils;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface OnLoadMetricsForObjectFinished {
    void loadedMetrics(HashMap<HIstoryUtils.METRIC, Object> metrics, int position);
}

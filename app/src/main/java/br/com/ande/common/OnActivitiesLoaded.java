package br.com.ande.common;

import java.util.List;

import br.com.ande.model.Walk;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface OnActivitiesLoaded {
    void activitiesLoaded(List<Walk> walks);
}

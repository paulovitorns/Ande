package br.com.ande.common;

import java.util.List;

import br.com.ande.model.History;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface OnLoadHistoriesFinished {
    void historiesLoaded(List<History> histories);
}

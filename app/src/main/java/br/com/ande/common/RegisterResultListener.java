package br.com.ande.common;

import br.com.ande.model.User;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface RegisterResultListener {

    void onSuccess(User user);

    void onError(String title, String msg);

}

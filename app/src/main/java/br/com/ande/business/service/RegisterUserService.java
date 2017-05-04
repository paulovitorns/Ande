package br.com.ande.business.service;

import br.com.ande.common.RegisterResultListener;
import br.com.ande.model.User;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface RegisterUserService {

    void registerClient(User user, RegisterResultListener listener);

    void updateClient(User user, RegisterResultListener listener);

}

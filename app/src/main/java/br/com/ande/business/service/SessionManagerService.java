package br.com.ande.business.service;

import br.com.ande.model.Session;
import br.com.ande.model.User;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface SessionManagerService {

    void createNewSession(User user);

    Session getCurrentSession();

    void updateCurrentSession(User user);

    void destroyCurrentSession();
}

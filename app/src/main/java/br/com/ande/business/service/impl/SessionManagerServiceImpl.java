package br.com.ande.business.service.impl;

import br.com.ande.business.service.SessionManagerService;
import br.com.ande.model.Session;
import br.com.ande.model.User;
import br.com.ande.util.SharedPreferencesUtils;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class SessionManagerServiceImpl implements SessionManagerService {

    @Override
    public void createNewSession(User user) {

        if(SharedPreferencesUtils.getSessionData() == null)
        {
            SharedPreferencesUtils.saveSessionData(new Session(user));
        }else{
            SharedPreferencesUtils.unsetSessionData();
            SharedPreferencesUtils.saveSessionData(new Session(user));
        }

    }

    @Override
    public Session getCurrentSession() {
        return SharedPreferencesUtils.getSessionData();
    }

    @Override
    public void updateCurrentSession(User user) {
        SharedPreferencesUtils.saveSessionData(new Session(user));
    }

    @Override
    public void destroyCurrentSession() {
        SharedPreferencesUtils.unsetSessionData();
    }
}

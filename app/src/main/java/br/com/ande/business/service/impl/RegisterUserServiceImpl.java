package br.com.ande.business.service.impl;

import br.com.ande.business.service.RegisterUserService;
import br.com.ande.common.RegisterResultListener;
import br.com.ande.model.User;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class RegisterUserServiceImpl implements RegisterUserService {

    @Override
    public void registerClient(User user, RegisterResultListener listener) {
        if(user != null)
            listener.onSuccess(user);
        else
            listener.onError("Erro ao cadastrar", "Preencha os seus dados");
    }

    @Override
    public void updateClient(User user, RegisterResultListener listener) {
        if(user != null)
            listener.onSuccess(user);
        else
            listener.onError("Erro ao atualizar sua conta", "Preencha os seus dados");
    }
}

package br.com.ande.model;

import java.io.Serializable;

/**
 * © Copyright 2016 Skatavel.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Skatavel app.
 */

public class Session implements Serializable {

    public static final String KEY = Session.class.getSimpleName();

    private User user;

    public Session(User user) {
        this.user = user;
    }

    public void setSession(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public void cleanSession(){
        this.user = null;
    }

    public void updateClient(User user){
        this.user = user;
    }

}

package br.com.ande.dao;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class UserDAO {

    String userId;
    String email;
    String password;

    public UserDAO() {}

    public UserDAO(String userId, String email, String password) {
        this.userId     = userId;
        this.email      = email;
        this.password   = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

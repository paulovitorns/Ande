package br.com.ande.model;

import java.io.Serializable;
import java.util.Date;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class User implements Serializable {

    public static String KEY = User.class.getSimpleName();


    private String  uid;
    private String  name;
    private String  birthdate;
    private String  email;
    private String  imgNameResource;

    public User() {
    }

    public User(String uid, String name, String birthdate, String email, String imgNameResource) {
        this.uid                = uid;
        this.name               = name;
        this.birthdate          = birthdate;
        this.email              = email;
        this.imgNameResource    = imgNameResource;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgNameResource() {
        return imgNameResource;
    }

    public void setImgNameResource(String imgNameResource) {
        this.imgNameResource = imgNameResource;
    }
}

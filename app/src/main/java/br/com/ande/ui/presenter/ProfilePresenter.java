package br.com.ande.ui.presenter;

import br.com.ande.model.User;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface ProfilePresenter extends BasePresenter {

    void createNewUserIntoFireBase();

    void updateUserIntoFirebase();

    void requestUserUid(String oldEmail);

    void sendToRegister(User user, String oldEmail);

    void sendToUpdate(User user);

    void updateImagemUser(User user);

    boolean validateRegisterData();

    boolean isValidEmail();

}

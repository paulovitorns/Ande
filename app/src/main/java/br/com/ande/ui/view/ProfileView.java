package br.com.ande.ui.view;

import br.com.ande.model.User;

/**
 * © Copyright 2016 Skatavel.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Skatavel app.
 */

public interface ProfileView extends BaseView {

    void setDataInfoUser(User user);

    void onClickBtnSave();

    void setNameEmptyError();

    void setBirthdateEmptyError();

    void setEmailEmptyError();

    void setEmailFormatError();

    void setNomeDefaultState();

    void setBirthdateDefaultState();

    void setEmailDefaultState();

    void showSuccessDialog();

}

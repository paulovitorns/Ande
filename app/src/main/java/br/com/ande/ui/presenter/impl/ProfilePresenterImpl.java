package br.com.ande.ui.presenter.impl;

import br.com.ande.business.service.RegisterUserService;
import br.com.ande.business.service.SessionManagerService;
import br.com.ande.business.service.impl.RegisterUserServiceImpl;
import br.com.ande.business.service.impl.SessionManagerServiceImpl;
import br.com.ande.common.RegisterResultListener;
import br.com.ande.model.Session;
import br.com.ande.model.User;
import br.com.ande.ui.presenter.ProfilePresenter;
import br.com.ande.ui.view.ProfileView;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class ProfilePresenterImpl implements ProfilePresenter, RegisterResultListener {

    private User                    user;
    private ProfileView             view;
    private RegisterUserService     service;
    private SessionManagerService   sessionManagerService;

    public ProfilePresenterImpl(ProfileView view){
        this.view = view;
        init();
    }

    @Override
    public void init() {
        this.service                = new RegisterUserServiceImpl();
        this.sessionManagerService  = new SessionManagerServiceImpl();

        Session session = this.sessionManagerService.getCurrentSession();

        if(session != null && session.getUser() != null){
            this.view.setDataInfoUser(session.getUser());
        }
    }

    @Override
    public void tryAgain() {

    }

    @Override
    public void goBack() {

    }

    @Override
    public void sendToRegister(User user) {
        view.showLoading();
        this.user     = user;

        if(validateRegisterData()){

            service.registerClient(user, this);
        }else{
            view.hideLoading();
        }
    }

    @Override
    public void sendToUpdate(User user) {
        view.showLoading();
        this.user     = user;

        if(isValidEmail()){

            service.updateClient(user, this);
        }else{
            view.hideLoading();
        }
    }

    @Override
    public void updateImagemUser(User user) {
        this.sessionManagerService.updateCurrentSession(user);
    }

    @Override
    public boolean validateRegisterData() {

        if(this.user.getName().isEmpty()){
            view.setNameEmptyError();
            return false;
        }else{
            view.setNomeDefaultState();
        }

        if(this.user.getBirthdate().isEmpty()){
            view.setBirthdateEmptyError();
            return false;
        }else{
            if(!this.user.getBirthdate().equalsIgnoreCase("dd/mm/yyyy")) {
                view.setBirthdateDefaultState();
            }else {
                view.setBirthdateEmptyError();
                return false;
            }
        }

        if(this.user.getEmail().isEmpty()) {
            view.setEmailEmptyError();
            return false;
        }else if(!isValidEmail()){
            view.setEmailFormatError();
            return false;
        }else{
            view.setEmailDefaultState();
        }

        return true;
    }

    @Override
    public boolean isValidEmail() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()){
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess(User user) {
        view.hideLoading();
        sessionManagerService.createNewSession(user);
        view.showSuccessDialog();
    }

    @Override
    public void onError(String title, String msg) {
        view.hideLoading();
//        view.showDialogError(error);
    }

}

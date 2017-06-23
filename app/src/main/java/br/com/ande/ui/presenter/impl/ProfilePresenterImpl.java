package br.com.ande.ui.presenter.impl;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.ande.Ande;
import br.com.ande.business.service.RegisterUserService;
import br.com.ande.business.service.SessionManagerService;
import br.com.ande.business.service.impl.RegisterUserServiceImpl;
import br.com.ande.business.service.impl.SessionManagerServiceImpl;
import br.com.ande.common.RegisterResultListener;
import br.com.ande.dao.firebase.UserDAO;
import br.com.ande.model.Session;
import br.com.ande.model.User;
import br.com.ande.ui.presenter.ProfilePresenter;
import br.com.ande.ui.view.ProfileView;
import br.com.ande.util.Utils;

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
    private DatabaseReference       databaseReference;

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
    public void createNewUserIntoFireBase() {

        if(validateRegisterData()){

            databaseReference = FirebaseDatabase.getInstance().getReference(Ande.usersData);
            String uid = databaseReference.push().getKey();

            user.setUid(uid);

            UserDAO userDAO = new UserDAO(uid, user.getEmail(), Utils.md5(user.getEmail()));
            databaseReference.child(uid).setValue(userDAO);

            service.registerClient(user, ProfilePresenterImpl.this);
        }else{
            view.hideLoading();
        }
    }

    @Override
    public void updateUserIntoFirebase() {

        if(validateRegisterData()){

            UserDAO userDAO = new UserDAO(user.getUid(), user.getEmail(), Utils.md5(user.getEmail()));

            databaseReference = FirebaseDatabase.getInstance().getReference(Ande.usersData).child(user.getUid());
            databaseReference.setValue(userDAO);

            service.registerClient(user, ProfilePresenterImpl.this);
        }else{
            view.hideLoading();
        }
    }

    @Override
    public void requestUserUid(String oldEmail) {

        Firebase ref    = new Firebase(Ande.usersUriData);
        Query query     = ref.orderByChild("email").equalTo(oldEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getValue() != null) {
                    if(snapshot.getChildrenCount() > 1) {
                        view.hideLoading();
                        view.setEmailRegisteredError();
                    }else{
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            UserDAO dao = snap.getValue(UserDAO.class);
                            if(user.getUid() != null && user.getUid().equalsIgnoreCase(dao.getUserId())) {

                                user.setUid(dao.getUserId());
                                updateUserIntoFirebase();
                                continue;
                            }else{
                                view.hideLoading();
                                view.setEmailRegisteredError();
                                continue;
                            }
                        }
                    }

                }else{
                    createNewUserIntoFireBase();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    public void sendToRegister(User user, String oldEmail) {
        view.showLoading();
        this.user = user;

        requestUserUid(oldEmail);
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

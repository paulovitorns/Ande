package br.com.ande.ui.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.com.ande.R;
import br.com.ande.model.User;
import br.com.ande.ui.presenter.ProfilePresenter;
import br.com.ande.ui.presenter.impl.ProfilePresenterImpl;
import br.com.ande.ui.view.DashBoardView;
import br.com.ande.ui.view.ProfileView;
import br.com.ande.ui.view.component.CustomDialog;
import br.com.ande.ui.view.component.DateValidateWatcher;
import br.com.ande.ui.view.component.SimpleValidateWatcher;
import br.com.ande.util.EditTextValidadeUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class ProfileFragment extends Fragment implements ProfileView {

    @Bind(R.id.edtLayoutName)       TextInputLayout edtLayoutName;
    @Bind(R.id.edtLayoutBirth)      TextInputLayout edtLayoutBirth;
    @Bind(R.id.edtLayoutEmail)      TextInputLayout edtLayoutEmail;

    @Bind(R.id.edtName)             EditText    edtName;
    @Bind(R.id.edtBirth)            EditText    edtBirth;
    @Bind(R.id.edtEmail)            EditText    edtEmail;


    private User                user;
    private boolean             isFirstChange = true;
    private ProfilePresenter    presenter;

    public ProfileFragment(){

    }

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        edtName.addTextChangedListener(new SimpleValidateWatcher(edtName, edtLayoutName, R.string.error_empty_name, getContext()));
        edtBirth.addTextChangedListener(new DateValidateWatcher(edtBirth, edtLayoutBirth, R.string.error_empty_birth, getContext()));

        user = new User();
        presenter = new ProfilePresenterImpl(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setDataInfoUser(User user) {

        if(!user.getName().isEmpty())
            edtName.setText(user.getName());

        if(!user.getBirthdate().isEmpty())
            edtBirth.setText(user.getBirthdate());

        if(!user.getEmail().isEmpty())
            edtEmail.setText(user.getEmail());
    }

    @OnClick(R.id.btnSave)
    @Override
    public void onClickBtnSave() {

        user.setName(edtName.getText().toString());
        user.setBirthdate(edtBirth.getText().toString());
        user.setEmail(edtEmail.getText().toString());

        presenter.sendToRegister(user);
    }

    @Override
    public void setNameEmptyError() {
        EditTextValidadeUtils.setErrorToView(edtName, getContext());
        edtLayoutName.setErrorEnabled(true);
        edtLayoutName.setError(getString(R.string.error_empty_name));
    }

    @Override
    public void setBirthdateEmptyError() {
        EditTextValidadeUtils.setErrorToView(edtBirth, getContext());
        edtLayoutBirth.setErrorEnabled(true);
        edtLayoutBirth.setError(getString(R.string.error_empty_birth));
    }

    @Override
    public void setEmailEmptyError() {
        EditTextValidadeUtils.setErrorToView(edtEmail, getContext());
        edtLayoutEmail.setErrorEnabled(true);
        edtLayoutEmail.setError(getString(R.string.error_empty_email));
    }

    @Override
    public void setEmailFormatError() {
        EditTextValidadeUtils.setErrorToView(edtEmail, getContext());
        edtLayoutEmail.setErrorEnabled(true);
        edtLayoutEmail.setError(getString(R.string.error_format_email));
    }

    @Override
    public void setNomeDefaultState() {
        EditTextValidadeUtils.setNormalStateToView(edtName, getContext());
        edtLayoutName.setErrorEnabled(false);
    }

    @Override
    public void setBirthdateDefaultState() {
        EditTextValidadeUtils.setNormalStateToView(edtBirth, getContext());
        edtLayoutBirth.setErrorEnabled(false);
    }

    @Override
    public void setEmailDefaultState() {
        EditTextValidadeUtils.setNormalStateToView(edtEmail, getContext());
        edtLayoutEmail.setErrorEnabled(false);
    }

    @Override
    public void showSuccessDialog() {

        String title    = "Olá, "+user.getName()+"!";
        String msg      = "Seus dados foram salvos com sucesso.";

        CustomDialog customDialog = new CustomDialog(getContext(), title, msg);
        customDialog.show();
    }

    @Override
    public Activity getContext() {
        return getActivity();
    }

    @Override
    public void onBackPressed() {
        ((DashBoardView)getActivity()).onBackPressed();
    }

    @Override
    public void showLoading() {
        ((DashBoardView)getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((DashBoardView)getActivity()).hideLoading();
    }

}

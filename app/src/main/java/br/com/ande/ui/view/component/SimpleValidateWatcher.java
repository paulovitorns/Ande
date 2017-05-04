package br.com.ande.ui.view.component;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import br.com.ande.util.EditTextValidadeUtils;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class SimpleValidateWatcher implements TextWatcher {

    private EditText        editText;
    private TextInputLayout inputLayout;
    private int             idStringValidate;
    private Context         context;

    public SimpleValidateWatcher(EditText editText, TextInputLayout inputLayout, int idStringValidate, Context context) {
        this.editText           = editText;
        this.inputLayout        = inputLayout;
        this.idStringValidate   = idStringValidate;
        this.context            = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(!editText.getText().toString().isEmpty()){
            EditTextValidadeUtils.setNormalStateToView(editText, context);
            inputLayout.setErrorEnabled(false);
        }else{
            EditTextValidadeUtils.setErrorToView(editText, context);
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(context.getString(this.idStringValidate));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

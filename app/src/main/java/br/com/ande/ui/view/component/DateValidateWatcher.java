package br.com.ande.ui.view.component;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;

import br.com.ande.util.EditTextValidadeUtils;


/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class DateValidateWatcher implements TextWatcher {

    private EditText        editText;
    private TextInputLayout inputLayout;
    private int             idStringValidate;
    private Context         context;
    private String          current = "";
    private String          ddmmyyyy = "ddmmyyyy";
    private Calendar        cal = Calendar.getInstance();

    public DateValidateWatcher(EditText editText, TextInputLayout inputLayout, int idStringValidate, Context context) {
        this.editText           = editText;
        this.inputLayout        = inputLayout;
        this.idStringValidate   = idStringValidate;
        this.context            = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int a, int a1, int a2) {

        if(!editText.getText().toString().isEmpty()){

            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]", "");
                String cleanC = current.replaceAll("[^\\d.]", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }

                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + ddmmyyyy.substring(clean.length());
                }else{

                    int day  = Integer.parseInt(clean.substring(0,2));
                    int mon  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    if(mon > 12) mon = 12;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<1900)?1900:(year>2100)?2100:year;
                    cal.set(Calendar.YEAR, year);

                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                editText.setText(current);
                editText.setSelection(sel < current.length() ? sel : current.length());
            }

            EditTextValidadeUtils.setNormalStateToView(editText, context);
            inputLayout.setErrorEnabled(false);

        }else{
            EditTextValidadeUtils.setErrorToView(editText, context);
            inputLayout.setErrorEnabled(true);
            inputLayout.setError(context.getString(this.idStringValidate));
            return;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

}

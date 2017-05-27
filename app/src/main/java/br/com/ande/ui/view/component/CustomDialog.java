package br.com.ande.ui.view.component;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import br.com.ande.R;
import br.com.ande.ui.view.LauchView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class CustomDialog implements View.OnClickListener{

    @Bind(R.id.txtTitleDialog)  TextView    txtTitleDialog;
    @Bind(R.id.txtTextDialog)   TextView    txtDescDialog;
    @Bind(R.id.btnOk)           Button      btnOk;

    private Dialog          dialog;
    private Context         context;
    private String          title;
    private String          msg;
    private LauchView       lauchView;

    public CustomDialog(Context context, String title, String msg){
        this.context    = context;
        this.title      = title;
        this.msg        = msg;

        this.create();
    }

    public CustomDialog(Context context, String title, String msg, LauchView lauchView){
        this.context    = context;
        this.title      = title;
        this.msg        = msg;
        this.lauchView  = lauchView;

        this.create();
    }
    private void create(){

        dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_fragment);
        dialog.setCancelable(true);

        ButterKnife.bind(this, dialog);

        txtTitleDialog.setText(title);
        txtDescDialog.setText(msg);

        btnOk.setOnClickListener(this);
    }

    public void show(){
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        dialog.cancel();
        if(lauchView != null){
            lauchView.showDialogPermission();
        }
    }
}
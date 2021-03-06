package br.com.ande.ui.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import br.com.ande.BuildConfig;
import br.com.ande.R;
import br.com.ande.model.User;
import br.com.ande.ui.presenter.ProfilePresenter;
import br.com.ande.ui.presenter.impl.ProfilePresenterImpl;
import br.com.ande.ui.view.DashBoardView;
import br.com.ande.ui.view.ProfileView;
import br.com.ande.ui.view.component.CustomDialog;
import br.com.ande.ui.view.component.DateValidateWatcher;
import br.com.ande.ui.view.component.SimpleValidateWatcher;
import br.com.ande.util.AnalyticsUtils;
import br.com.ande.util.EditTextValidadeUtils;
import br.com.ande.util.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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

    @Bind(R.id.imgProfile)          CircleImageView imageView;

    private User                user;
    private ProfilePresenter    presenter;
    static final int REQUEST_IMAGE_CAPTURE  = 1;
    static final int REQUEST_RESULT_CODE    = -1;

    private int targetW;
    private int targetH;

    public ProfileFragment(){

    }

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        HashMap<String, String> track = new HashMap<>();
        track.put(AnalyticsUtils.event_track, AnalyticsUtils.screen_profile_dash);
        AnalyticsUtils.logScreenViewEvent(track, getContext());

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        targetH = Utils.dp2px((int) (getResources().getDimension(R.dimen.img_profile_size) / getResources().getDisplayMetrics().density));
        targetW = Utils.dp2px((int) (getResources().getDimension(R.dimen.img_profile_size) / getResources().getDisplayMetrics().density));

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

        this.user = user;

        if(user.getName() != null || !user.getName().isEmpty())
            edtName.setText(user.getName());

        if(user.getBirthdate() != null || !user.getBirthdate().isEmpty())
            edtBirth.setText(user.getBirthdate());

        if(user.getEmail() != null || !user.getEmail().isEmpty())
            edtEmail.setText(user.getEmail());

        if(user.getImgNameResource() != null){
            if(!user.getImgNameResource().isEmpty())
                setPic();
        }

    }

    @OnClick(R.id.btnSave)
    @Override
    public void onClickBtnSave() {

        String oldEmail = (user.getEmail() == null || user.getEmail().equalsIgnoreCase("")) ? edtEmail.getText().toString() : user.getEmail();

        user.setName(edtName.getText().toString());
        user.setBirthdate(edtBirth.getText().toString());
        user.setEmail(edtEmail.getText().toString());

        presenter.sendToRegister(user, oldEmail);
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
    public void setEmailRegisteredError() {
        EditTextValidadeUtils.setErrorToView(edtEmail, getContext());
        edtLayoutEmail.setErrorEnabled(true);
        edtLayoutEmail.setError(getString(R.string.error_registered_email));
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

    @OnClick(R.id.fabCamera)
    @Override
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID+".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "profile_image";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        user.setImgNameResource(image.getAbsolutePath());

        return image;
    }

    @Override
    public void setPic() {
        // Get the dimensions of the View

        if(targetW == 0)
            targetW = imageView.getMeasuredWidth();

        if(targetH == 0)
            targetH = imageView.getMeasuredHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(user.getImgNameResource(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(user.getImgNameResource(), bmOptions);

        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == REQUEST_RESULT_CODE) {

            if(user.getEmail() != null){
                if(!user.getEmail().isEmpty())
                    presenter.updateImagemUser(user);
            }

            setPic();
        }
    }

}

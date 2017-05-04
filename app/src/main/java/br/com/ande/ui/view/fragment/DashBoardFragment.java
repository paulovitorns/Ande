package br.com.ande.ui.view.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.ande.R;
import br.com.ande.model.User;
import br.com.ande.ui.presenter.AndeDashPresenter;
import br.com.ande.ui.presenter.impl.AndeDashPresenterImpl;
import br.com.ande.ui.view.AndeDashView;
import br.com.ande.ui.view.DashBoardView;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public class DashBoardFragment extends Fragment implements AndeDashView {

    @Bind(R.id.txUserName)      TextView    txUserName;
    @Bind(R.id.txWalkRegister)  TextView    txWalkRegister;

    @Bind(R.id.imgProfile)      CircleImageView imageView;

    private int     targetW;
    private int     targetH;

    private AndeDashPresenter presenter;
    private User    user;

    public DashBoardFragment(){

    }

    public static DashBoardFragment newInstance(){
        return new DashBoardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ButterKnife.bind(this, view);

        targetH = dp2px((int) (getResources().getDimension(R.dimen.img_dash_size) / getResources().getDisplayMetrics().density));
        targetW = dp2px((int) (getResources().getDimension(R.dimen.img_dash_size) / getResources().getDisplayMetrics().density));

        this.presenter = new AndeDashPresenterImpl(this);
        this.user = new User();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showInfoUser(User user) {
        this.user = user;

        if(user.getName() != null || !user.getName().isEmpty())
            txUserName.setText("Olá, "+user.getName());
        else
            txUserName.setText("Olá, visitante!");

        String strWalk = txWalkRegister.getText().toString();
        strWalk = strWalk.replace("...", String.valueOf(10));
        txWalkRegister.setText(strWalk);

        if(user.getImgNameResource() != null){
            if(!user.getImgNameResource().isEmpty())
                setPic();
        }

    }

    @Override
    public void startWalkListeners() {

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

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}

package com.appler.universitysystem.ui.activity;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.appler.universitysystem.Global;
import com.appler.universitysystem.R;
import com.appler.universitysystem.api.FlagConstant;
import com.appler.universitysystem.base.BaseActivity;
import com.appler.universitysystem.presenter.MainPresenter;
import com.appler.universitysystem.utils.GlideUtils;
import com.appler.universitysystem.view.MainView;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

public class ShowFlowImgActivity extends BaseActivity implements MainView {

    @BindView(R.id.toolbar_info)
    Toolbar toolbarInfo;
    @BindView(R.id.pv_flowImg)
    PhotoView pv_flowImg;

    private String TAG = getClass().getSimpleName();

    @Override
    public void doBusiness(Context context) {
        toolbarInfo.setTitle("迎新流程");
        toolbarInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public int bindLayout() {
        return R.layout.activity_show_flow_img;
    }

    @Override
    public void getDZJG(String string) {

    }

    @Override
    public void getFlow(String string) {

    }

    @Override
    public void completeOneFlow(String string) {

    }

    @Override
    public void getMessageList(String string) {

    }

    @Override
    public void getFlowNavigation(String string) {

    }

    @Override
    public void getPoiAll(String string) {

    }

    @Override
    public void getPoiTypeList(String string) {

    }

    @Override
    public void getPoiByType(String string) {

    }

    @Override
    public void getQuanjingImgList(String string) {

    }

    @Override
    public void getQuanjingPointByName(String string) {

    }

    @Override
    public void getFlowPicture(String string) {
        String pictureUrl = Global.URL + string;
        Log.i(TAG, "getFlowPicture: pictureUrl=" + pictureUrl);

        //https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2342625776,3313071345&fm=26&gp=0.jpg
        GlideUtils.loadNet(pv_flowImg, pictureUrl, R.mipmap.login_bg);

    }

    @Override
    public void getApkTimeLimit(String string) {

    }

    @Override
    public void getQjTypeList(String string) {

    }

    @Override
    public void versionUpdate(String string) {

    }


    @Override
    protected void onResume() {
        super.onResume();

        MainPresenter mainPresenter = new MainPresenter(ShowFlowImgActivity.this);
        mainPresenter.getFlowPicture(FlagConstant.FLAG_FLOW_PICTURE);
    }
}

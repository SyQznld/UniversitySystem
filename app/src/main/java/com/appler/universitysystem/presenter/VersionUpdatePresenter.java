package com.appler.universitysystem.presenter;


import com.appler.universitysystem.base.BaseMvpPresenter;
import com.appler.universitysystem.model.im.IVersionUpdateModel;
import com.appler.universitysystem.model.VersionUpdateModel;
import com.appler.universitysystem.view.VersionUpdateView;


public class VersionUpdatePresenter extends BaseMvpPresenter {

    private String TAG = "VersionUpdatePresenter";
    private VersionUpdateView versionUpdateView;
    private VersionUpdateModel versionUpdateModel;

    public VersionUpdatePresenter(final VersionUpdateView versionUpdateView) {
        this.versionUpdateView = versionUpdateView;

        versionUpdateModel = new VersionUpdateModel(new IVersionUpdateModel() {
            @Override
            public void resetPassword(String string) {
                versionUpdateView.resetPassword(string);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }


        });
    }


    public void resetPassword(String flag, String userId,String oldpwd, String newpwd) {
        versionUpdateModel.resetPassword(compositeSubscription,flag,userId,oldpwd,newpwd);
    }

}

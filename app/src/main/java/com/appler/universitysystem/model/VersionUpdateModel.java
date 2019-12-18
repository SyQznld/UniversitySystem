package com.appler.universitysystem.model;

import android.util.Log;

import com.appler.universitysystem.base.BaseRetrofit;
import com.appler.universitysystem.model.im.IVersionUpdateModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class VersionUpdateModel {
    private String TAG = "VersionUpdateModel";
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IVersionUpdateModel iVersionUpdateModel;

    public VersionUpdateModel(IVersionUpdateModel iVersionUpdateModel) {
        this.iVersionUpdateModel = iVersionUpdateModel;
    }



    public void resetPassword(CompositeSubscription compositeSubscription, String flag, String userId, String oldpwd, String newpwd) {
        compositeSubscription.add(
                baseRetrofit.getApiService().resetPassword(flag,userId,oldpwd,newpwd)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "resetPassword onError: " + e.getMessage());
                        iVersionUpdateModel.resetPassword(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String s = responseBody.string();
                            Log.i(TAG, "resetPassword onNext: " + s);
                            iVersionUpdateModel.resetPassword(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }
}
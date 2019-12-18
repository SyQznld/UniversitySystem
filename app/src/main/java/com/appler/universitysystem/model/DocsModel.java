package com.appler.universitysystem.model;


import android.util.Log;

import com.appler.universitysystem.base.BaseRetrofit;
import com.appler.universitysystem.model.im.IDocsModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class DocsModel {

    private String TAG = this.getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IDocsModel iDocsModel;

    public DocsModel(IDocsModel iDocsModel) {
        this.iDocsModel = iDocsModel;
    }


    public void getDocsList(CompositeSubscription compositeSubscription, String flag) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getDocsList(flag)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getDocsList: " + e.getMessage());
                                iDocsModel.getDocsList(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getDocsList: " + string);
                                    iDocsModel.getDocsList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
        );
    }
    //流程文件  功能需求
    public void getFlowFile(CompositeSubscription compositeSubscription, String flag, String flowid) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getFlowFile(flag, flowid)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError: getFlowFile" + e.getMessage());
                                iDocsModel.getFlowFile(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext: getFlowFile" + string);
                                    iDocsModel.getFlowFile(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
        );
    }
}

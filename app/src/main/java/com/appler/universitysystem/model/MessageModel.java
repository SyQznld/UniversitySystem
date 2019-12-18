package com.appler.universitysystem.model;

import android.util.Log;

import com.appler.universitysystem.base.BaseRetrofit;
import com.appler.universitysystem.model.im.IMessageModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MessageModel {

    private String TAG = getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IMessageModel iMessageModel;

    public MessageModel(IMessageModel iMessageModel) {
        this.iMessageModel = iMessageModel;
    }

    public void getMessageList(CompositeSubscription compositeSubscription, String flag) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getMessageList(flag)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError getMessageList: " + e.getMessage());
                                iMessageModel.getMessageList(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    Log.i(TAG, "onNext getMessageList: " + string);
                                    iMessageModel.getMessageList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
        );
    }
}

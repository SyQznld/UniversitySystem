package com.appler.universitysystem.model;

import com.appler.universitysystem.base.BaseRetrofit;
import com.appler.universitysystem.model.im.IMainModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainModel {

    private String TAG = getClass().getSimpleName();
    private BaseRetrofit baseRetrofit = new BaseRetrofit();
    private IMainModel IMainModel;

    public MainModel(IMainModel IMainModel) {
        this.IMainModel = IMainModel;
    }

    //    机构设置 党政机关列表
    public void getDZJG(CompositeSubscription compositeSubscription, String flag) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getDZJGList(flag)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.getDZJG(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getDZJG(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }


    //    流程
    public void getFlow(CompositeSubscription compositeSubscription, String flag, String userId,String selectXiaoqu) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getFlowList(flag, userId,selectXiaoqu)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.getFlow(e.getMessage());
//                                idzjgModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getFlow(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }

    //    导航
    public void getFlownavigation(CompositeSubscription compositeSubscription, String flag, String flowid,String campusName) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getFlowNavigation(flag, flowid,campusName)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.getFlowNavigation(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getFlowNavigation(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }

    //    完成某个流程
    public void completeOneFlow(CompositeSubscription compositeSubscription, String flag, String userId, String flowId) {
        compositeSubscription.add(
                baseRetrofit.getApiService().completeOneFlow(flag, userId, flowId)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.completeOneFlow(e.getMessage());
//                                idzjgModel.onError();
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.completeOneFlow(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
        );
    }

    //    消息轮播
    public void messageCarousel(CompositeSubscription compositeSubscription, String flag) {
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
                                IMainModel.getMessageList(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getMessageList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }

    //    全景图片列表
    public void getQuanjingImgList(CompositeSubscription compositeSubscription, String flag,String qjType) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getQuanjingPicList(flag,qjType)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.getQuanjingImgList(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getQuanjingImgList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }

    //    全景点  根据名字
    public void getQuanjingPointByName(CompositeSubscription compositeSubscription, String flag, String name) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getQuanjingPointByName(flag, name)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.getQuanjingPointByName(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getQuanjingPointByName(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }

    //    poi全部数据
    public void getPoiAll(CompositeSubscription compositeSubscription, String flag) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getPoiAll(flag)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.getPoiAll(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getPoiAll(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }

    //    poi全部类型列表
    public void getPoiTypeList(CompositeSubscription compositeSubscription, String flag) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getPoiTypeList(flag)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.getPoiTypeList(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getPoiTypeList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }

    //    根据某一种类型获取对应数据列表
    public void getPoiByType(CompositeSubscription compositeSubscription, String flag, String type) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getPoiByType(flag, type)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.getPoiByType(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getPoiByType(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }

    //    流程图片
    public void getFlowPicture(CompositeSubscription compositeSubscription, String flag) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getFlowPicture(flag)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.getFlowPicture(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getFlowPicture(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }

    //    apk时间限制
    public void getApkTimeLimit(CompositeSubscription compositeSubscription, String flag) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getApkTimeLimit(flag)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.getApkTimeLimit(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getApkTimeLimit(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }



    //   全景类型列表
    public void getQjTypeList(CompositeSubscription compositeSubscription, String flag) {
        compositeSubscription.add(
                baseRetrofit.getApiService().getQjTypeList(flag)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.getQjTypeList(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.getQjTypeList(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }


    //   版本更新
    public void versionUpdate(CompositeSubscription compositeSubscription, String flag) {
        compositeSubscription.add(
                baseRetrofit.getApiService().versionUpdate(flag)
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                IMainModel.versionUpdate(e.getMessage());
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    IMainModel.versionUpdate(string);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })

        );
    }
}

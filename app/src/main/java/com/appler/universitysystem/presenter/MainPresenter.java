package com.appler.universitysystem.presenter;

import com.appler.universitysystem.base.BaseMvpPresenter;
import com.appler.universitysystem.model.MainModel;
import com.appler.universitysystem.model.im.IMainModel;
import com.appler.universitysystem.view.MainView;

public class MainPresenter extends BaseMvpPresenter {
    private String TAG = getClass().getSimpleName();
    private MainModel mainModel;
    private MainView mainView;

    public MainPresenter(final MainView mainView) {
        this.mainView = mainView;
        mainModel = new MainModel(new IMainModel() {
            @Override
            public void getDZJG(String string) {
                mainView.getDZJG(string);
            }

            @Override
            public void getFlow(String string) {
                mainView.getFlow(string);
            }

            @Override
            public void completeOneFlow(String string) {
                mainView.completeOneFlow(string);
            }

            @Override
            public void getMessageList(String string) {
                mainView.getMessageList(string);
            }

            @Override
            public void getFlowNavigation(String string) {
                mainView.getFlowNavigation(string);
            }


            @Override
            public void getPoiAll(String string) {
                mainView.getPoiAll(string);
            }

            @Override
            public void getPoiTypeList(String string) {
                mainView.getPoiTypeList(string);
            }

            @Override
            public void getPoiByType(String string) {
                mainView.getPoiByType(string);
            }

            @Override
            public void getQuanjingImgList(String string) {
                mainView.getQuanjingImgList(string);
            }

            @Override
            public void getQuanjingPointByName(String string) {
                mainView.getQuanjingPointByName(string);
            }

            @Override
            public void getFlowPicture(String string) {
                mainView.getFlowPicture(string);
            }

            @Override
            public void getApkTimeLimit(String string) {
                mainView.getApkTimeLimit(string);
            }

            @Override
            public void getQjTypeList(String string) {
                mainView.getQjTypeList(string);
            }

            @Override
            public void versionUpdate(String string) {
                mainView.versionUpdate(string);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {

            }
        });
    }

    public void getDZJG(String flag) {
        mainModel.getDZJG(compositeSubscription, flag);
    }

    //流程
    public void getFlowList(String flag, String userId,String selectXiaoqu) {
        mainModel.getFlow(compositeSubscription, flag, userId,selectXiaoqu);
    }

    //导航漫游
    public void getFlownavigation(String flag, String userId,String campusName) {
        mainModel.getFlownavigation(compositeSubscription, flag, userId,campusName);
    }

    //完成某个流程
    public void completeOneFlow(String flag, String userId, String flowId) {
        mainModel.completeOneFlow(compositeSubscription, flag, userId, flowId);
    }

    //消息轮播
    public void messageCarousel(String flag) {
        mainModel.messageCarousel(compositeSubscription, flag);
    }

    //全景图片
    public void getQuanjingImgList(String flag,String qjType) {
        mainModel.getQuanjingImgList(compositeSubscription, flag,qjType);
    }

    //全景点  根据名字匹配
    public void getQuanjingPointByName(String flag, String name) {
        mainModel.getQuanjingPointByName(compositeSubscription, flag, name);
    }


    //poi全部数据
    public void getPoiAll(String flag) {
        mainModel.getPoiAll(compositeSubscription, flag);
    }

    //poi全部类型列表
    public void getPoiTypeList(String flag) {
        mainModel.getPoiTypeList(compositeSubscription, flag);
    }

    //根据某一种类型获取对应数据列表
    public void getPoiByType(String flag, String type) {
        mainModel.getPoiByType(compositeSubscription, flag, type);
    }

    //流程图片
    public void getFlowPicture(String flag) {
        mainModel.getFlowPicture(compositeSubscription, flag);
    }

    //apk时间限制
    public void getTimeLimit(String flag) {
        mainModel.getApkTimeLimit(compositeSubscription, flag);
    }
    //全景类型列表
    public void getQjTypeList(String flag) {
        mainModel.getQjTypeList(compositeSubscription, flag);
    }



        //版本更新
    public void versionUpdate(String flag) {
        mainModel.versionUpdate(compositeSubscription, flag);
    }





}

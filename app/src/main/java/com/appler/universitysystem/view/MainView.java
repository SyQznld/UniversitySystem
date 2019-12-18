package com.appler.universitysystem.view;

import com.appler.universitysystem.base.IView;

public interface MainView extends IView {
    void getDZJG(String string);

    void getFlow(String string);

    void completeOneFlow(String string);

    void getMessageList(String string);

    void getFlowNavigation(String string);


    void getPoiAll(String string);

    void getPoiTypeList(String string);

    void getPoiByType(String string);

    void getQuanjingImgList(String string);

    void getQuanjingPointByName(String string);

    void getFlowPicture(String string);

    void getApkTimeLimit(String string);


    void getQjTypeList(String string);


    void versionUpdate(String string);

}

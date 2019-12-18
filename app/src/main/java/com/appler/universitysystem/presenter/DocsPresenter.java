package com.appler.universitysystem.presenter;


import com.appler.universitysystem.base.BaseMvpPresenter;
import com.appler.universitysystem.model.DocsModel;
import com.appler.universitysystem.model.im.IDocsModel;
import com.appler.universitysystem.view.DocsView;

public class DocsPresenter extends BaseMvpPresenter {
    private String TAG = this.getClass().getSimpleName();
    private DocsModel docsModel;
    private DocsView docsView;


    public DocsPresenter(final DocsView docsView) {
        this.docsView = docsView;
        docsModel = new DocsModel(new IDocsModel() {
            @Override
            public void getDocsList(String string) {
                docsView.getDocsList(string);
            }

            @Override
            public void getFlowFile(String string) {
                docsView.getFlowFile(string);
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


    public void getDocsList(String flag) {
        docsModel.getDocsList(compositeSubscription, flag);
    }

    //流程文件      迎新功能需求
    public void getFlowFile(String flag, String flowid) {
        docsModel.getFlowFile(compositeSubscription, flag, flowid);
    }
}

package com.appler.universitysystem.presenter;

import com.appler.universitysystem.base.BaseMvpPresenter;
import com.appler.universitysystem.model.MessageModel;
import com.appler.universitysystem.model.im.IMessageModel;
import com.appler.universitysystem.view.MessageView;


public class MessagePresenter extends BaseMvpPresenter {
    private MessageView messageView;
    private MessageModel messageModel;

    public MessagePresenter(final MessageView messageView) {
        this.messageView = messageView;
        messageModel = new MessageModel(new IMessageModel() {
            @Override
            public void getMessageList(String string) {
                messageView.getMessageList(string);
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

    public void getMessageList(String flag) {
        messageModel.getMessageList(compositeSubscription, flag);
    }
}

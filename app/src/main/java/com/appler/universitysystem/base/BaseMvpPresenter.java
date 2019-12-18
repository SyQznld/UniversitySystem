package com.appler.universitysystem.base;

import rx.subscriptions.CompositeSubscription;


public class BaseMvpPresenter<V> implements IMvpPresenter<V> {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    private V mvpView;

    @Override
    public void attachView(V view) {
        mvpView = view;
        compositeSubscription = new CompositeSubscription();

    }

    @Override
    public void detachView() {
        mvpView = null;
        compositeSubscription.clear();
        compositeSubscription = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public V getMvpView() {
        return mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached())
            throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }


}

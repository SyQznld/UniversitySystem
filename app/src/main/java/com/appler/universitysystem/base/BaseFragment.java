package com.appler.universitysystem.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appler.universitysystem.Global;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements IView {

    private View view;
    private Activity activity;
    private Unbinder bind;
    private BaseMvpPresenter baseMvpPresenter;
    private MaterialDialog materialDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getFragmentView(), container, false);
        activity = getActivity();
        baseMvpPresenter = new BaseMvpPresenter();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        initFragmentView();
        doFragmentBusiness();


    }

    protected abstract void doFragmentBusiness();

    protected abstract void initFragmentView();

    public abstract int getFragmentView();


    @Override
    public void setState(int state) {
        switch (state) {
            case Global.LOADING_STATE:
                materialDialog = new MaterialDialog.Builder(activity)
                        .content("正在加载")
                        .progress(true, 0)
                        .progressIndeterminateStyle(false)
                        .show();
                break;
            case Global.LOADING_FAIL:
                if (materialDialog != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            materialDialog.dismiss();
                        }
                    }, 1000);
                }
                break;
            case Global.LOADING_SUCEESS:
                if (materialDialog != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            materialDialog.dismiss();
                        }
                    }, 1000);
                }
                break;
        }
    }

}

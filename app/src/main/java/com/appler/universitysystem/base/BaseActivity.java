package com.appler.universitysystem.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appler.universitysystem.Global;

import butterknife.ButterKnife;


public abstract class BaseActivity extends FragmentActivity implements IView {

    private String TAG = getClass().getSimpleName();
    private BaseMvpPresenter baseMvpPresenter;

    private MaterialDialog loadDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        ButterKnife.bind(this);
        baseMvpPresenter = new BaseMvpPresenter();
        doBusiness(this);
    }

    /**
     * 业务操作
     */
    public abstract void doBusiness(Context context);

    /**
     * 绑定布局
     * 抽象方法：必须实现的方法  返回数据不一样 必须实现的方法
     */
    public abstract int bindLayout();


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseMvpPresenter.detachView();
    }


    @Override
    public void setState(int state) {
        switch (state) {
            case Global.LOADING_STATE:
                loadDialog = new MaterialDialog.Builder(this)
                        .content("加载中")
                        .progress(true, 0)
                        .progressIndeterminateStyle(false)
                        .show();
                break;
            case Global.LOADING_FAIL:
                if (loadDialog != null) {
                    loadDialog.setContent("加载失败，请重新加载");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadDialog.dismiss();
                        }
                    }, 1000);
                }
                break;
            case Global.LOADING_SUCEESS:
                if (loadDialog != null) {
                    loadDialog.setContent("加载成功");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadDialog.dismiss();
                        }
                    }, 500);
                }
                break;
        }
    }
}

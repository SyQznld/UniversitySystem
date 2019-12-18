package com.appler.universitysystem.ui.pop;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.appler.universitysystem.Global;
import com.appler.universitysystem.R;
import com.appler.universitysystem.api.FlagConstant;
import com.appler.universitysystem.bean.PoiTypeData;
import com.appler.universitysystem.bean.QjTypeData;
import com.appler.universitysystem.presenter.MainPresenter;
import com.appler.universitysystem.ui.adapter.QjTypeListAdapter;
import com.appler.universitysystem.utils.RequestDialogUtils;
import com.appler.universitysystem.utils.ToastUtils;
import com.appler.universitysystem.view.MainView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示全景类型
 */
public class ShowQjTypePop extends PopupWindow implements MainView {

    private String TAG = getClass().getSimpleName();
    private List<PoiTypeData> poiTypeDataList = new ArrayList<>();
    private MainPresenter mainPresenter;

    private WebView webView;
    private Activity context;
    private TextView tv_qj;
    private Dialog requestDialog;
    private PopupWindow qjTypePop;

    private String quanjingtype;

    public ShowQjTypePop(Activity context, TextView tv_qj, WebView webView) {
        super(context);
        this.context = context;
        this.tv_qj = tv_qj;
        this.webView = webView;

        mainPresenter = new MainPresenter(this);
    }


    public void showQjTypePop(TextView tv_quanjing, String string) {
        if (!"".equals(string)) {

            Gson gson = new Gson();
            List<QjTypeData> qjTypeDataList = gson.fromJson(string, new TypeToken<List<QjTypeData>>() {
            }.getType());

            LayoutInflater layoutInflater = context.getLayoutInflater();
            final View view = layoutInflater.inflate(R.layout.qjtype_bottom_dialog, null);

            RecyclerView rv_qjtype = view.findViewById(R.id.rv_qjType);
            rv_qjtype.setLayoutManager(new LinearLayoutManager(context));
            QjTypeListAdapter qjTypeListAdapter = new QjTypeListAdapter(context, qjTypeDataList);
            rv_qjtype.setAdapter(qjTypeListAdapter);
            qjTypeListAdapter.setQjTypeItemClickListener(new QjTypeListAdapter.QjTypeItemClickListener() {
                @Override
                public void qjTypeItemClick(int position, QjTypeData qjTypeData) {
                    quanjingtype = qjTypeData.getQuanjingtype();
                    mainPresenter.getQuanjingImgList(FlagConstant.FLAG_QUANJING_IMAGELIST, quanjingtype);
                    requestDialog = RequestDialogUtils.createLoadingDialog(context, "加载中");
                    qjTypePop.dismiss();
                }
            });


            qjTypePop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
            qjTypePop.setBackgroundDrawable(new BitmapDrawable());
            qjTypePop.setOutsideTouchable(true);
            qjTypePop.setTouchable(true);
            qjTypePop.setAnimationStyle(R.style.QjTypeAnimationBottomFade);
            qjTypePop.showAtLocation(tv_quanjing, Gravity.BOTTOM, 0, 0);

        }
        
    }


    @Override
    public void setState(int state) {

    }

    @Override
    public void getDZJG(String string) {

    }

    @Override
    public void getFlow(String string) {

    }

    @Override
    public void completeOneFlow(String string) {

    }

    @Override
    public void getMessageList(String string) {

    }

    @Override
    public void getFlowNavigation(String string) {

    }


    @Override
    public void getPoiAll(String string) {

    }

    @Override
    public void getPoiTypeList(String string) {

    }

    @Override
    public void getPoiByType(String string) {
    }

    @Override
    public void getQuanjingImgList(String string) {
        if (Global.HOST_CLOSE.equals(string)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(context, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(context, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(context, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(context, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[{")) {
            RequestDialogUtils.closeDialog(requestDialog);
            ShowQuanjingPicPop quanjingPicPop = new ShowQuanjingPicPop(context, tv_qj,webView);
            quanjingPicPop.showQjPop(tv_qj, string, quanjingtype);
        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(context, "服务器连接错误");
        }
    }

    @Override
    public void getQuanjingPointByName(String string) {

    }

    @Override
    public void getFlowPicture(String string) {

    }

    @Override
    public void getApkTimeLimit(String string) {

    }

    @Override
    public void getQjTypeList(String string) {
        if (Global.HOST_CLOSE.equals(string)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(context, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(context, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(context, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(context, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[")) {
            RequestDialogUtils.closeDialog(requestDialog);

        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(context, "服务器连接错误");
        }
    }

    @Override
    public void versionUpdate(String string) {

    }

}

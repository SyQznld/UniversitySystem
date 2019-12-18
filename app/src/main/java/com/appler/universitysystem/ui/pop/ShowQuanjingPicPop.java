package com.appler.universitysystem.ui.pop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.appler.universitysystem.Global;
import com.appler.universitysystem.R;
import com.appler.universitysystem.api.FlagConstant;
import com.appler.universitysystem.bean.QuanjingImageData;
import com.appler.universitysystem.presenter.MainPresenter;
import com.appler.universitysystem.ui.activity.LoadUrlActivity;
import com.appler.universitysystem.ui.adapter.QuanjingPicAdapter;
import com.appler.universitysystem.utils.RequestDialogUtils;
import com.appler.universitysystem.utils.ToastUtils;
import com.appler.universitysystem.view.MainView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 全景图片浏览 pop
 */
public class ShowQuanjingPicPop extends PopupWindow implements MainView {

    private String TAG = getClass().getSimpleName();
    private Activity context;
    private TextView tv_quanjing;
    private PopupWindow qjPicPop;
    private WebView webView;

    private MainPresenter mainPresenter;
    private Dialog requestDialog;


    public ShowQuanjingPicPop(Activity context, TextView tv_quanjing,WebView webView) {
        super(context);
        this.context = context;
        this.tv_quanjing = tv_quanjing;
        this.webView = webView;

        mainPresenter = new MainPresenter(this);
    }

    /**
     * 使用PopupWindow展示全景图片 后台接口
     */
    public void showQjPop(TextView tv_quanjing, String string, String qjType) {
        if (!"".equals(string)) {
            Gson gson = new Gson();
            List<QuanjingImageData> quanjingImageDataList = gson.fromJson(string, new TypeToken<List<QuanjingImageData>>() {
            }.getType());

            LayoutInflater layoutInflater = context.getLayoutInflater();
            final View inflate = layoutInflater.inflate(R.layout.quanjing_pic_layout, null);
            ImageView iv_qjpic_back = inflate.findViewById(R.id.iv_qjpic_back);
            final TextView tv_qjpic_type = inflate.findViewById(R.id.tv_qjpic_type);
            RecyclerView rv_qjPic = inflate.findViewById(R.id.rv_quanjing_pic);
            tv_qjpic_type.setText(qjType);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
            rv_qjPic.setLayoutManager(linearLayoutManager);

            QuanjingPicAdapter quanjingPicAdapter = new QuanjingPicAdapter(context, quanjingImageDataList);
            rv_qjPic.setAdapter(quanjingPicAdapter);
            quanjingPicAdapter.setQjPicItemClickListener(new QuanjingPicAdapter.QjPicItemClickListener() {
                @Override
                public void qjPicItemClick(int position, QuanjingImageData quanjingPicData) {
                    Intent intent = new Intent(context, LoadUrlActivity.class);
                    intent.putExtra("flag", 2);
                    intent.putExtra("qjmy_url", Global.QJMY_IMG_URL + quanjingPicData.getQuanjingid() + Global.QJMY_HOUZHUI);
                    context.startActivity(intent);
//                    qjPicPop.dismiss();
                }
            });

            iv_qjpic_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //返回主页显示全景类型
                    mainPresenter.getQjTypeList(FlagConstant.FLAG_QUANJING_TYPE_LIST);
                    requestDialog = RequestDialogUtils.createLoadingDialog(context, "加载中");
                    qjPicPop.dismiss();
                }
            });

            qjPicPop = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
            qjPicPop.setBackgroundDrawable(new BitmapDrawable());
            qjPicPop.setOutsideTouchable(true);
            qjPicPop.setTouchable(true);
            qjPicPop.setAnimationStyle(R.style.AnimationBottomFade);
            qjPicPop.showAtLocation(tv_quanjing, Gravity.BOTTOM, 0, 0);
        }
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
//            showQjTypeDialog(string);
            ShowQjTypePop showQjTypePop = new ShowQjTypePop(context, tv_quanjing, webView);
            showQjTypePop.showQjTypePop(tv_quanjing, string);
        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(context, "服务器连接错误");
        }
    }

    @Override
    public void versionUpdate(String string) {

    }

    @Override
    public void setState(int state) {

    }
}

package com.appler.universitysystem.ui.pop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.appler.universitysystem.Global;
import com.appler.universitysystem.R;
import com.appler.universitysystem.api.FlagConstant;
import com.appler.universitysystem.bean.PoiByType;
import com.appler.universitysystem.bean.PoiTypeData;
import com.appler.universitysystem.presenter.MainPresenter;
import com.appler.universitysystem.ui.adapter.PoiTypeAdapter;
import com.appler.universitysystem.utils.RequestDialogUtils;
import com.appler.universitysystem.utils.SavePoiTypeCheckedUtils;
import com.appler.universitysystem.utils.ToastUtils;
import com.appler.universitysystem.view.MainView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * poi的 pop
 */
public class ShowPoiTypePop extends PopupWindow implements MainView {

    private String TAG = getClass().getSimpleName();
    private List<PoiTypeData> poiTypeDataList = new ArrayList<>();
    private MainPresenter mainPresenter;

    private WebView webView;
    private Activity context;
    private TextView tv_poi;
    private Dialog requestDialog;

    private String poiType;
    private String chooseAll = "全选";
    private String chooseEast = "东校区";
    private String chooseWest = "西校区";

    public ShowPoiTypePop(Activity context, TextView tv_poi, WebView webView) {
        super(context);
        this.context = context;
        this.tv_poi = tv_poi;
        this.webView = webView;

        mainPresenter = new MainPresenter(this);
    }

    public void showPoiPopupWindow(View inflate, TextView tv_poi, String string, final SavePoiTypeCheckedUtils savePoiTypeCheckedUtils) {

        Log.i(TAG, "showPoiPopupWindow: type=" + string);

        PoiTypeData checkAll = new PoiTypeData(chooseAll);
        PoiTypeData checkEast = new PoiTypeData(chooseEast);
        PoiTypeData checkWest = new PoiTypeData(chooseWest);
        poiTypeDataList.add(checkAll);
        poiTypeDataList.add(checkEast);
        poiTypeDataList.add(checkWest);

        Gson gson = new Gson();
        List<PoiTypeData> poiTypeDataListGson = gson.fromJson(string, new TypeToken<List<PoiTypeData>>() {
        }.getType());

        poiTypeDataList.addAll(poiTypeDataListGson);

        final RecyclerView rv_poi_type = inflate.findViewById(R.id.rv_poi_type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        PoiTypeAdapter poiTypeAdapter = new PoiTypeAdapter(context, poiTypeDataList, savePoiTypeCheckedUtils);
        rv_poi_type.setLayoutManager(linearLayoutManager);
        rv_poi_type.setAdapter(poiTypeAdapter);

        //CheckBox的点击事件
        poiTypeAdapter.setCheckBoxItemClickListener(new PoiTypeAdapter.CheckBoxItemClickListener() {
            @Override
            public void checkBoxItemClick(int position, boolean isChecked, PoiTypeData poiTypeDataList) {
                String type = poiTypeDataList.getType();
                poiType = type;
                Log.i(TAG, "checkBoxItemClick: checkBoxText=" + position);

                if (isChecked) {
                    savePoiTypeCheckedUtils.poiTypeCheckedMap.put(position, true);

                    mainPresenter.getPoiByType(FlagConstant.FLAG_POI_BYTYPE, type);
                    requestDialog = RequestDialogUtils.createLoadingDialog(context, "加载中");

                } else {
                    savePoiTypeCheckedUtils.poiTypeCheckedMap.remove(position);

                    RequestDialogUtils.closeDialog(requestDialog);
                    webView.loadUrl("javascript:loadPoiMarkersByType('" + "" + "','" + poiType + "',false)");
                }

            }
        });

        //使用pop展示poi种类
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = manager.getDefaultDisplay().getHeight() / 2;
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;

        PopupWindow popupwindow = new PopupWindow(inflate, width, height, false);
//        popupwindow.setFocusable(true);
        popupwindow.setOutsideTouchable(true);
        popupwindow.setTouchable(true);
        popupwindow.setAnimationStyle(R.style.AnimationBottomFade);

        //获取pop的宽
        inflate.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupwindow.setContentView(inflate);
        int measuredWidth = popupwindow.getContentView().getMeasuredWidth();

        //左负右正      上负下正
        int x = -(measuredWidth - 6);
        int y = -(height / 2 + tv_poi.getHeight() / 2);

        popupwindow.showAsDropDown(tv_poi, x, y);
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
        Log.i(TAG, "getPoiByType: string=" + string);
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
        } else if (!"".equals(string) && string.contains("[") && string.contains("{") && string.contains("type")) {
            RequestDialogUtils.closeDialog(requestDialog);
            Gson gson = new Gson();
            List<PoiByType> poiByTypeList = gson.fromJson(string, new TypeToken<List<PoiByType>>() {
            }.getType());
            Set<String> typeSet = new HashSet<>();
            for (int i = 0; i < poiByTypeList.size(); i++) {
                String type = poiByTypeList.get(i).getType();
                typeSet.add(type);
            }

            Log.i(TAG, "getPoiByType: typeSet===" + typeSet);
            webView.loadUrl("javascript:loadPoiMarkersByType('" + string + "','" + poiType + "',true)");

        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(context, "服务器连接错误");
        }

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

    }

    @Override
    public void versionUpdate(String string) {

    }

}

package com.appler.universitysystem;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appler.universitysystem.api.FlagConstant;
import com.appler.universitysystem.base.BaseActivity;
import com.appler.universitysystem.bean.ApkTimeLimitData;
import com.appler.universitysystem.bean.MessageListData;
import com.appler.universitysystem.bean.POIData;
import com.appler.universitysystem.bean.QjPointData;
import com.appler.universitysystem.presenter.MainPresenter;
import com.appler.universitysystem.ui.activity.LoadUrlActivity;
import com.appler.universitysystem.ui.activity.MessageListActivity;
import com.appler.universitysystem.ui.activity.NoPermissionActivity;
import com.appler.universitysystem.ui.activity.POISearchActivity;
import com.appler.universitysystem.ui.activity.ShowFlowImgActivity;
import com.appler.universitysystem.ui.activity.UserActivity;
import com.appler.universitysystem.ui.pop.ShowDZJGPop;
import com.appler.universitysystem.ui.pop.ShowFlowPop;
import com.appler.universitysystem.ui.pop.ShowPoiTypePop;
import com.appler.universitysystem.ui.pop.ShowQjTypePop;
import com.appler.universitysystem.ui.pop.ShowQuanjingPicPop;
import com.appler.universitysystem.utils.CommonUtils;
import com.appler.universitysystem.utils.MainHandler;
import com.appler.universitysystem.utils.RequestDialogUtils;
import com.appler.universitysystem.utils.SavePoiTypeCheckedUtils;
import com.appler.universitysystem.utils.ToastUtils;
import com.appler.universitysystem.utils.customView.TipView;
import com.appler.universitysystem.version.VersionData;
import com.appler.universitysystem.version.VersionUpdateUtils;
import com.appler.universitysystem.view.MainView;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.m.permission.MPermissions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.wv_webView)
    WebView webView;
    @BindView(R.id.iv_imgUser)
    ImageView iv_imgUser;
    @BindView(R.id.fab_flowPic)
    FloatingActionButton fab_flowPic;
    @BindView(R.id.fab_nowLocation)
    FloatingActionButton fab_nowLocation;
    @BindView(R.id.fab_zoomIn)
    ImageButton fab_zoomIn;
    @BindView(R.id.fab_zoomOut)
    ImageButton fab_zoomOut;
    @BindView(R.id.fab_reset)
    FloatingActionButton fab_reset;
    @BindView(R.id.tv_jigou)
    TextView tv_jigou;
    @BindView(R.id.tv_flow)
    TextView tv_flow;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tip_view)
    TipView tipView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    Map<String, String> map = new HashMap<>();
    @BindView(R.id.ll_zzsf_wap)
    LinearLayout ll_zzsf_wap;
    @BindView(R.id.ll_wdt)
    LinearLayout ll_wdt;
    @BindView(R.id.iv_zzsf_wap)
    ImageView iv_zzsf_wap;
    @BindView(R.id.iv_wdt)
    ImageView iv__wdt;
    @BindView(R.id.tv_zzsf_wap)
    TextView tv_zzsf_wap;
    @BindView(R.id.tv_wdt)
    TextView tv_wdt;
    @BindView(R.id.tv_layers)
    TextView tv_layers;
    @BindView(R.id.ll_layer_type)
    LinearLayout ll_layer_type;
    @BindView(R.id.navigation_view)
    NavigationView navigation_view;
    @BindView(R.id.tv_quanjing)
    TextView tv_quanjing;

    @BindView(R.id.tv_poi)
    TextView tv_poi;
    @BindView(R.id.rl_main_page)
    RelativeLayout rl_main_page;


    private View inflate;   //poi模块界面

    private boolean flag1 = false;
    private boolean flag2 = false;
    private double longitude, latitude;
    private LocationManager lm;
    private String mapPath;
    private GestureDetector detector;

    private MainPresenter mainPresenter;

    private ShowFlowPop showFlowPop;//流程弹框pop
    private ShowDZJGPop dzjgPop;//党政机关 机构设置弹框pop
    private List<MessageListData> messageListDataList = new ArrayList<>();
    private Dialog requestDialog;
    private static final int REQUECT_CODE_SDCARD = 0;

    private SavePoiTypeCheckedUtils savePoiTypeCheckedUtils = new SavePoiTypeCheckedUtils();

    private List<String> xiaoquList;
    private String selectXiaoqu;

    private String quanjingtype;//选择的全景类型

    private boolean isLocation;  //是否定位


    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }


    @Override
    public void doBusiness(Context context) {

        mainPresenter = new MainPresenter(this);
        mainPresenter.getTimeLimit(FlagConstant.FLAG_APK_TIME_LIMIT);


        //版本更新
        mainPresenter.versionUpdate(FlagConstant.FLAG_VERSION_UPDATE);


        xiaoquList = new ArrayList<>();
        xiaoquList.add("东校区");
        xiaoquList.add("西校区");
        selectXiaoqu = xiaoquList.get(0);

    }

    @OnClick({R.id.fab_flowPic, R.id.fab_nowLocation, R.id.fab_zoomIn, R.id.fab_zoomOut, R.id.fab_reset,
            R.id.iv_imgUser, R.id.ll_search, R.id.btn_main_msg,
            R.id.tv_layers, R.id.tv_poi, R.id.tv_jigou, R.id.tv_flow, R.id.tv_quanjing,
            R.id.tv_search, R.id.ll_zzsf_wap, R.id.ll_wdt})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.fab_nowLocation: //定位
                if (isLocation) {
//                    clearMark();
                    webView.loadUrl("javascript:clearNowLocationMarker()");
                    isLocation = false;
                } else {
                    if (!"4.9E-324".equals(String.valueOf(latitude)) && !"4.9E-324".equals(String.valueOf(longitude)) && 0.0 != longitude && 0.0 != latitude) {
                        webView.loadUrl("javascript:WGS84Tomap('" + longitude + "','" + latitude + "')");
                    } else {
                        ToastUtils.showShortToast(MainActivity.this, "暂无位置信息,请到宽阔位置获取位置");
                    }
                    isLocation = true;
                }

                break;
            case R.id.fab_flowPic: //流程图显示
//                clearMark();
                startActivity(new Intent(MainActivity.this, ShowFlowImgActivity.class));
                break;

            case R.id.fab_zoomIn: //放大
//                clearMark();

                webView.loadUrl("javascript:fangda()");
                break;

            case R.id.fab_zoomOut: //缩小
//                clearMark();

                webView.loadUrl("javascript:suoxiao()");
                break;
            case R.id.fab_reset: //复位
//                clearMark();

                webView.loadUrl("javascript:setMapCenter()");
                break;
            case R.id.iv_imgUser: //跳转用户信息界面
                clearMark();

                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_search: //POI点搜索  模糊搜索
                clearMark();
                mainPresenter.getPoiAll(FlagConstant.FLAG_POI_ALL);
                requestDialog = RequestDialogUtils.createLoadingDialog(MainActivity.this, "加载中");
                break;
            case R.id.btn_main_msg: //消息列表
//                clearMark();

                Intent msgIntent = new Intent(MainActivity.this, MessageListActivity.class);
                startActivity(msgIntent);
                break;
            case R.id.tv_layers:        //图层列表
                drawer_layout.openDrawer(GravityCompat.END);
                break;
            case R.id.tv_poi:        //poi点种类
                clearMark();
                mainPresenter.getPoiTypeList(FlagConstant.FLAG_POI_TYPELIST);
                requestDialog = RequestDialogUtils.createLoadingDialog(MainActivity.this, "加载中");
                break;
            case R.id.tv_quanjing:       //全景
                clearMark();

                mainPresenter.getQjTypeList(FlagConstant.FLAG_QUANJING_TYPE_LIST);
                requestDialog = RequestDialogUtils.createLoadingDialog(MainActivity.this, "加载中");
                break;
            case R.id.tv_jigou:        //机构设置
                clearMark();

                dzjgPop = new ShowDZJGPop(MainActivity.this, tv_jigou, webView);
                mainPresenter.getDZJG(FlagConstant.FLAG_DZJG);
                requestDialog = RequestDialogUtils.createLoadingDialog(MainActivity.this, "加载中");
                break;
            case R.id.tv_flow:        //流程选择
                clearMark();

                /**
                 * 点击某一流程后选择东西校区
                 * */

                new MaterialDialog.Builder(MainActivity.this)
                        .title(R.string.chooseXiaoqu)
                        .items(R.array.chooseXiaoquArr)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                selectXiaoqu = xiaoquList.get(which);
                                showFlowPop = new ShowFlowPop(MainActivity.this, webView, tv_flow, selectXiaoqu);
                                return false;
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {
                                } else if (which == DialogAction.NEGATIVE) {
                                    dialog.dismiss();
                                }
                            }
                        })
                        .positiveText("确定")
                        .negativeText("取消")
                        .show();
                break;
            case R.id.ll_zzsf_wap:    //郑州师范切片
                showZZSFLayer();
                break;
            case R.id.ll_wdt:      //微地图
                showWDTLayer();
                break;

        }
    }


    //清楚mark点
    private void clearMark() {
        webView.loadUrl("javascript:clearPoiMarker()"); //清空poi点搜索关联的marker点
        webView.loadUrl("javascript:clearDZJGMarker()");    //清空机构关联的marker点
        webView.loadUrl("javascript:clearFlowFloorMarker()");  //清空流程关联楼栋marker点
        webView.loadUrl("javascript:clearRoadTrack()");  //清空上一个流程关联的道路线
//      webView.loadUrl("javascript:unloadPoiTypeMarkers()");  //清空poi点关联的种类marker点

    }


    /**
     * 图层类型的点击事件，判断是否有网络连接
     */
    //    郑州师范切片 影像
    private void showZZSFLayer() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable()) {
            if (flag1 == false) {
                iv_zzsf_wap.setBackgroundResource(R.mipmap.btn_yx_pressed);
                tv_zzsf_wap.setTextColor(Color.parseColor("#1E9FFF"));
                flag1 = true;
                map.put("1", Global.YX_ZZSF);
                loadLayer(map.get("1"));
            } else {
                iv_zzsf_wap.setBackgroundResource(R.mipmap.btn_yx_normal);
                tv_zzsf_wap.setTextColor(Color.parseColor("#444444"));
                flag1 = false;
                unLoadLayer(map.get("1"));
                map.put("1", "");
            }
        } else {
            Toast.makeText(MainActivity.this, "连接失败请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    //    微地图
    private void showWDTLayer() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            if (flag2 == false) {
                iv__wdt.setBackgroundResource(R.mipmap.btn_dl_pressed);
                tv_wdt.setTextColor(Color.parseColor("#1E9FFF"));
                iv__wdt.setPressed(true);
                flag2 = true;
                map.put("2", Global.YX_WDT);
                loadLayer(map.get("2"));
            } else {
                iv__wdt.setBackgroundResource(R.mipmap.btn_dl_normal);
                tv_wdt.setTextColor(Color.parseColor("#444444"));
                iv__wdt.setPressed(false);
                flag2 = false;
                unLoadLayer(map.get("2"));
                map.put("2", "");
            }
        } else {
            Toast.makeText(MainActivity.this, "连接失败请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Global.REQUEST_CODE_POI:

                    POIData poiData = data.getParcelableExtra("poiData");
                    String name = poiData.getName();
                    String type = poiData.getType();

                    POIData.GeomBean poiGeom = data.getParcelableExtra("poiGeom");
                    String x = poiGeom.getX();
                    String y = poiGeom.getY();

                    webView.loadUrl("javascript:loadSinglePoi('" + x + "','" + y + "','" + name + "','" + type + "')");

                    break;
            }
        }
    }


    @Override
    public void getDZJG(String string) {
        Log.i(TAG, "getDZJG: string====" + string);
        if (Global.HOST_CLOSE.equals(string)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[")) {
            RequestDialogUtils.closeDialog(requestDialog);
            dzjgPop.showDzjgPopupWindow(tv_jigou, string);
        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(MainActivity.this, "服务器连接错误");
        }
    }

    @Override
    public void getFlow(String string) {

    }

    @Override
    public void completeOneFlow(String string) {

    }

    @Override
    public void getMessageList(String string) {
        if (Global.HOST_CLOSE.equals(string)) {
            Toast.makeText(MainActivity.this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            Toast.makeText(MainActivity.this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            Toast.makeText(MainActivity.this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            Toast.makeText(MainActivity.this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[{")) {
            messageListDataList = new Gson().fromJson(string, new TypeToken<List<MessageListData>>() {
            }.getType());
            List<MessageListData> tips = new ArrayList<>();
            for (int i = 0; i < messageListDataList.size(); i++) {
                MessageListData messageListData = messageListDataList.get(i);
                int frequency = messageListData.getFrequency();
                for (int j = 0; j < frequency; j++) {
                    tips.add(messageListData);
                }
            }

            //新闻轮播
            tipView.setTipList(tips);
            tipView.setTipItemClickListener(new TipView.TipItemClickListener() {
                @Override
                public void clickTipItem(MessageListData messageListData) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("消息内容");
                    builder.setMessage(messageListData.getTcontent());
                    builder.show();
                }
            });
        }
    }

    @Override
    public void getFlowNavigation(String string) {
    }


    @Override
    public void getPoiAll(String string) {
        if (Global.HOST_CLOSE.equals(string)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[{")) {
            RequestDialogUtils.closeDialog(requestDialog);
            Intent poiIntent = new Intent(MainActivity.this, POISearchActivity.class);
            poiIntent.putExtra("string", string);
            startActivityForResult(poiIntent, Global.REQUEST_CODE_POI);

        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(MainActivity.this, "服务器连接错误");
        }

    }

    @Override
    public void getPoiTypeList(String string) {
        Log.i(TAG, "getPoiTypeList: string===" + string);
        if (Global.HOST_CLOSE.equals(string)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[{")) {
            RequestDialogUtils.closeDialog(requestDialog);
            ShowPoiTypePop showPoiTypePop = new ShowPoiTypePop(MainActivity.this, tv_poi, webView);
            showPoiTypePop.showPoiPopupWindow(inflate, tv_poi, string, savePoiTypeCheckedUtils);

        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(MainActivity.this, "服务器连接错误");
        }

    }


    @Override
    public void getPoiByType(String string) {
    }

    @Override
    public void getQuanjingImgList(String string) {
        Log.i(TAG, "getQuanjingImgList: string==========" + string);
        if (Global.HOST_CLOSE.equals(string)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[{")) {
            RequestDialogUtils.closeDialog(requestDialog);
            ShowQuanjingPicPop quanjingPicPop = new ShowQuanjingPicPop(MainActivity.this, tv_quanjing, webView);
            quanjingPicPop.showQjPop(tv_quanjing, string, quanjingtype);
        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(MainActivity.this, "服务器连接错误");
        }
    }

    @Override
    public void getQuanjingPointByName(String string) {
        if (Global.HOST_CLOSE.equals(string)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[{")) {
            RequestDialogUtils.closeDialog(requestDialog);
            if (!"".equals(string)) {
                Gson gson = new Gson();
                List<QjPointData> qjPointDataList = gson.fromJson(string, new TypeToken<List<QjPointData>>() {
                }.getType());
                int quanjingid = qjPointDataList.get(0).getQuanjingid();
                Intent intent = new Intent(MainActivity.this, LoadUrlActivity.class);
                intent.putExtra("flag", 2);
                intent.putExtra("qjmy_url", Global.QJMY_IMG_URL + quanjingid + Global.QJMY_HOUZHUI);
                startActivity(intent);
            }
        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(MainActivity.this, "服务器连接错误");
        }
    }

    @Override
    public void getFlowPicture(String string) {

    }

    @Override
    public void getApkTimeLimit(String string) {
        Log.i(TAG, "getApkTimeLimit: " + string);
        if (Global.HOST_CLOSE.equals(string)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[")) {
            RequestDialogUtils.closeDialog(requestDialog);
            Gson gson = new Gson();
            List<ApkTimeLimitData> apkTimeLimitDataList = gson.fromJson(string, new TypeToken<List<ApkTimeLimitData>>() {
            }.getType());

            //timeend='2019-07-31 00:00:00', now='2019-07-01 16:08:33', timestart='2019-07-01 00:00:00'
            String nowTime = apkTimeLimitDataList.get(0).getNow();
            String startTime = apkTimeLimitDataList.get(0).getTimestart();
            String endTime = apkTimeLimitDataList.get(0).getTimeend();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                boolean effectiveDate = CommonUtils.isEffectiveDate(dateFormat.parse(nowTime), dateFormat.parse(startTime), dateFormat.parse(endTime));
                if (effectiveDate) {
                    //动态申请权限
                    setPermission();

                    //webview设置
                    initWebview();

                    LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
                    inflate = layoutInflater.inflate(R.layout.poi_type_layout, null);

                    //定位
                    if (initGpsSetting()) return;

                } else {
                    Intent intent = new Intent(MainActivity.this, NoPermissionActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(MainActivity.this, "服务器连接错误");
        }
    }

    @Override
    public void getQjTypeList(String string) {
        Log.i(TAG, "getQjTypeList: string===" + string);
        if (Global.HOST_CLOSE.equals(string)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[")) {
            RequestDialogUtils.closeDialog(requestDialog);
//            showQjTypeDialog(string);
            ShowQjTypePop showQjTypePop = new ShowQjTypePop(MainActivity.this, tv_quanjing, webView);
            showQjTypePop.showQjTypePop(tv_quanjing, string);
        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(MainActivity.this, "服务器连接错误");
        }
    }

    @Override
    public void versionUpdate(String string) {
        Log.i(TAG, "versionUpdate: string===" + string);
        if (Global.HOST_CLOSE.equals(string)) {
            Toast.makeText(this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            Toast.makeText(this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            Toast.makeText(this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            Toast.makeText(this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.startsWith("{")) {
            final VersionData versionData = new Gson().fromJson(string, new TypeToken<VersionData>() {
            }.getType());
            Double urlVersion = Double.parseDouble(versionData.getVersion());
            Double localVersion = Double.parseDouble(VersionUpdateUtils.getVersionName());
            if (urlVersion > localVersion) {
                try {
                    new MaterialDialog.Builder(MainActivity.this)
                            .title("版本更新")
                            .content(versionData.getUpdateinfo())
                            .positiveText("确定")
                            .negativeText("取消")
                            .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {
                                        //服务器版本大于当前安装版本，版本更新，传入url
                                        VersionUpdateUtils.downFile(MainActivity.this, versionData.getLoadurl());
                                    } else if (which == DialogAction.NEGATIVE) {
                                        dialog.dismiss();
                                    }

                                }
                            }).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            ToastUtils.showShortToast(MainActivity.this, "服务器连接错误");
        }
    }

    /**
     * webview基本初始化
     */
    @SuppressLint({"ClickableViewAccessibility", "JavascriptInterface"})
    private void initWebview() {
        mapPath = Global.BASIC_MAP_IP + Global.YX;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webView.loadUrl("file:///android_asset/map.html?SDPath=" + Global.BASIC_MAP_IP);

//        //微地图
//        webView.loadUrl("file:///android_asset/map.html?id=1&" + mapPath + "/Layers/" + "&" + "12650034.107591387" +
//                "&" + "4144867.9283914543" + "&" + "12652090.675101338" + "&" + "4146109.4258633601");


        webView.addJavascriptInterface(new JavaScriptinterface(),
                "android");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        //MapView滑动处理，控制图斑弹出框显示
        detector = new GestureDetector(MainActivity.this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float minMove = 5;
                float Move = -10;
                float beginX = e1.getX();
                float endX = e2.getX();
                float beginY = e1.getY();
                float endY = e2.getY();
                if (beginX - endX > minMove || beginX - endX < Move) {
                    webView.loadUrl("javascript:removeLocation()");
                } else if (beginY - endY > minMove || beginY - endY < Move) {
                    webView.loadUrl("javascript:removeLocation()");
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float minMove = 5;
                float Move = -10;
                float beginX = e1.getX();
                float endX = e2.getX();
                float beginY = e1.getY();
                float endY = e2.getY();
                if (beginX - endX > minMove || beginX - endX < Move) {
                    webView.loadUrl("javascript:removeLocation()");
                } else if (beginY - endY > minMove || beginY - endY < Move) {
                    webView.loadUrl("javascript:removeLocation()");
                }
                return false;
            }
        });
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showWDTLayer();
            }
        }, 1000);
    }

    /**
     * 传入对应图层名称，加载显示不同数据
     */
    private void loadLayer(String name) {
        webView.loadUrl("javascript:layer('" + name + "','true')");

        webView.loadUrl("javascript:setMapCenter()");
    }

    /**
     * 传入对应图层名称，关闭显示
     */
    private void unLoadLayer(String name) {
        webView.loadUrl("javascript:layer('" + name + "','false')");
    }


    /**
     * 定位基本设置
     */
    private boolean initGpsSetting() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!isGpsAble(lm)) {
            Toast.makeText(MainActivity.this, "请打开GPS~", Toast.LENGTH_SHORT).show();
            openGPS2();
        }
        //从GPS获取最近的定位信息
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        //获取当前可用的位置控制器
        List<String> proList = lm.getProviders(true);
        String gpsProvider = LocationManager.GPS_PROVIDER;
        String networkProvider = LocationManager.NETWORK_PROVIDER;

        Location lc = null;
        //检查是否打开了GPS或网络定位
        if (proList.contains(gpsProvider)) {
            //是否为GPS位置控制器
            lc = lm.getLastKnownLocation(gpsProvider);
        } else if (proList.contains(networkProvider)) {
            //是否为网络位置控制器
            lc = lm.getLastKnownLocation(networkProvider);
        } else {
            Toast.makeText(MainActivity.this, "请检查网络或GPS是否打开", Toast.LENGTH_SHORT).show();
        }


//         lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateShow(lc);
        //设置间隔获得一次GPS定位信息
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 当GPS定位信息发生改变时，更新定位
                updateShow(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                // 当GPS LocationProvider可用时，更新定位
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                updateShow(lm.getLastKnownLocation(provider));
            }

            @Override
            public void onProviderDisabled(String provider) {
                updateShow(null);
            }
        });
        return false;
    }

    //定义一个更新显示的方法
    private void updateShow(Location location) {
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            if (longitude != 0.0 && latitude != 0.0) {
//                webView.loadUrl("javascript:WGS84Tomap('" + longitude + "','" + latitude + "')");
            }
        } else {
            longitude = 0.0;
            latitude = 0.0;
        }
    }

    //gps是否可用
    private boolean isGpsAble(LocationManager lm) {
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //打开设置页面让用户自己设置
    private void openGPS2() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0);
    }

    /**
     * 与js交互返回值
     */
    private class JavaScriptinterface {

        @JavascriptInterface
        public void showVR(final String poiName) {
            //点击poi点 触发对应点击事件
            //出行：剩余停车位；景观：全景；其他：弹框显示名称
            MainHandler.getInstance().post(new Runnable() {
                @Override
                public void run() {
                    if (!"".equals(poiName) && !"undefined".equals(poiName)) {
                        //根据js中回传的VR名称 请求接口 查看全景
                        mainPresenter.getQuanjingPointByName(FlagConstant.FLAG_QUANJING_POINT_BYNAME, poiName);
                        requestDialog = RequestDialogUtils.createLoadingDialog(MainActivity.this, "加载中");
                    }
                }
            });

        }

    }


    /**
     * 动态申请权限
     */
    private boolean setPermission() {
        MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_SDCARD,
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.MOUNT_UNMOUNT_FILESYSTEMS",
                "android.permission.READ_PHONE_STATE",
                "android.permission.VIBRATE",
                "android.permission.ACCESS_NETWORK_STATE",
                "android.permission.ACCESS_WIFI_STATE",
                "android.permission.CHANGE_WIFI_STATE",
                "android.permission.INTERNET",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.BAIDU_LOCATION_SERVICE",
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCESS_GPS",
                "android.permission.READ_LOGS",
                "android.permission.CAMERA",
                "android.permission.WRITE_SETTINGS");
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        //消息列表
        mainPresenter.messageCarousel(FlagConstant.FLAG_MESSAGE);
    }
}

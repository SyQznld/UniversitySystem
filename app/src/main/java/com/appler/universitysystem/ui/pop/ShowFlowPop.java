package com.appler.universitysystem.ui.pop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appler.universitysystem.Global;
import com.appler.universitysystem.R;
import com.appler.universitysystem.api.FlagConstant;
import com.appler.universitysystem.bean.FlowData;
import com.appler.universitysystem.bean.FlowRouteData;
import com.appler.universitysystem.bean.UserData;
import com.appler.universitysystem.dao.UserDao;
import com.appler.universitysystem.presenter.MainPresenter;
import com.appler.universitysystem.ui.activity.DocsListActivity;
import com.appler.universitysystem.ui.activity.LoadUrlActivity;
import com.appler.universitysystem.ui.adapter.FlowListAdapter;
import com.appler.universitysystem.utils.CommonUtils;
import com.appler.universitysystem.utils.RequestDialogUtils;
import com.appler.universitysystem.utils.ToastUtils;
import com.appler.universitysystem.view.MainView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 显示流程弹框
 */
public class ShowFlowPop extends PopupWindow implements MainView {

    private String TAG = getClass().getSimpleName();
    private Activity context;
    private View view;
    private PopupWindow popupWindow;
    private WebView mWebView;
    private TextView tv_flow;

    private RecyclerView rv_flow_list;
    private ImageView iv_flow_close;
    private MainPresenter mainPresenter;

    private String flowFloorXY = "";//流程关联楼栋  坐标点
    private String flowFloorName = "";//流程关联楼栋  楼栋名称

    private String flowid = "";  //判断显示 已完成或者未完成或者不显示
    private String showQjFlowId = "";  //对应显示全景导航id
    private FlowListAdapter flowListAdapter;
    private Dialog requestDialog;

    private String selectXiaoqu; //点击流程选择校区以后
    private String campus = "";  //选择 校区


    public ShowFlowPop(Activity context, WebView mweb, TextView tv_flow, String selectXiaoqu) {
        super(context);
        this.context = context;
        this.mWebView = mweb;
        this.tv_flow = tv_flow;
        this.selectXiaoqu = selectXiaoqu;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.flow_pop_layout, null);

        initView();
        mainPresenter = new MainPresenter(this);
        //流程列表接口请求
        requestFlowList(selectXiaoqu);
    }


    private void initView() {
        //流程列表
        rv_flow_list = view.findViewById(R.id.rv_flow_list);
        iv_flow_close = view.findViewById(R.id.iv_flow_close);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_flow_list.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void getDZJG(String string) {
    }


    @Override
    public void getFlow(String string) {
        Log.i(TAG, "getFlow: " + string);
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
            //流程列表
            if (!"".equals(string)) {
                FlowData flowData = new Gson().fromJson(string, new TypeToken<FlowData>() {
                }.getType());
                flowid = flowData.getFlowid();
                if ("".equals(flowid)) {
                    flowid = "100";
                }
                final List<FlowData.FlowBean> flowList = flowData.getFlow();

                for (int i = 0; i < flowList.size(); i++) {
                    String zuobiao = flowList.get(i).getZuobiao();
                    String floorname = flowList.get(i).getFloorname();

                    if (zuobiao.contains("(") && zuobiao.contains(")")) {
                        String replace = zuobiao.substring(zuobiao.indexOf("(") + 1, zuobiao.indexOf(")"));
                        if (replace.contains(" ")) {
                            String[] split = replace.split(" ");
                            if (split.length > 1) {
                                mWebView.loadUrl("javascript:zoomToFlowFloor('" + split[1] + "','" + split[0] + "','" + floorname + "','" + "all" + "')");
                            }
                        }
                    }
                }

                flowListAdapter = new FlowListAdapter(context, flowList, Integer.parseInt(flowid));
                rv_flow_list.setAdapter(flowListAdapter);
                flowListAdapter.setFlowListItemClickListener(new FlowListAdapter.FlowListItemClickListener() {
                    @Override
                    public void daohang(int position, final FlowData.FlowBean flowBean) {
                        String campus1 = flowBean.getCampus();
                        if (Global.CAMPUS_DONG.equals(campus1)){
                            campus = "dong";
                        } else if (Global.CAMPUS_XI.equals(campus1)){
                            campus = "xi";
                        }
                        String flowid = flowBean.getFlowid();
                        flowFloorXY = flowBean.getZuobiao();
                        flowFloorName = flowBean.getFloorname();
                        showQjFlowId = flowBean.getFlowid();

                        mainPresenter.getFlownavigation(FlagConstant.FLAG_FLOW_ROUTE, flowid, campus1);
                    }

                    @Override
                    public void completeOneFlow(int position, final FlowData.FlowBean flowBean) {
                        new MaterialDialog.Builder(context)
                                .title("完成该流程")
                                .content("确定完成当前流程？")
                                .positiveText("确定")
                                .negativeText("取消")
                                .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                                .onAny(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (which == DialogAction.POSITIVE) {

                                            UserData user = UserDao.getUser();
                                            if (null != user) {
                                                mainPresenter.completeOneFlow(FlagConstant.FLAG_FLOW_COMPLETE, CommonUtils.getIMEI(context), flowBean.getFlowid());
                                                popupWindow.dismiss();
                                            }

                                        } else if (which == DialogAction.NEGATIVE) {
                                            dialog.dismiss();
                                        }
                                    }
                                }).show();
                    }

                });

                //流程相关文件
                flowListAdapter.setFlowDemandItemClickListener(new FlowListAdapter.FlowDemandItemClickListener() {
                    @Override
                    public void flowDemandItemClick(FlowData.FlowBean flowBean) {

//                        popupWindow.dismiss();
                        mWebView.loadUrl("javascript:clearRoadTrack()");  //清空上一个流程关联的道路线

                        String flowId = flowBean.getFlowid();
                        Intent intent = new Intent(context, DocsListActivity.class);
                        intent.putExtra("flowId", flowId);
                        intent.putExtra("flag", 4);
                        context.startActivity(intent);

                    }
                });

                iv_flow_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mWebView.loadUrl("javascript:clearFlowFloorMarker()");  //清空流程关联楼栋marker点
                        mWebView.loadUrl("javascript:clearRoadTrack()");  //清空流程关联路线
                        popupWindow.dismiss();
                    }
                });


                WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                int width = manager.getDefaultDisplay().getWidth();
                int height = manager.getDefaultDisplay().getHeight();
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false); //改为true 拖动地图弹框不消失
//                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.showAtLocation(tv_flow, Gravity.BOTTOM, 0, 0);

            }
        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            ToastUtils.showShortToast(context, "服务器连接错误");
        }
    }

    @Override
    public void completeOneFlow(String string) {
        Log.i(TAG, "completeOneFlow: " + string);
        if (Global.HOST_CLOSE.equals(string)) {
            Toast.makeText(context, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            Toast.makeText(context, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            Toast.makeText(context, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            Toast.makeText(context, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else {
            //刷新数据，执行下一个流程
            if ("success".equals(string)) {
//                requestFlowList();
            }
        }

    }

    @Override
    public void getMessageList(String string) {

    }

    @Override
    public void getFlowNavigation(String string) {
        Log.i(TAG, "getFlowNavigation: " + string);
        if (Global.HOST_CLOSE.equals(string)) {
            Toast.makeText(context, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            Toast.makeText(context, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            Toast.makeText(context, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            Toast.makeText(context, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (string.startsWith("[")) {
            List<FlowRouteData> flowRouteDataList = new Gson().fromJson(string, new TypeToken<List<FlowRouteData>>() {
            }.getType());
            FlowRouteData flowRouteData = flowRouteDataList.get(0);
            String roadName = flowRouteData.getRoadname();
            String geom = flowRouteData.getGeom();
            String flow_campus = flowRouteData.getCampus();
            Log.i(TAG, "url getFlowNavigation: " + flow_campus);
            if (Global.CAMPUS_DONG.equals(flow_campus)){
                campus = "dong";
            } else if (Global.CAMPUS_XI.equals(flow_campus)){
                campus = "xi";
            }
            //  flowFloorXY  流程相关楼栋定位
            if (flowFloorXY.contains("(") && flowFloorXY.contains(")")) {
                String replace = flowFloorXY.substring(flowFloorXY.indexOf("(") + 1, flowFloorXY.indexOf(")"));
                Log.i(TAG, "getFlowNavigation chro: " + replace);
                if (replace.contains(" ")) {
                    String[] split = replace.split(" ");
                    if (split.length > 1) {
                        mWebView.loadUrl("javascript:zoomToFlowFloor('" + split[1] + "','" + split[0] + "','" + flowFloorName + "','" + "single" + "')");
                    }
                }
            }

            //drawListTrack 流程相关路线定位
            if (geom.contains("(") && geom.contains(")")) {
                String substring = geom.substring(geom.indexOf("(") + 1, geom.indexOf(")"));
                mWebView.loadUrl("javascript:drawListTrack('" + substring + "','" + roadName + "')");
            }

//                显示是否进行全景漫游导航 提示框
            showQuanjingDialog(campus);

        } else {
            ToastUtils.showShortToast(context, "暂无导航路线");
        }
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

    }

    @Override
    public void versionUpdate(String string) {

    }

    /**
     * 提示全景漫游
     */
    private void showQuanjingDialog(final String flow_campus) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.QjmyAlertDialog);
        LayoutInflater inflater = context.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.quanjing_dialog_layout, null);
        builder.setView(dialog);
        final AlertDialog qjpopDialog = builder.create();
        qjpopDialog.show();
        qjpopDialog.setCanceledOnTouchOutside(false);

        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params = qjpopDialog.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth / 2;
        qjpopDialog.getWindow().setAttributes(params);//设置大小
        TextView tv_yes = dialog.findViewById(R.id.tv_qjpop_yes);
        TextView tv_no = dialog.findViewById(R.id.tv_qjpop_no);
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //http://192.168.2.253:8077/yingxinimg/lushang/dong/1/tour.html
                if (!"".equals(showQjFlowId)) {
                    Intent intent = new Intent(context, LoadUrlActivity.class);
                    intent.putExtra("flag", 2);
                    intent.putExtra("qjmy_url", Global.QJMY_FLOW_URL + "/" +flow_campus + "/" + showQjFlowId + Global.QJMY_HOUZHUI);
                    context.startActivity(intent);
                    qjpopDialog.dismiss();
                } else {
                    Toast.makeText(context, "请先选择一个流程", Toast.LENGTH_SHORT).show();
                    qjpopDialog.dismiss();
                }
            }
        });
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qjpopDialog.dismiss();
            }
        });
    }

    @Override
    public void setState(int state) {

    }

    private void requestFlowList(String selectXiaoqu) {
        Log.i(TAG, "requestFlowList11111: " + CommonUtils.getIMEI(context));
        mainPresenter.getFlowList(FlagConstant.FLAG_FLOW_LIST, CommonUtils.getIMEI(context), selectXiaoqu);
        requestDialog = RequestDialogUtils.createLoadingDialog(context, "加载中");
    }
}

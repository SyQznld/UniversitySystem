package com.appler.universitysystem.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.appler.universitysystem.Global;
import com.appler.universitysystem.R;
import com.appler.universitysystem.api.FlagConstant;
import com.appler.universitysystem.base.BaseActivity;
import com.appler.universitysystem.bean.MessageListData;
import com.appler.universitysystem.presenter.MessagePresenter;
import com.appler.universitysystem.ui.adapter.MessageListAdapter;
import com.appler.universitysystem.utils.CommonUtils;
import com.appler.universitysystem.utils.RequestDialogUtils;
import com.appler.universitysystem.utils.ToastUtils;
import com.appler.universitysystem.view.MessageView;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

public class MessageListActivity extends BaseActivity implements MessageView {
    private String TAG = getClass().getSimpleName();
    @BindView(R.id.toolbar_info)
    Toolbar toolbar;
    @BindView(R.id.srl_message_list)
    SmartRefreshLayout srl_message_list;
    @BindView(R.id.sl_message_list)
    StateLayout sl_message_list;
    @BindView(R.id.rv_message_list)
    RecyclerView rv_message_list;

    private MessagePresenter messagePresenter;
    private Dialog requestDialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_message_list;
    }

    @Override
    public void doBusiness(Context context) {
        toolbar.setTitle("消息列表");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        messagePresenter = new MessagePresenter(this);

//        下拉刷新上拉加载更多
        initRefreshAndLoadMore();
    }

    /**
     * 下拉刷新上拉加载更多
     */
    private void initRefreshAndLoadMore() {

        srl_message_list.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                接口返回信息列表数据
                initState();
                refreshLayout.finishRefresh();
            }
        });
    }

    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(MessageListActivity.this)) {
//            无网络图片提示、监听
            sl_message_list.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_message_list.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(MessageListActivity.this)) {
                        ToastUtils.showToast(MessageListActivity.this, "请确保网络连接正常~");
                    } else {
//                      刷新后又网络，加载数据
                        requestMessageList();
                        sl_message_list.showContentView();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
//            有网络加载数据
            requestMessageList();
            sl_message_list.showContentView();
        }
    }

    private void requestMessageList() {
        messagePresenter.getMessageList(FlagConstant.FLAG_MESSAGE);
        requestDialog = RequestDialogUtils.createLoadingDialog(MessageListActivity.this, "加载中");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initState();
    }

    @Override
    public void getMessageList(String string) {
        if (Global.HOST_CLOSE.equals(string)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(MessageListActivity.this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(MessageListActivity.this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(MessageListActivity.this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(MessageListActivity.this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[") && string.contains("]") && string.contains("{") && string.contains("}")) {
            RequestDialogUtils.closeDialog(requestDialog);
            List<MessageListData> messageListDataList = new Gson().fromJson(string, new TypeToken<List<MessageListData>>() {
            }.getType());
            rv_message_list.setLayoutManager(new LinearLayoutManager(MessageListActivity.this));
            rv_message_list.setAdapter(new MessageListAdapter(MessageListActivity.this, messageListDataList));
        } else {
            RequestDialogUtils.closeDialog(requestDialog);
            Toast.makeText(MessageListActivity.this, "服务器连接错误", Toast.LENGTH_SHORT).show();
        }
    }

}

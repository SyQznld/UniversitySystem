package com.appler.universitysystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appler.universitysystem.Global;
import com.appler.universitysystem.R;
import com.appler.universitysystem.api.FlagConstant;
import com.appler.universitysystem.base.BaseActivity;
import com.appler.universitysystem.bean.DocsListData;
import com.appler.universitysystem.bean.FlowFileData;
import com.appler.universitysystem.presenter.DocsPresenter;
import com.appler.universitysystem.ui.adapter.DocsListAdapter;
import com.appler.universitysystem.ui.adapter.FlowFileAdapter;
import com.appler.universitysystem.utils.CommonUtils;
import com.appler.universitysystem.utils.ToastUtils;
import com.appler.universitysystem.view.DocsView;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

public class DocsListActivity extends BaseActivity implements DocsView {
    private String TAG = getClass().getSimpleName();
    @BindView(R.id.toolbar_info)
    Toolbar toolbarInfo;
    @BindView(R.id.rv_docs_list)
    RecyclerView rv_docs;
    @BindView(R.id.sl_docs_list)
    StateLayout sl_docs;
    @BindView(R.id.srl_docs)
    SmartRefreshLayout refreshLayout;

    private DocsPresenter docsPresenter;
    private int flag;   //判断从哪个页面进来

    @Override
    public void doBusiness(Context context) {

        toolbarInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        docsPresenter = new DocsPresenter(this);

        //下拉刷新 上拉加载
        initRefreshAndLoad();

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_docs_list;
    }


    /**
     * 下拉刷新上拉加载
     */
    private void initRefreshAndLoad() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                //接口返回资料列表数据
                initState();
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                refreshLayout.finishLoadMore();
            }
        });
    }

    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(DocsListActivity.this)) {
            sl_docs.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_docs.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(DocsListActivity.this)) {
                        ToastUtils.showToast(DocsListActivity.this, "请确保网络连接正常~");
                    } else {
//                        problemList.clear();
                        requestDocsList();
                        sl_docs.showContentView();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
//            problemList.clear();
            requestDocsList();
            sl_docs.showContentView();
        }
    }

    private void requestDocsList() {
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);

        if (flag == 3) {
            toolbarInfo.setTitle("资料列表");   //user
            docsPresenter.getDocsList(FlagConstant.FLAG_DOCS_LIST);
        } else if (flag == 4) {
            toolbarInfo.setTitle("文件列表");   //流程
            String flowId = intent.getStringExtra("flowId");
            docsPresenter.getFlowFile(FlagConstant.FLAG_FLOW_FILE, flowId);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initState();
    }

    @Override
    public void getDocsList(String string) {
        Log.i(TAG, "getDocsList: string===" + string);
        if (Global.HOST_CLOSE.equals(string)) {
            Toast.makeText(DocsListActivity.this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            Toast.makeText(DocsListActivity.this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            Toast.makeText(DocsListActivity.this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            Toast.makeText(DocsListActivity.this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[") && string.contains("]") && string.contains("{") && string.contains("}")) {
            sl_docs.showContentView();
            List<DocsListData> docsListDataList = new Gson().fromJson(string, new TypeToken<List<DocsListData>>() {
            }.getType());
            rv_docs.setLayoutManager(new LinearLayoutManager(this));
            rv_docs.setAdapter(new DocsListAdapter(DocsListActivity.this, docsListDataList));
        } else {
            sl_docs.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
        }
    }

    @Override
    public void getFlowFile(String string) {
        Log.i(TAG, "getFlowFile: string=====" + string);
        if (Global.HOST_CLOSE.equals(string)) {
            Toast.makeText(DocsListActivity.this, Global.TOAST_HOST_CLOSE, Toast.LENGTH_SHORT).show();
        } else if (string.contains(Global.CONN_FAIL)) {
            Toast.makeText(DocsListActivity.this, Global.TOAST_CONN_FAIL, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_HTTP500)) {
            Toast.makeText(DocsListActivity.this, Global.TOAST_HTTP500, Toast.LENGTH_SHORT).show();
        } else if (string.equals(Global.PC_BACK_TIMEOUT) || string.equals(Global.PC_BACK_TIMEOUT_)) {
            Toast.makeText(DocsListActivity.this, Global.TOAST_TIMEOUT, Toast.LENGTH_SHORT).show();
        } else if (!"".equals(string) && string.contains("[") && string.contains("{") && string.contains("}") && string.contains("]")) {
            sl_docs.showContentView();
            List<FlowFileData> flowFileData = new Gson().fromJson(string, new TypeToken<List<FlowFileData>>() {
            }.getType());
            for (int i = 0; i < flowFileData.size(); i++) {
                String filename = flowFileData.get(i).getFilename();
                String fileurl = flowFileData.get(i).getFileurl();
                if (!"".equals(filename) || null != filename) {
                    if (!"".equals(fileurl) || null != fileurl) {
                        rv_docs.setLayoutManager(new LinearLayoutManager(this));
                        rv_docs.setAdapter(new FlowFileAdapter(DocsListActivity.this, flowFileData));
                    }
                }
            }
        } else {
            sl_docs.showEmptyView(string);
//            sl_docs.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
        }
    }
}

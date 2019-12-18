package com.appler.universitysystem.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appler.universitysystem.R;
import com.appler.universitysystem.bean.FlowData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class FlowListAdapter extends BaseQuickAdapter<FlowData.FlowBean, BaseViewHolder> {
    private String TAG = getClass().getSimpleName();
    private Context context;
    private List<FlowData.FlowBean> data;
    private int flowId;


    public FlowListAdapter(Context context, @Nullable List<FlowData.FlowBean> data, int flowId) {
        super(R.layout.flow_card_item, data);
        this.context = context;
        this.data = data;
        this.flowId = flowId;

    }

    @Override
    protected void convert(BaseViewHolder helper, final FlowData.FlowBean item) {

        CardView cv_flow = helper.getView(R.id.cv_flow_item);
        TextView tv_name = helper.getView(R.id.tv_flow_name);
        ImageView iv_flow_pic = helper.getView(R.id.iv_flow_pic);
        TextView tv_flow_floorName = helper.getView(R.id.tv_flow_floorName);
        TextView tv_flow_attention = helper.getView(R.id.tv_flow_attention);
        final Button btn_flow_complete = helper.getView(R.id.btn_flow_complete);

        TextView tv_yx_demand = helper.getView(R.id.tv_gn_demand);
        tv_yx_demand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flowDemandItemClickListener.flowDemandItemClick(item);
            }
        });

        final int layoutPosition = helper.getLayoutPosition();

        if (layoutPosition % 4 == 0) {   //绿色
            cv_flow.setCardBackgroundColor(Color.parseColor("#8dc041"));
        } else if (layoutPosition % 4 == 1) { //红色
            cv_flow.setCardBackgroundColor(Color.parseColor("#fc4145"));
        } else if (layoutPosition % 4 == 2) {  //蓝色
            cv_flow.setCardBackgroundColor(Color.parseColor("#4d9fef"));
        } else if (layoutPosition % 4 == 3) {  //橘色
            cv_flow.setCardBackgroundColor(Color.parseColor("#fda133"));
        }



        String name = item.getName();
        tv_name.setText(name);
        if (name.contains("交费")) {
            iv_flow_pic.setImageResource(R.drawable.ic_flow_jiaofei);
        } else if (name.contains("学院")) {
            iv_flow_pic.setImageResource(R.drawable.ic_flow_baodao);
        } else if (name.contains("住宿")) {
            iv_flow_pic.setImageResource(R.drawable.ic_flow_sushe);
        } else if (name.contains("户口")) {
            iv_flow_pic.setImageResource(R.drawable.ic_flow_sushe);
        } else if (name.contains("入班")) {
            iv_flow_pic.setImageResource(R.drawable.ic_flow_jiaoshi);
        }
        tv_flow_floorName.setText(item.getFloorname());
        final String remarks = item.getRemarks();
        if (!"".equals(remarks) && !"null".equals(remarks) && null != remarks) {
            tv_flow_attention.setVisibility(View.VISIBLE);
            tv_flow_attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("注意事项");
                    builder.setMessage(Html.fromHtml(remarks, null, null));
                    builder.show();
                }
            });
        } else {
            tv_flow_attention.setVisibility(View.INVISIBLE);
        }

        //TODO 导航漫游模块
        cv_flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flowListItemClickListener.daohang(layoutPosition, item);
            }
        });

        //当前位置
        if (layoutPosition == flowId - 1) {
            btn_flow_complete.setVisibility(View.GONE);     //隐藏完成

            btn_flow_complete.setText("完成");
            btn_flow_complete.setTextColor(Color.parseColor("#ffffff"));
            btn_flow_complete.setBackgroundResource(R.drawable.flow_item_unfinish_bg);
            btn_flow_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flowListItemClickListener.completeOneFlow(layoutPosition, item);
                }
            });

        } else if (layoutPosition < flowId - 1 && flowId > 1) {
            btn_flow_complete.setVisibility(View.GONE); //隐藏完成
            btn_flow_complete.setText("已完成");
            btn_flow_complete.setTextColor(Color.parseColor("#444444"));
            btn_flow_complete.setBackgroundResource(R.drawable.flow_item_finish_bg);
        } else {
            btn_flow_complete.setVisibility(View.GONE); //隐藏完成
        }
    }


    private FlowListItemClickListener flowListItemClickListener;

    public interface FlowListItemClickListener {
        void daohang(int position, FlowData.FlowBean flowBean);

        void completeOneFlow(int position, FlowData.FlowBean flowBean);
    }

    public void setFlowListItemClickListener(FlowListItemClickListener flowListItemClickListener) {
        this.flowListItemClickListener = flowListItemClickListener;
    }

    private FlowDemandItemClickListener flowDemandItemClickListener;

    public interface FlowDemandItemClickListener {
        void flowDemandItemClick(FlowData.FlowBean flowBean);
    }

    public void setFlowDemandItemClickListener(FlowDemandItemClickListener flowDemandItemClickListener) {
        this.flowDemandItemClickListener = flowDemandItemClickListener;
    }

}

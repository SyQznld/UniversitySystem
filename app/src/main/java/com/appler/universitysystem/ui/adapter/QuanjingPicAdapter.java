package com.appler.universitysystem.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appler.universitysystem.Global;
import com.appler.universitysystem.R;
import com.appler.universitysystem.bean.QuanjingImageData;
import com.appler.universitysystem.utils.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class QuanjingPicAdapter extends BaseQuickAdapter<QuanjingImageData, BaseViewHolder> {
    private String TAG = getClass().getSimpleName();
    private Context context;
    private List<QuanjingImageData> data;

    public QuanjingPicAdapter(Context context, @Nullable List<QuanjingImageData> data) {
        super(R.layout.quanjing_pic_item_layout, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final QuanjingImageData qjPicData) {
        RelativeLayout rl_quanjing = helper.getView(R.id.rl_qj_item);
        ImageView iv_qjPic = helper.getView(R.id.iv_qj_pic);
        TextView tv_qjName = helper.getView(R.id.tv_qj_picName);
        String s = Global.URL + qjPicData.getQuanjingimg();
        Log.i(TAG, "convert: " + s);
        GlideUtils.loadNet(iv_qjPic, Global.URL + qjPicData.getQuanjingimg(),R.mipmap.login_input_bg);
//        GlideUtils.loadNet(iv_qjPic, "http://192.168.2.253:8077/yingxinimg/quanjing/nanmen.jpg",R.mipmap.login_input_bg);
        tv_qjName.setText(qjPicData.getName());
        rl_quanjing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qjPicItemClickListener.qjPicItemClick(helper.getLayoutPosition(),qjPicData);

            }
        });
    }


    private QjPicItemClickListener qjPicItemClickListener;

    public void setQjPicItemClickListener(QjPicItemClickListener qjPicItemClickListener) {
        this.qjPicItemClickListener = qjPicItemClickListener;
    }

    public interface QjPicItemClickListener{
        void qjPicItemClick(int position,QuanjingImageData quanjingPicData);
    }
}

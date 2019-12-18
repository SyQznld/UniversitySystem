package com.appler.universitysystem.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appler.universitysystem.R;
import com.appler.universitysystem.bean.QjTypeData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class QjTypeListAdapter extends BaseQuickAdapter<QjTypeData, BaseViewHolder> {
    private String TAG = getClass().getSimpleName();
    private Context context;
    private List<QjTypeData> data;

    public QjTypeListAdapter(Context context, @Nullable List<QjTypeData> data) {
        super(R.layout.qjtype_item_layout, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final QjTypeData item) {
        LinearLayout ll_qjtype = helper.getView(R.id.ll_qjtype);
        TextView tv_qjtype_name = helper.getView(R.id.tv_qjtype_name);
        tv_qjtype_name.setText(item.getQuanjingtype());
        ll_qjtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qjTypeItemClickListener.qjTypeItemClick(helper.getLayoutPosition(), item);
            }
        });

    }


    private QjTypeItemClickListener qjTypeItemClickListener;

    public void setQjTypeItemClickListener(QjTypeItemClickListener qjTypeItemClickListener) {
        this.qjTypeItemClickListener = qjTypeItemClickListener;
    }

    public interface QjTypeItemClickListener {
        void qjTypeItemClick(int position, QjTypeData qjTypeData);
    }
}

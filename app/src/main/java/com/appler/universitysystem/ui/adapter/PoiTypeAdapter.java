package com.appler.universitysystem.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.appler.universitysystem.R;
import com.appler.universitysystem.bean.PoiTypeData;
import com.appler.universitysystem.utils.SavePoiTypeCheckedUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Map;

public class PoiTypeAdapter extends BaseQuickAdapter<PoiTypeData, BaseViewHolder> {

    private Context context;
    private List<PoiTypeData> data;
    private SavePoiTypeCheckedUtils savePoiTypeCheckedUtils;

    public PoiTypeAdapter(Context context, @Nullable List<PoiTypeData> data, SavePoiTypeCheckedUtils savePoiTypeCheckedUtils) {
        super(R.layout.poi_item_layout, data);
        this.context = context;
        this.data = data;
        this.savePoiTypeCheckedUtils = savePoiTypeCheckedUtils;

    }


    @Override
    protected void convert(final BaseViewHolder helper, final PoiTypeData item) {
        TextView tv_poi_type = helper.getView(R.id.tv_poi_type);
        tv_poi_type.setText(item.getType());
        String tvText = tv_poi_type.getText().toString();

        if ("全选".equals(tvText) || "东校区".equals(tvText) || "西校区".equals(tvText)) {
            tv_poi_type.setTextSize(16);
        }

        //在初始化checkBox状态和设置状态变化监听事件之前先把状态变化监听事件设置为null
        final CheckBox cb_poi_type = helper.getView(R.id.cb_poi_type);
        cb_poi_type.setOnCheckedChangeListener(null);

        //保持之前选中的状态
        final Map<Integer, Boolean> poiTypeCheckedMap = savePoiTypeCheckedUtils.poiTypeCheckedMap;
        final int layoutPosition = helper.getLayoutPosition();
        if (poiTypeCheckedMap.containsKey(layoutPosition)) {
            cb_poi_type.setChecked(true);
        } else {
            cb_poi_type.setChecked(false);
        }

        cb_poi_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                checkBoxItemClickListener.checkBoxItemClick(layoutPosition, isChecked, item);
            }
        });

    }

    private CheckBoxItemClickListener checkBoxItemClickListener;

    public interface CheckBoxItemClickListener {
        void checkBoxItemClick(int position, boolean isChecked, PoiTypeData poiTypeData);

    }

    public void setCheckBoxItemClickListener(CheckBoxItemClickListener checkBoxItemClickListener) {
        this.checkBoxItemClickListener = checkBoxItemClickListener;
    }
}

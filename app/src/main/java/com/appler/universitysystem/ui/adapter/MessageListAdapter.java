package com.appler.universitysystem.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.appler.universitysystem.R;
import com.appler.universitysystem.bean.MessageListData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class MessageListAdapter extends BaseQuickAdapter<MessageListData, BaseViewHolder> {

    private Context context;
    private List<MessageListData> data;

    public MessageListAdapter(Context context, @Nullable List<MessageListData> data) {
        super(R.layout.message_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListData item) {
        TextView tv_message_title = helper.getView(R.id.tv_message_title);
        tv_message_title.setText(item.getTitle());
        String color = item.getColor();
        if (!"null".equals(color) && !"".equals(color) && null != color) {

            tv_message_title.setTextColor(Color.parseColor(color));
        } else {
            tv_message_title.setTextColor(Color.BLACK);
        }
        TextView tv_message_content = helper.getView(R.id.tv_message_content);
        tv_message_content.setText(item.getTcontent());
        TextView tv_message_time = helper.getView(R.id.tv_message_time);
        tv_message_time.setText(item.getAddtime());
        TextView tv_message_name = helper.getView(R.id.tv_message_name);
        tv_message_name.setText(item.getName());

    }
}

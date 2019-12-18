package com.appler.universitysystem.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appler.universitysystem.R;
import com.appler.universitysystem.bean.DZJGData;

import java.util.List;

public class DZJGSearchAdapter extends BaseAdapter {

    private Context context;
    private List<DZJGData.ChildrenBean> datas;
    private LayoutInflater layoutInflater;

    public DZJGSearchAdapter(Context context, List<DZJGData.ChildrenBean> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.child_item, null);
            viewHolder.tvDepartmentName = view.findViewById(R.id.tv_department_name);
            viewHolder.btnDepartmentUrl = view.findViewById(R.id.btn_department_url);
            viewHolder.btnDepartmentNavigation = view.findViewById(R.id.btn_department_navigation);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvDepartmentName.setText(datas.get(i).getName());
        viewHolder.tvDepartmentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDZJGItemClickListener.filterItemOnClick(i, datas.get(i));
            }
        });

        //详情
        viewHolder.btnDepartmentUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDzjgDetailsItemClickListener.detailsItemClickListener(datas.get(i));
            }
        });

        viewHolder.btnDepartmentNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    class ViewHolder {
        TextView tvDepartmentName;
        TextView btnDepartmentUrl;
        TextView btnDepartmentNavigation;
    }

    private FilterDZJGItemClickListener filterDZJGItemClickListener;

    public interface FilterDZJGItemClickListener {
        void filterItemOnClick(int position, DZJGData.ChildrenBean childrenBean);
    }

    public void setFilterDZJGItemClickListener(FilterDZJGItemClickListener filterDZJGItemClickListener) {
        this.filterDZJGItemClickListener = filterDZJGItemClickListener;
    }

    private FilterDzjgDetailsItemClickListener filterDzjgDetailsItemClickListener;

    public interface FilterDzjgDetailsItemClickListener {
        void detailsItemClickListener(DZJGData.ChildrenBean childrenBean);
    }

    public void setFilterDzjgDetailsItemClickListener(FilterDzjgDetailsItemClickListener filterDzjgDetailsItemClickListener) {
        this.filterDzjgDetailsItemClickListener = filterDzjgDetailsItemClickListener;
    }

}

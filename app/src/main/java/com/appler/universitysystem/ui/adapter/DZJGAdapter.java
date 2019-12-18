package com.appler.universitysystem.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.appler.universitysystem.R;
import com.appler.universitysystem.bean.DZJGData;

import java.util.List;

public class DZJGAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<DZJGData> groups;

    public DZJGAdapter(Context context, List<DZJGData> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return groups.get(i).getChildren().size();
    }

    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return groups.get(i).getChildren().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    //    展示指定组的试图对象
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parent_item, viewGroup, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvDepartment = view.findViewById(R.id.expand_parent);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.tvDepartment.setText(groups.get(i).getParents());
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_item, viewGroup, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvUsername = view.findViewById(R.id.tv_department_name);
            childViewHolder.btnDepartmentUrl = view.findViewById(R.id.btn_department_url);
            childViewHolder.btnDepartmentNavigation = view.findViewById(R.id.btn_department_navigation);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        final DZJGData.ChildrenBean childrenBean = groups.get(i).getChildren().get(i1);
        final String name = childrenBean.getName();

        childViewHolder.tvUsername.setText(name);
        childViewHolder.tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dzjgItemClickListener.itemClickListener(i, groups.get(i).getChildren().get(i1));
            }
        });

        //详情的点击事件
        childViewHolder.btnDepartmentUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dzjgDetailsItemClickListener.detailsItemClickListener(groups.get(i).getChildren().get(i1));
            }
        });

        //导航
        childViewHolder.btnDepartmentNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(name);
                builder.setMessage(Html.fromHtml(childrenBean.getJianjie(), null, null));
                builder.show();*/
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvDepartment;
    }

    static class ChildViewHolder {
        TextView tvUsername;
        Button btnDepartmentNavigation;
        Button btnDepartmentUrl;
    }

    private DzjgItemClickListener dzjgItemClickListener;

    public interface DzjgItemClickListener {
        void itemClickListener(int position, DZJGData.ChildrenBean childrenBean);
    }

    public void setDzjgItemClickListener(DzjgItemClickListener dzjgItemClickListener) {
        this.dzjgItemClickListener = dzjgItemClickListener;
    }

    private DzjgDetailsItemClickListener dzjgDetailsItemClickListener;

    public interface DzjgDetailsItemClickListener {
        void detailsItemClickListener(DZJGData.ChildrenBean childrenBean);
    }

    public void setDzjgDetailsItemClickListener(DzjgDetailsItemClickListener dzjgDetailsItemClickListener) {
        this.dzjgDetailsItemClickListener = dzjgDetailsItemClickListener;
    }
}

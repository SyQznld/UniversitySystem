package com.appler.universitysystem.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appler.universitysystem.R;
import com.appler.universitysystem.bean.POIData;

import java.util.ArrayList;
import java.util.List;

public class POIAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<POIData> data;
    private LayoutInflater layoutInflater;

    private ArrayList<POIData> originalValues;
    private final Object lock = new Object();
    private ArrayFilter filter;

    public POIAdapter(Context context, List<POIData> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
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
            view = layoutInflater.inflate(R.layout.poi_search_item, null);
            viewHolder.tv_poi_id = view.findViewById(R.id.tv_poi_id);
            viewHolder.tv_poi_name = view.findViewById(R.id.tv_poi_name);
            viewHolder.linearLayout = view.findViewById(R.id.ll_poi_num_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final POIData poiData = data.get(i);
        viewHolder.tv_poi_id.setText(String.valueOf(poiData.getGid()));
        viewHolder.tv_poi_name.setText(poiData.getName());

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("poiData", poiData);
                intent.putExtra("poiGeom", poiData.getGeom());

                ((Activity) context).setResult(Activity.RESULT_OK, intent);
                ((Activity) context).finish();

            }
        });
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ArrayFilter();
        }
        return filter;
    }

    class ViewHolder {
        TextView tv_poi_id;
        TextView tv_poi_name;
        LinearLayout linearLayout;
    }

    //    实现过滤的类
    private class ArrayFilter extends Filter {

        //        执行筛选
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
//            原始数据备份为空时上锁，同步复制原始数据
            if (originalValues == null) {
                synchronized (lock) {
                    originalValues = new ArrayList<>(data);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<POIData> list;
//                同步复制原始备份数据
                synchronized (lock) {
                    list = new ArrayList<>(originalValues);
                }
//                返回没筛选的原始数据
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();
                ArrayList<POIData> values;
                synchronized (lock) {
                    values = new ArrayList<>(originalValues);
                }
                List<POIData> newValues = new ArrayList<>();
                int count = values.size();
                for (int i = 0; i < count; i++) {
                    POIData value = values.get(i);
                    String name = value.getName().toLowerCase();
//                    startsWith判断字符串是否以指定前缀开头
//                    indexOf可返回某个指定的字符串值在字符串中首次出现的位置     如果要检索的字符串值没有出现，则该方法返回 -1
                    if (name.startsWith(prefixString) || name.indexOf(prefixString.toString()) != -1) {
                        newValues.add(value);
                    } else {
//                        处理首字符是空格
                        String[] words = name.split(" ");
                        int wordCount = words.length;
                        for (int j = 0; j < wordCount; j++) {
                            if (words[j].startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        //        刷新结果
        @Override
        protected void publishResults(CharSequence prefix, FilterResults filterResults) {
//            过滤后的结果
            data = (List<POIData>) filterResults.values;

            /**
             * 数据容器变化==========>notifyDataSetInvalidated();
             * 容器中的数据变化========>notifyDataSetChanged();
             */

            if (filterResults.count > 0) {
                notifyDataSetChanged();     //相当于datas中数据过滤掉一部分
            } else {
//               此时数据源是重新new出来的,原始数据已失效
                notifyDataSetInvalidated();
            }

        }
    }

}

package com.appler.universitysystem.ui.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appler.universitysystem.R;
import com.appler.universitysystem.base.BaseActivity;
import com.appler.universitysystem.bean.POIData;
import com.appler.universitysystem.ui.adapter.POIAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class POISearchActivity extends BaseActivity {

    @BindView(R.id.et_poi_search)
    EditText et_search;
    @BindView(R.id.flowlayout)
    TagFlowLayout flowLayout;
    @BindView(R.id.lv_poi)
    ListView lv_poi;
    @BindView(R.id.iv_poi_clear)
    ImageView iv_poi_clear;
    @BindView(R.id.tv_east)
    TextView tv_east;
    @BindView(R.id.tv_west)
    TextView tv_west;

    private String TAG = getClass().getSimpleName();

    private List<POIData> poiData;
    private Set<String> sets = new HashSet<>();
    private List<String> mType = new ArrayList<>();

    private List<POIData> poiFilterData = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private String name;

    private POIAdapter poiAdapter;
    private boolean eastFlag;
    private boolean westFlag;

    @Override
    public int bindLayout() {
        return R.layout.activity_poisearch;
    }


    @Override
    public void doBusiness(Context context) {
//        String string = CommonUtils.readJsonFromAssets("POI.json", POISearchActivity.this);
        String string = getIntent().getStringExtra("string");
        Gson gson = new Gson();
        poiData = gson.fromJson(string, new TypeToken<List<POIData>>() {
        }.getType());

        poiAdapter = new POIAdapter(POISearchActivity.this, poiData);
        lv_poi.setAdapter(poiAdapter);

//        默认展示所有数据
        allPOIData();

    }

    @OnClick({R.id.iv_poi_back, R.id.et_poi_search, R.id.tv_east, R.id.tv_west})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_poi_back:
                finish();
                break;
            case R.id.et_poi_search:
                fillListViewAndKeywordShow();
                break;
            case R.id.tv_east:  //东校区

                if (eastFlag) {
                    eastFlag = false;
                    tv_east.setTextSize(10);
                } else {
                    eastFlag = true;
                    tv_east.setTextSize(14);
                    westFlag = false;
                    tv_west.setTextSize(10);
                }

                eastWestFilter(tv_east);
                break;
            case R.id.tv_west:  //西校区
                if (westFlag) {
                    westFlag = false;
                    tv_west.setTextSize(10);
                } else {
                    westFlag = true;
                    tv_west.setTextSize(14);
                    eastFlag = false;
                    tv_east.setTextSize(10);
                }
                eastWestFilter(tv_west);

                break;
        }

    }

    //东西校区的过滤功能
    private void eastWestFilter(TextView textView) {

        et_search.setText("");
        name = textView.getText().toString();

        if (list.size() == 0) {
            et_search.setHint("输入关键字搜索" + name);
            list.add(name);
            filterPOIData(name);
            keyWordSearch(poiFilterData);
        } else {
            if (list.get(list.size() - 1).equals(name)) {
                et_search.setHint("输入关键字搜索东、西校区");
                allPOIData();
                keyWordSearch(poiData);
                list.clear();
                list.add("0");
            } else {
                et_search.setHint("输入关键字搜索" + name);
                filterPOIData(name);
                keyWordSearch(poiFilterData);
                list.clear();
                list.add(name);
            }
        }
    }

    //    模糊查询
    private void keyWordSearch(List<POIData> featuresBeans) {
        POIAdapter poiAdapter = new POIAdapter(POISearchActivity.this, featuresBeans);
        lv_poi.setAdapter(poiAdapter);

        final POIAdapter finalPoiAdapter = poiAdapter;
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                Log.i(TAG, "onTextChanged: CharSequence s====" + s);    //输入内容

                if (!"".equals(s)) {
                    if (!"".equals(s.toString())) {
                        iv_poi_clear.setVisibility(View.VISIBLE);
                    } else {
                        iv_poi_clear.setVisibility(View.GONE);
                    }
                    finalPoiAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        清楚按钮的监听事件
        iv_poi_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputContext = et_search.getText().toString();
                if (!"".equals(inputContext)) {
                    et_search.setText("");
                    iv_poi_clear.setVisibility(View.GONE);
                }
            }
        });
    }

    //      ListView页面填充、关键字过滤、一键删除输入框内容
    private void fillListViewAndKeywordShow() {
        int size = list.size();
        if (size == 0) {
            allPOIData();
            keyWordSearch(poiData);
        } else {
            String str = list.get(list.size() - 1);
            if (("0").equals(str)) {
                allPOIData();
                keyWordSearch(poiData);
            } else {
//                filterPOIData(name);
                keyWordSearch(poiFilterData);
            }
        }
    }

    //    默认显示所有POI点的数据
    private void allPOIData() {
        for (int i = 0; i < poiData.size(); i++) {
            String type = poiData.get(i).getType();
            sets.add(type);
        }
        for (String set : sets) {
            mType.add(set);
        }
        poiAdapter.notifyDataSetChanged();
    }

    //    点击按钮过滤POI点    东校区/西校区
    private void filterPOIData(String name) {
        poiFilterData.clear();
        for (int i = 0; i < poiData.size(); i++) {
            String campus = poiData.get(i).getCampus();

            if (name.equals(campus)) {
                poiFilterData.add(poiData.get(i));
            }
        }
        POIAdapter poiFilterAdapter = new POIAdapter(POISearchActivity.this, poiFilterData);
        lv_poi.setAdapter(poiFilterAdapter);
    }

}
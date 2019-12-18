package com.appler.universitysystem.ui.pop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.appler.universitysystem.R;
import com.appler.universitysystem.bean.DZJGData;
import com.appler.universitysystem.ui.activity.LoadUrlActivity;
import com.appler.universitysystem.ui.adapter.DZJGAdapter;
import com.appler.universitysystem.ui.adapter.DZJGSearchAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 党政机关 pop
 */
public class ShowDZJGPop extends PopupWindow {

    private String TAG = getClass().getSimpleName();
    private Activity context;
    private TextView tv_jigou;
    private WebView webView;
    private PopupWindow dzjgPop;
    private List<DZJGData> dzjgData;


    public ShowDZJGPop(Activity context, TextView tv_jigou, WebView webView) {
        super(context);
        this.context = context;
        this.tv_jigou = tv_jigou;
        this.webView = webView;

    }

    /**
     * 使用PopupWindow展示党政机关
     */
    public void showDzjgPopupWindow(TextView tv_jigou, String string) {

//      得到elv页面
        LayoutInflater layoutInflater = context.getLayoutInflater();
        final View inflate = layoutInflater.inflate(R.layout.popup_jg, null);

        final ExpandableListView expandableListView = inflate.findViewById(R.id.elv_jg);
        final ListView listView = inflate.findViewById(R.id.lv_jg_search);

        final EditText et_jg_search_input = inflate.findViewById(R.id.et_jg_search_input);
        TextView tv_jg_search_affirm = inflate.findViewById(R.id.tv_jg_search_affirm);
        final ImageView iv_clear = inflate.findViewById(R.id.iv_jg_clear);

        et_jg_search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入文字前触发
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //text改变过程中触发
                String inputContext = et_jg_search_input.getText().toString();
                if (!"".equals(inputContext)) {
                    iv_clear.setVisibility(View.VISIBLE);
                } else {
                    iv_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //输入文字后触发
            }
        });

        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputContext = et_jg_search_input.getText().toString();
                if (!"".equals(inputContext)) {
                    et_jg_search_input.setText("");
                    iv_clear.setVisibility(View.GONE);
                }

            }
        });

        tv_jg_search_affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                expandableListView.setVisibility(View.GONE);
                List<DZJGData.ChildrenBean> listsFilter = new ArrayList<>();
                String str = et_jg_search_input.getText().toString();
                List<DZJGData.ChildrenBean> children;
                for (int i = 0; i < dzjgData.size(); i++) {
                    children = dzjgData.get(i).getChildren();

                    for (int j = 0; j < children.size(); j++) {
                        String name = children.get(j).getName();
                        if (name.contains(str)) {
                            listsFilter.add(children.get(j));
                        }
                    }
                }

                DZJGSearchAdapter dzjgSearchAdapter = new DZJGSearchAdapter(context, listsFilter);
                listView.setAdapter(dzjgSearchAdapter);

                //导航    详情
                dzjgSearchAdapter.setFilterDZJGItemClickListener(new DZJGSearchAdapter.FilterDZJGItemClickListener() {
                    @Override
                    public void filterItemOnClick(int position, DZJGData.ChildrenBean childrenBean) {
                        String name = childrenBean.getName();
                        String tel = childrenBean.getTel();
                        String neiyou = childrenBean.getNeiyou();
                        String address = childrenBean.getAddress();
                        String jianjie = childrenBean.getJianjie();

                        String zuobiao = childrenBean.getZuobiao();

                        //POINT(113.651729794745 34.8627693989588)
                        if (null != zuobiao && zuobiao.contains("(") && zuobiao.contains(")")) {
                            String subZuobiao = zuobiao.substring(zuobiao.indexOf("(") + 1, zuobiao.indexOf(")"));
                            if (subZuobiao.contains(" ")) {
                                String[] split = subZuobiao.split(" ");
                                if (split.length > 1) {
                                    webView.loadUrl("javascript:zoomToLatLngDZJG('" + split[1] + "','" + split[0] + "','" + name + "','" + tel + "','" + jianjie + "','" + address + "','" + jianjie + "')");
                                }
                            }
                        } else {
                            Toast.makeText(context, "暂无定位", Toast.LENGTH_SHORT).show();
                        }
                        dzjgPop.dismiss();
                    }
                });
                dzjgSearchAdapter.setFilterDzjgDetailsItemClickListener(new DZJGSearchAdapter.FilterDzjgDetailsItemClickListener() {
                    @Override
                    public void detailsItemClickListener(DZJGData.ChildrenBean childrenBean) {

                        String url = childrenBean.getUrl();
                        Log.i(TAG, "detailsItemClickListener: " + url);
                        Intent intent = new Intent(context, LoadUrlActivity.class);
                        intent.putExtra("flag", 1);
                        intent.putExtra("departmentUrl", url);
                        context.startActivity(intent);
                        dzjgPop.dismiss();
                    }
                });

            }
        });

        Gson gson = new Gson();
        dzjgData = gson.fromJson(string, new TypeToken<List<DZJGData>>() {
        }.getType());

        DZJGAdapter dzjgAdapter = new DZJGAdapter(context, dzjgData);
        expandableListView.setAdapter(dzjgAdapter);

        //二级列表 父节点点击事件
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });

        //二级列表 子条目TextView的点击事件
        dzjgAdapter.setDzjgItemClickListener(new DZJGAdapter.DzjgItemClickListener() {
            @Override
            public void itemClickListener(int position, DZJGData.ChildrenBean childrenBean) {
                String name = childrenBean.getName();
                String tel = childrenBean.getTel();
                String neiyou = childrenBean.getNeiyou();
                String address = childrenBean.getAddress();
                String jianjie = childrenBean.getJianjie();

                String zuobiao = childrenBean.getZuobiao();

                //POINT(113.651729794745 34.8627693989588)
                if (null != zuobiao && zuobiao.contains("(") && zuobiao.contains(")")) {
                    String subZuobiao = zuobiao.substring(zuobiao.indexOf("(") + 1, zuobiao.indexOf(")"));
                    if (subZuobiao.contains(" ")) {
                        String[] split = subZuobiao.split(" ");
                        if (split.length > 1) {
                            webView.loadUrl("javascript:zoomToLatLngDZJG('" + split[1] + "','" + split[0] + "','" + name + "','" + tel + "','" + jianjie + "','" + address + "','" + jianjie + "')");
                        }
                    }
                } else {
                    Toast.makeText(context, "暂无定位", Toast.LENGTH_SHORT).show();
                }
                dzjgPop.dismiss();
            }
        });

        dzjgAdapter.setDzjgDetailsItemClickListener(new DZJGAdapter.DzjgDetailsItemClickListener() {
            @Override
            public void detailsItemClickListener(DZJGData.ChildrenBean childrenBean) {

                dzjgPop.dismiss();

                String url = childrenBean.getUrl();
                Intent intent = new Intent(context, LoadUrlActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("departmentUrl", url);
                context.startActivity(intent);

            }
        });

//      使用PopupWindow
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int heigh = manager.getDefaultDisplay().getHeight();

        dzjgPop = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, heigh / 3, true);
        dzjgPop.setFocusable(true);
        dzjgPop.setBackgroundDrawable(new BitmapDrawable());
        dzjgPop.setOutsideTouchable(true);
        dzjgPop.setTouchable(true);
        dzjgPop.setAnimationStyle(R.style.AnimationBottomFade);
        dzjgPop.showAtLocation(tv_jigou, Gravity.BOTTOM, 0, 0);

    }

}

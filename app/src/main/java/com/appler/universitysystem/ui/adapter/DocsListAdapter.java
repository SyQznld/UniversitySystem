package com.appler.universitysystem.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.appler.universitysystem.R;
import com.appler.universitysystem.bean.DocsListData;
import com.appler.universitysystem.utils.CommonUtils;
import com.appler.universitysystem.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.List;

import static com.appler.universitysystem.utils.CommonUtils.filePath;

public class DocsListAdapter extends BaseQuickAdapter<DocsListData, BaseViewHolder> {
    private String TAG = getClass().getSimpleName();
    private Context context;
    private List<DocsListData> data;

    public DocsListAdapter(Context context, @Nullable List<DocsListData> data) {
        super(R.layout.docs_list_layout, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final DocsListData item) {
        CardView cv_docs = helper.getView(R.id.cv_docs);
        TextView tv_docs_title = helper.getView(R.id.tv_docs_title);
        TextView tv_docs_add_time = helper.getView(R.id.tv_docs_add_time);
        TextView tv_docs_ren = helper.getView(R.id.tv_docs_ren);

        tv_docs_title.setText(item.getTitle());

        String addtime = item.getAddtime();
        if (null != addtime && addtime.contains(" ")) {
            String[] split = addtime.split(" ");
            String time = split[0];
            tv_docs_add_time.setText(time);
        }

        tv_docs_ren.setText(item.getRen());


        cv_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = item.getUrl();
                if (!"".equals(url)) {
                    //http://192.168.2.253:8077/uploadfiles/yingxin/4fc434d0fb7a4d02b1ccc01d930cbbd8/迎新功能需求.xlsx
                    String filename = null;
                    if (url.contains("/")) {
                        String[] split = url.split("/");
                        filename = split[split.length - 1];     //迎新功能需求.xlsx

                        //下载文档并查看
                        final File file = new File(filePath + File.separator + filename);
                        if (!file.exists()) {
                            CommonUtils.downloadNetFile(url);
                        }
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CommonUtils.openFile(file, context);
                            }
                        }, 1000);
                    }

                }else {
                    ToastUtils.showShortToast(context,"暂无文件");
                }


            }
        });

    }
}

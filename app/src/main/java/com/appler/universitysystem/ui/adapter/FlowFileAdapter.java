package com.appler.universitysystem.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.appler.universitysystem.R;
import com.appler.universitysystem.bean.FlowFileData;
import com.appler.universitysystem.utils.CommonUtils;
import com.appler.universitysystem.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.List;

import static com.appler.universitysystem.utils.CommonUtils.filePath;

public class FlowFileAdapter extends BaseQuickAdapter<FlowFileData, BaseViewHolder> {
    private String TAG = getClass().getSimpleName();
    private Context context;
    private List<FlowFileData> data;

    public FlowFileAdapter(Context context, @Nullable List<FlowFileData> data) {
        super(R.layout.docs_list_layout, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final FlowFileData item) {
        CardView cv_docs = helper.getView(R.id.cv_docs);
        TextView tv_docs_title = helper.getView(R.id.tv_docs_title);
        TextView tv_docs_add_time = helper.getView(R.id.tv_docs_add_time);
        TextView tv_docs_ren = helper.getView(R.id.tv_docs_ren);

        String mFilename = item.getFilename();
        String mAddtime = item.getFiletime();
        String mUploadr = item.getUploadr();
        Log.i(TAG, "convert: flowFile===mFilename:" + mFilename + "   mAddtime:" + mAddtime + "   mUploadr:" + mUploadr);

        tv_docs_title.setText(mFilename);

        if (null != mAddtime && mAddtime.contains(" ")) {
            String[] split = mAddtime.split(" ");
            String time = split[0];
            tv_docs_add_time.setText(time);
        }

        tv_docs_ren.setText(mUploadr);


        cv_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //http://192.168.2.253:8077/uploadfiles/yingxin/4fc434d0fb7a4d02b1ccc01d930cbbd8/迎新功能需求.xlsx
                String filename = null;
                final String url = item.getFileurl();
                //url       http://192.168.2.253:8077/uploadfiles/flow/601e3dd59a37405b9d2e9ec029cbc5e7/迎新功能需求.xlsx
                Log.i(TAG, "onClick: url===" + url);
                if (null != url && url.contains("/")) {
                    String[] split = url.split("/");
                    filename = split[split.length - 1];     //迎新功能需求.xlsx
                }
                //下载文档并查看
                final String finalFilename = filename;
                if (!"".equals(url) && url.contains("/")) {
                    final File file = new File(filePath + File.separator + finalFilename);
                    if (!file.exists()) {
                        CommonUtils.downloadNetFile(url);
                        Log.i(TAG, "onClick: 下载以后 getFilePath   " + file.getAbsolutePath());
                    }
                    Log.i(TAG, "onClick: 下载以后 getFilePath   " + file.getAbsolutePath());
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtils.openFile(file, context);
                        }
                    }, 1000);

                } else {
                    ToastUtils.showShortToast(context, "暂无文件");
                }

            }
        });

    }
}

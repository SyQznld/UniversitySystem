package com.appler.universitysystem.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appler.universitysystem.R;
import com.appler.universitysystem.base.BaseActivity;
import com.appler.universitysystem.utils.CommonUtils;
import com.appler.universitysystem.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;

/**
 * webview加载网页
 */
public class LoadUrlActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.toolbar_info)
    Toolbar toolbar;
    @BindView(R.id.wv_loadUrl)
    WebView webView;

    private String url;
    private int flag; //判断从哪个页面进入

    @Override
    public int bindLayout() {
        return R.layout.activity_load_url;
    }

    @Override
    public void doBusiness(Context context) {
        flag = getIntent().getIntExtra("flag", 0);
        if (flag == 1) {   //党政机关详情
            toolbar.setTitle("机构详情");
            url = getIntent().getStringExtra("departmentUrl");
        } else if (flag == 2) {   //流程全景漫游导航
            url = getIntent().getStringExtra("qjmy_url");
            Log.i(TAG, " url================: "  + url);
            if (url.contains("quanjingdian")) {
                toolbar.setTitle("校园景观");
            } else {
                toolbar.setTitle("漫游导航");
            }
        }
        initWebView(url);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWebView(String url) {
        WebSettings settings = webView.getSettings();
        webView.requestFocusFromTouch();
        settings.setJavaScriptEnabled(true);   //支持js
        settings.setAllowFileAccess(true);     //设置可以访问文件
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        settings.setSupportZoom(true); //支持缩放
        // 1、LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
        // 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布
        settings.supportMultipleWindows(); //多窗口
        //  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存

        settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点 webview
        settings.setBuiltInZoomControls(true); //设置支持缩放
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        settings.setDomStorageEnabled(true); //


        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });


        // 加载需要显示的网页
        webView.loadUrl(url);


        //  使用
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {

                if (!"".equals(url) && url.contains("/")) {
                    String[] split = url.split("/");
                    String filename = split[split.length - 1];
                    final File file = new File(CommonUtils.filePath + File.separator + filename);
                    if (!file.exists()) {
                        CommonUtils.downloadNetFile(url);
                    }
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            CommonUtils.openFile(file, LoadUrlActivity.this);
                        }
                    }, 1000);
                }else {
                    ToastUtils.showShortToast(LoadUrlActivity.this,"暂无文件");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空所有Cookie
        CookieSyncManager.createInstance(getApplicationContext());  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearCache(true);
    }

}

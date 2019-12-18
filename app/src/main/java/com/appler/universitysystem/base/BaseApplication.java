package com.appler.universitysystem.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.appler.universitysystem.utils.CommonUtils;
import com.appler.universitysystem.utils.dao.UpgradeHelper;

import java.io.File;
import java.util.LinkedList;

import universitysystem.greendao.gen.DaoMaster;
import universitysystem.greendao.gen.DaoSession;

import static com.appler.universitysystem.utils.CommonUtils.writeTxtToFile;


public class BaseApplication extends Application {
    public static LinkedList<Activity> activityLinkedList;
    private static BaseApplication baseApplication;
    private static DaoSession daoSession;
    private String TAG = "BaseApplication";
    private Context context;

    private static String txtPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "高校迎新系统" + File.separator;
    private static String url;

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }

    public static DaoSession getDaoInstance() {
        return daoSession;
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        context = this;
        baseApplication = this;


        String fileName = "url.txt";
        String strFilePath = txtPath + fileName;
        if (new File(strFilePath).exists()) {
            url = CommonUtils.ReadTxtFromSDCard(txtPath, "url.txt");
            Log.i(TAG, "onCreate: url===" + url);
            setUrl(url);
        } else {
            writeTxtToFile("http://192.168.1.253", txtPath, fileName);


            url = CommonUtils.ReadTxtFromSDCard(txtPath, "url.txt");
            setUrl(url);
        }


        setupDatabase();

        activityLinkedList = new LinkedList<>();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityLinkedList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityLinkedList.remove(activity);
            }
        });
    }


    public static void setUrl(String url) {
        BaseApplication.url = url;
    }

    public static String getUrl() {
        return url;
    }

    private void setupDatabase() {
        UpgradeHelper helper = new UpgradeHelper(context, "universitysystem.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();

    }

    public Context getContext() {
        return context;
    }

    //两次返回键返回
    public void exitApp() {
        //当前容器内activity列表
        for (Activity activity : activityLinkedList) {
            Log.i(TAG, "exitApp: " + activity.getLocalClassName());
        }
        //逐个退出activity
        for (Activity activity : activityLinkedList) {
            activity.finish();
        }
        //结束进程
        System.exit(0);
    }
}

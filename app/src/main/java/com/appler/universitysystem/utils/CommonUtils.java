package com.appler.universitysystem.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.appler.universitysystem.base.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

import static android.content.Context.TELEPHONY_SERVICE;


public class CommonUtils {

    public static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "高校迎新系统" + File.separator + "file";


    /**
     * 读取assets文件夹下json文件
     */
    public static String readJsonFromAssets(String fileName, Activity activity) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = activity.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * The IMEI: 仅仅只对Android手机有效
     * 采用此种方法，需要在AndroidManifest.xml中加入一个许可：android.permission.READ_PHONE_STATE，并且用
     * 户应当允许安装此应用。作为手机来讲，IMEI是唯一的，它应该类似于 359881030314356（除非你有一个没有量产的手
     * 机（水货）它可能有无效的IMEI，如：0000000000000）。
     *
     * @return imei
     */
    public static String getIMEI(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getApplicationContext().getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }




    public static String ReadTxtFromSDCard(String txtPath,String filename) {

        StringBuilder sb = new StringBuilder("");
        //判断是否有读取权限
        if (Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED)) {

            //打开文件输入流
            try {
                FileInputStream input = new FileInputStream(txtPath + filename);
                byte[] temp = new byte[1024];

                int len = 0;
                //读取文件内容:
                while ((len = input.read(temp)) > 0) {
                    sb.append(new String(temp, 0, len));
                }
                //关闭输入流
                input.close();
            } catch (java.io.IOException e) {
                Log.e("ReadTxtFromSDCard", "ReadTxtFromSDCard");
                e.printStackTrace();
            }

        }
        return sb.toString();
    }


    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        // 每次写入时，都换行写
//        String strContent = strcontent + "\r\n";
        String strContent = strcontent + "\r";
        try {
            File file = new File(filePath + fileName);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + filePath + fileName);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }


    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }









    /**
     * 判断网络
     */
    public static boolean isNetConnected(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return !(!isWifiConnected(context) && !isMobileConnected(context));
        } else {
            return false;
        }
    }

    /**
     * 判断WIFI网络是否可用
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();

            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     */
    public static boolean isMobileConnected(Context context) {

        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }




    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }



    /**
     * 绘制图标右上角的未读消息数量显示
     *
     * @param context 上下文
     * @param icon    需要被添加的icon的资源ID
     * @param news    未读的消息数量
     * @return drawable
     */
    @SuppressWarnings("unused")
    public static Drawable displayNewsNumber(Context context, int icon, int news) {
        // 初始化画布
        int iconSize = (int) context.getResources().getDimension(
                android.R.dimen.app_icon_size);
        // Bitmap contactIcon = Bitmap.createBitmap(iconSize, iconSize,
        // Config.ARGB_8888);
        Bitmap iconBitmap = BitmapFactory.decodeResource(
                context.getResources(), icon);
        Canvas canvas = new Canvas(iconBitmap);
        // 拷贝图片
        Paint iconPaint = new Paint();
        iconPaint.setDither(true);// 防抖动
        iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理
        Rect src = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());
        Rect dst = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());
        canvas.drawBitmap(iconBitmap, src, dst, iconPaint);
        // 启用抗锯齿和使用设备的文本字距
        Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        countPaint.setColor(Color.RED);
        canvas.drawCircle(iconSize - 13, 20, 10, countPaint);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        // textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(19f);
        canvas.drawText(String.valueOf(news), iconSize - 18, 27, textPaint);
        return new BitmapDrawable(iconBitmap);
    }

    /**
     * 打开文件
     */
    public static void openFile(File file, Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);  //设置intent的Action属性
        String type = getMIMEType(file);  //获取文件file的MIME类型
        Log.i("", "onDownloadStart openFile: " + type);
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);   //设置intent的data和Type属性。

        context.startActivity(intent);     //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
    }

    private static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        int dotIndex = fName.lastIndexOf(".");  //获取后缀名前的分隔符"."在fName中的位置。
        if (dotIndex < 0) {
            return type;
        }

        String end = fName.substring(dotIndex, fName.length()).toLowerCase();  /* 获取文件的后缀名*/
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    /**
     * 下载文件
     */
    public static void downloadNetFile(final String url) {
        //下载路径，如果路径无效了，可换成你的下载路径
        // final String url = "http://192.168.2.193:8002/Upload/2018年08月01日 08时37分32秒/【离】员工离职交接手续表.xlsx";
        Log.i("downloadNetFile", "DOWNLOAD url: " + url);
        Log.i("downloadNetFile", "DOWNLOAD filePath: " + filePath);
        if (!new File(filePath).exists()) {
            makeRootDirectory(filePath);
        }
        final long startTime = System.currentTimeMillis();
        Log.i("DOWNLOAD", "startTime=" + startTime);
        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // 下载失败
                e.printStackTrace();
                ToastUtils.showToast(BaseApplication.getBaseApplication().getContext(), "获取文件失败~");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Sink sink = null;
                BufferedSink bufferedSink = null;
                try {
                    String substring;
                    if (url.contains(" ")) {
                        substring = url.substring(url.lastIndexOf(" ") + 1).trim();
                    } else {
                        substring = url.substring(url.lastIndexOf("/") + 1).trim();
                    }
                    File dest = new File(filePath, substring);
//                    File dest = new File(filePath, url.substring(url.lastIndexOf("/") + 1));
                    Log.i("DOWNLOAD", "dest.getAbsolutePath(): " + dest.getAbsolutePath());


                    sink = Okio.sink(dest);
                    bufferedSink = Okio.buffer(sink);
                    bufferedSink.writeAll(response.body().source());
                    bufferedSink.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedSink != null) {
                        bufferedSink.close();
                    }
                }
            }
        });


    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            Log.i("", "getFilePath makeRootDirectory: " + file.getAbsolutePath());
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    public static boolean dealImage(String sourceFilePath, String targetFilePath) {
        try {
            int dh = 1024;
            int dw = 1024;
            BitmapFactory.Options factory = new BitmapFactory.Options();
            factory.inJustDecodeBounds = true; //当为true时  允许查询图片不为 图片像素分配内存
            InputStream is = new FileInputStream(sourceFilePath);
            Bitmap bmp = BitmapFactory.decodeStream(is, null, factory);
            int hRatio = (int) Math.ceil(factory.outHeight / (float) dh); //图片是高度的几倍
            int wRatio = (int) Math.ceil(factory.outWidth / (float) dw); //图片是宽度的几倍
            System.out.println("hRatio:" + hRatio + "  wRatio:" + wRatio);
            //缩小到  1/ratio的尺寸和 1/ratio^2的像素
            if (hRatio > 1 || wRatio > 1) {
                if (hRatio > wRatio) {
                    factory.inSampleSize = hRatio;
                } else
                    factory.inSampleSize = wRatio;
            }
            factory.inJustDecodeBounds = false;
            is.close();
            is = new FileInputStream(sourceFilePath);
            bmp = BitmapFactory.decodeStream(is, null, factory);
            OutputStream outFile = new FileOutputStream(targetFilePath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 60, outFile);
            outFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private final static String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

}

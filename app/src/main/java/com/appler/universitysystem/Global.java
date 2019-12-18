package com.appler.universitysystem;

/**
 * 全局变量
 */
public class Global {
    public static final String APKNAME = "郑州师范三维校园app.apk";

    public static final int LOADING_STATE = 1001;//加载中
    public static final int LOADING_FAIL = 1002;//加载失败
    public static final int LOADING_SUCEESS = 1003;//加载成功
    //http://192.168.2.204:8087/   高校房产系统


    //基本URL   动态ip配置 读取TXT
//    public static String BASE_URL_IP = BaseApplication.getUrl().trim();
//    public static String URL = BASE_URL_IP + ":8011/";
//    public static String BASIC_MAP_IP = BASE_URL_IP + "/DataServices/arcgis/";


//    public static String BASE_URL_IP = "http://192.168.1.253"; //ZCX
//    public static String URL = BASE_URL_IP + ":8077/"; //ZCX
//    public static String BASIC_MAP_IP = BASE_URL_IP + "/DataServices/arcgis/";


    /**
     * 郑州师范学院服务器
     */
    public static String BASE_URL_IP = "http://125.219.151.1";
    public static String URL = BASE_URL_IP + ":80/";
    public static String BASIC_MAP_IP = URL + "DataServices/arcgis/";


    //http://192.168.2.253:8077/yingxinimg/lushang/1/tour.html
    public static String QJMY_FLOW_URL = URL + "yingxinimg/lushang"; //全景漫游 url  流程点
    public static String QJMY_IMG_URL = URL + "yingxinimg/quanjingdian/"; //全景漫游 url  景观点
    public static String QJMY_HOUZHUI = "/tour.html"; //全景漫游 url 后缀html


    //默认加载影像 ip
    public static String YX = "daxuecheng";
    public static String YX_ZZSF = "zzsf201905";      //郑州师范影像
    public static String YX_WDT = "zzsfxg3";    //微地图


    //东西校区
    public static final String CAMPUS_DONG = "东校区";
    public static final String CAMPUS_XI = "西校区";


    public static String HOST_CLOSE = "No route to host";   //主机没开启
    public static String TOAST_HOST_CLOSE = "服务器连接错误，请稍后再试";
    public static String CONN_FAIL = "Failed to connect to";
    public static String TOAST_CONN_FAIL = "服务器连接错误，请检查网络设置";
    public static final String PC_BACK_HTTP500 = "HTTP 500 Internal Server Error";      // HTTP 500 Internal Server Error
    public static final String PC_BACK_TIMEOUT = "time out";      // 连接超时
    public static final String PC_BACK_TIMEOUT_ = "timeout";      // 连接超时
    public static final String TOAST_HTTP500 = "服务器连接错误，请稍后再试";      //  吐司提示  HTTP 500 Internal Server Error
    public static final String TOAST_TIMEOUT = "连接超时，请检查网络连接，稍后再试";      //  吐司提示  超时


    public static final int REQUEST_CODE_POI = 1;   //POI搜索点
    public static final int REQUEST_CODE_DZJG_xq = 2;  //党政机关详情

}

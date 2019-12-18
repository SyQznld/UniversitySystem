package com.appler.universitysystem.api;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 接口
 */

public interface ApiService {

    //登录
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> login(
            @Field("flag") String flag,
            @Field("username") String Name,
            @Field("password") String Password);

    /**
     * 党政机关
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getDZJGList(
            @Field("flag") String flag);


    /**
     * 流程列表
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getFlowList(
            @Field("flag") String flag,
            @Field("userid") String userId,
            @Field("campus") String selectXiaoqu
    );

    /**
     * 流程文件
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getFlowFile(
            @Field("flag") String flag,
            @Field("flowid") String flowid
    );

    /**
     * 流程导航漫游
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getFlowNavigation(
            @Field("flag") String flag,
            @Field("flowid") String flowid,
            @Field("campus") String campusName
    );

    /**
     * 完成某个流程  userid   flowid
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> completeOneFlow(
            @Field("flag") String flag,
            @Field("userid") String userId,
            @Field("flowid") String flowid
    );

    /**
     * 资料列表
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getDocsList(
            @Field("flag") String flag);

    /**
     * 消息列表
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getMessageList(
            @Field("flag") String flag);

    /**
     * 全景图片列表
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getQuanjingPicList(
            @Field("flag") String flag,
            @Field("quanjingtype") String quanjingtype);


    /**
     * 重置密码
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> resetPassword(
            @Field("flag") String flag,
            @Field("id") String userId,
            @Field("O_password") String oldpwd,
            @Field("N_password") String newpwd);


    /**
     * 所有poi点数据列表
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getPoiAll(
            @Field("flag") String flag);


    /**
     * 根据类型获取 poi点数据列表   单类型选择
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getPoiTypeList(
            @Field("flag") String flag);


    /**
     * 所有poi点类型列表
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getPoiByType(
            @Field("flag") String flag,
            @Field("type") String type);


    /**
     * 获取全景图片 列表浏览
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getQuanjingImgList(
            @Field("flag") String flag);


    /**
     * 获取某一个景观点的全景 根据name匹配
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getQuanjingPointByName(
            @Field("flag") String flag,
            @Field("name") String name);

    /**
     * 流程图片
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getFlowPicture(
            @Field("flag") String flag);

    /**
     * apk时间限制
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getApkTimeLimit(
            @Field("flag") String flag);


    /**
     * 全景类型
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> getQjTypeList(
            @Field("flag") String flag);


    /**
     * 版本更新
     */
    @FormUrlEncoded
    @POST("handler/yingxin/appHandler.ashx/")
    Observable<ResponseBody> versionUpdate(
            @Field("flag") String flag);

}

package com.appler.universitysystem.dao;

import com.appler.universitysystem.base.BaseApplication;
import com.appler.universitysystem.bean.UserData;

import java.util.List;

/**
 * 保存用户信息  数据库 基本操作
 */

public class UserDao {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param data
     */
    public static void insertUserData(UserData data) {
        BaseApplication.getDaoInstance().getUserDataDao().insert(data);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteUserData(long id) {
        BaseApplication.getDaoInstance().getUserDataDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param data
     */
    public static void updateUserData(UserData data) {
        BaseApplication.getDaoInstance().getUserDataDao().update(data);
    }


    /**
     * 查询全部数据
     */
    public static List<UserData> queryAll() {
        return BaseApplication.getDaoInstance().getUserDataDao().loadAll();
    }



    public static UserData getUser() {
        List<UserData> accountTables = BaseApplication.getDaoInstance().getUserDataDao().loadAll();
        if (accountTables.size() > 0) {
            UserData adminData = accountTables.get(0);
            return adminData;
        }
        return null;

    }


    public static String getUserID() {
        List<UserData> accountTables = BaseApplication.getDaoInstance().getUserDataDao().loadAll();
        if (accountTables.size() > 0) {
            UserData userData = accountTables.get(0);
            return userData.getUserId();
        }
        return null;

    }

    /**
     * 删除全部数据
     */
    public static void deleAllData() {
        BaseApplication.getDaoInstance().getUserDataDao().deleteAll();
    }


}

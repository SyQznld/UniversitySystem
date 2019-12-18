package com.appler.universitysystem.utils.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;

import universitysystem.greendao.gen.DaoMaster;
import universitysystem.greendao.gen.UserDataDao;


public class UpgradeHelper extends DaoMaster.OpenHelper {
    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory) {
        super(context, name, cursorFactory);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        super.onUpgrade(db, oldVersion, newVersion);

        Database database = new StandardDatabase(db);
        MigrationHelper.getInstance().migrate(database, UserDataDao.class);
    }
}

package com.example.mind.hw6;

import android.app.Application;

import com.example.mind.hw6.model.AppDevOpenHelper;
import com.example.mind.hw6.model.DaoMaster;
import com.example.mind.hw6.model.DaoSession;
import com.example.mind.hw6.model.Profile;
import com.example.mind.hw6.model.ProfileDao;
import com.example.mind.hw6.model.TaskDao;

import org.greenrobot.greendao.database.Database;

import java.util.List;

public class App extends Application {

    private static App mApp;
    private static DaoSession mDaoSession;
    private static ProfileDao mProfileDao;
    private static TaskDao mTaskDao;


    public static App getmApp() {
        return mApp;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppDevOpenHelper appDevOpenHelper = new AppDevOpenHelper(this, "tasks.ds");
        Database db = appDevOpenHelper.getWritableDb();

        mDaoSession = new DaoMaster(db).newSession();
        mProfileDao= mDaoSession.getProfileDao();
        mTaskDao = mDaoSession.getTaskDao();
        mApp = this;
    }

    public List getProfileByEmail(String email){

     List<Profile> list = mProfileDao.queryBuilder().where(ProfileDao.Properties.MEmail.eq(email)).list();
        return list;
    }
}

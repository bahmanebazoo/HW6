package com.example.mind.hw6.database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.mind.hw6.model.Profile;
import com.example.mind.hw6.model.ProfileDao;
import com.example.mind.hw6.model.Task;
import com.example.mind.hw6.model.TaskDao;

import org.greenrobot.greendao.database.Database;


import java.util.List;

public class Repository {

    private static Repository instance;
    private Database mDatabase;
    ProfileDao mProfileDao = App.getmApp().getmDaoSession().getProfileDao();
    TaskDao mTaskDao = App.getmApp().getmDaoSession().getTaskDao();
    private Context mContext;
    List<Profile> mProfiles;
    List<Task> mTasks;


    private Repository(Context context) {
        AppDevOpenHelper appDevOpenHelper = new AppDevOpenHelper(context, "tasks.ds");
        mDatabase = appDevOpenHelper.getWritableDb();


    }

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public List<Task> getList(Long user_id) {
        mTasks = mTaskDao
                .queryBuilder()
                .where(TaskDao.Properties.MUserID.eq(user_id)).list();

        return mTasks;
    }


    public void update(Task task) {

        mTaskDao.update(task);
    }


    public Task getTask(Long id) {
        mTasks = mTaskDao
                .queryBuilder()
                .where(TaskDao.Properties.MTaskID.eq(id)).list();
        if (mTasks.size() > 0)
            return mTasks.get(0);
        return null;
    }


    public List<Task> getListForShow(int tab, Long user_id, String s) {
        List<Task> ListForRecyclerView = getList(user_id);

        if (tab == 1) {
            for (int i = ListForRecyclerView.size() - 1; i >= 0; i--) {
                if ((s != null && !s.equals("")) &&
                        (!ListForRecyclerView.get(i).getMTitle().contains(s)
                                || ListForRecyclerView.get(i).getMDescription().contains(s))) {
                    ListForRecyclerView.remove(i);
                }
            }

        } else if (tab == 2) {
            for (int i = ListForRecyclerView.size() - 1; i >= 0; i--) {
                if (!ListForRecyclerView.get(i).getMDone() ||
                        (s != null && !s.equals("")) && (!ListForRecyclerView.get(i).getMTitle().contains(s)
                                ||
                                ListForRecyclerView.get(i).getMDescription().contains(s))) {
                    ListForRecyclerView.remove(i);
                }
            }
        } else if (tab == 3) {
            for (int i = ListForRecyclerView.size() - 1; i >= 0; i--) {
                if (ListForRecyclerView.get(i).getMDone() ||
                        (s != null && !s.equals("")) && (!ListForRecyclerView.get(i).getMTitle().contains(s)
                                ||
                                ListForRecyclerView.get(i).getMDescription().contains(s))) {
                    ListForRecyclerView.remove(i);
                }
            }
        }
        Log.d("bahman", "" + ListForRecyclerView.size());

        return ListForRecyclerView;

    }

    public Long mAddTask(Task task) {

        return mTaskDao.insert(task);

    }

    public void removeTask(Long id) {
        mTasks = mTaskDao
                .queryBuilder()
                .where(TaskDao.Properties.MTaskID.eq(id)).list();
        if (mTasks.size() > 0) {
            Task task = mTasks.get(0);
            mTaskDao.delete(task);
        }

    }

    public void removeTasks(Long user_id) {
        if (getProfile(user_id) != null) {
            mTasks = mTaskDao
                    .queryBuilder()
                    .where(ProfileDao.Properties.MUserID.eq(user_id)).list();
            if (mTasks.size() > 0) {
                for (int i = mTasks.size() - 1; i >= 0; i--) {
                    mTaskDao.delete(mTasks.get(i));
                }
            }
            mProfileDao.delete(getProfile(user_id));
        }
    }


    public void addProfile(Context context, Profile profile) {
        mProfileDao.insert(profile);
        Toast.makeText(context, profile.getMEmail() + "Sign Upped", Toast.LENGTH_SHORT);
    }

    public List<Profile> getProfileList() {

        mProfiles = mProfileDao
                .queryBuilder()
                .list();


        if (mProfiles.size() > 0) {
            return mProfiles;

        }
        return null;

    }

    public Profile getProfile(Long user_id) {
        mProfiles = mProfileDao
                .queryBuilder()
                .where(ProfileDao.Properties.MUserID.eq(user_id)).list();
        if (mProfiles.size() > 0) {
            return mProfiles.get(0);
        }
        return null;
    }


    public void removeProfile(Context context, Long user_id) {
        mProfiles = mProfileDao
                .queryBuilder()
                .where(ProfileDao.Properties.MUserID.eq(user_id)).list();
        Profile profile = mProfiles.get(0);
        mProfileDao.delete(profile);
        Toast.makeText(context, profile.getMEmail() + "Profile Deleted", Toast.LENGTH_SHORT).show();
    }

    public Profile getProfileByEmail(String email) {
        mProfiles = mProfileDao.queryBuilder().where(ProfileDao.Properties.MEmail.eq(email)).list();
        if (mProfiles.size() > 0) {
            return mProfiles.get(0);
        }

        return null;
    }

}


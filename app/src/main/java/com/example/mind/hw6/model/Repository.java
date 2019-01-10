package com.example.mind.hw6.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.mind.hw6.database.ProfileCursorWrapper;
import com.example.mind.hw6.database.TaskBaseHelper;
import com.example.mind.hw6.database.TaskCursorWrapper;
import com.example.mind.hw6.database.TaskDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository {
    private static Repository instance;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    // private LinkedHashMap<UUID, Task> mToDoListLinkedHashMap;
//    private LinkedHashMap<UUID, Task> mTempToDoListLinkedHashMap;


    private Repository(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TaskBaseHelper(mContext).getWritableDatabase();

    }

    public static Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public List<Task> getList(UUID user_uuid) {
        List<Task> tasks = new ArrayList<>();
        String whereClause = TaskDbSchema.TaskTable.Colms.USER_UUID + " =? ";
        String[] whereArgs = new String[]{user_uuid.toString()};
        TaskCursorWrapper taskCursorWrapper = queryTask(whereClause, whereArgs);
        try {
            if (taskCursorWrapper.getCount() == 0)
                return tasks;
            taskCursorWrapper.moveToFirst();
            while (!taskCursorWrapper.isAfterLast()) {
                tasks.add(taskCursorWrapper.getTask());
                taskCursorWrapper.moveToNext();
            }

        } finally {
            taskCursorWrapper.close();
        }
        return tasks;
    }


    private TaskCursorWrapper queryTask(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(TaskDbSchema.TaskTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new TaskCursorWrapper(cursor);
    }


    public void update(Task toDoList) {
        ContentValues values = getContentValues(toDoList);
        String whereClause = TaskDbSchema.TaskTable.Colms.UUID + " = ? ";
        mDatabase.update(TaskDbSchema.TaskTable.NAME, values,
                whereClause, new String[]{toDoList.getUUID().toString()});
    }


    public Task getTask(UUID id) {
        String whereClause = TaskDbSchema.TaskTable.Colms.UUID + " =? ";
        String[] whereArgs ;
        whereArgs = new String[]{id.toString()};
        TaskCursorWrapper taskCursorWrapper = queryTask(whereClause, whereArgs);

        try {
            if (taskCursorWrapper.getCount() == 0)
                return null;
            taskCursorWrapper.moveToFirst();
            return taskCursorWrapper.getTask();

        } finally {
            taskCursorWrapper.close();
        }
    }


    public List<Task> getListForShow(int tab, UUID user_uuid) {
        List<Task> ListForRecyclerView = getList(user_uuid);


        if (tab == 2) {
            for (int i = ListForRecyclerView.size() - 1; i >= 0; i--) {
                if (!ListForRecyclerView.get(i).isDone()) {
                    ListForRecyclerView.remove(i);
                }
            }
        } else if (tab == 3) {
            for (int i = ListForRecyclerView.size() - 1; i >= 0; i--) {
                if (ListForRecyclerView.get(i).isDone()) {
                    ListForRecyclerView.remove(i);
                }
            }
        }
        Log.d("bahman", "" + ListForRecyclerView.size());

        return ListForRecyclerView;

    }

    public UUID mAddToDo(Task toDoList) {
        ContentValues values = getContentValues(toDoList);
        mDatabase.insert(TaskDbSchema.TaskTable.NAME, null, values);

        return toDoList.getUUID() ;
    }

    public void removeTask(UUID uuid) {
        String whereClause = TaskDbSchema.ProfileTable.Colms.UUID + " =? ";
        String[] whereArgs = new String[]{uuid.toString()};
        mDatabase.delete(TaskDbSchema.TaskTable.NAME, whereClause, whereArgs);
    }

    public ContentValues getContentValues(Task toDoList) {
        ContentValues values = new ContentValues();
        values.put(TaskDbSchema.TaskTable.Colms.UUID, toDoList.getUUID().toString());
        values.put(TaskDbSchema.TaskTable.Colms.USER_UUID, toDoList.getUserUUID().toString());
        values.put(TaskDbSchema.TaskTable.Colms.TITLE, toDoList.getTitle());
        values.put(TaskDbSchema.TaskTable.Colms.DESCRIPTION, toDoList.getDescription());
        values.put(TaskDbSchema.TaskTable.Colms.DATE, toDoList.getDate().getTime());
        values.put(TaskDbSchema.TaskTable.Colms.BOOLEAN_DONE, toDoList.isDone() ? 1 : 0);

        return values;

    }

    public void addProfile(Context context, Profile profile) {
        ContentValues values = getProfileContentValues(profile);
        mDatabase.insert(TaskDbSchema.ProfileTable.NAME, null, values);
        Toast.makeText(context, profile.getEmail() + "Sign Upped", Toast.LENGTH_SHORT);
    }

    public List<Profile> getProfileList() {

        List<Profile> profiles = new ArrayList<>();
        ProfileCursorWrapper profileCursorWrapper = querryProfile(null, null);

        try {
            if (profileCursorWrapper.getCount() == 0) {
                return profiles;
            }
            profileCursorWrapper.moveToFirst();
            while (profileCursorWrapper.isAfterLast()) {
                profileCursorWrapper.getProfile();

                profileCursorWrapper.moveToNext();
            }
        } finally {
            profileCursorWrapper.close();
        }
        return profiles;
    }

    public Profile getProfile(UUID uuid) {
        String whereClause = TaskDbSchema.ProfileTable.Colms.UUID + " =? ";
        String[] whereArgs = new String[]{uuid.toString()};
        ProfileCursorWrapper profileCursorWrapper = querryProfile(whereClause, whereArgs);

        try {
            if (profileCursorWrapper.getCount() == 0) {
                return null;
            }
            profileCursorWrapper.moveToFirst();

            return profileCursorWrapper.getProfile();

        } finally {
            profileCursorWrapper.close();
        }
    }

    private ProfileCursorWrapper querryProfile(String whereClause, String[] whereArgs) {
        Cursor cursor =  mDatabase.query(TaskDbSchema.ProfileTable.NAME,

                null, whereClause, whereArgs,
                null, null, null);
        return new ProfileCursorWrapper(cursor);
    }

/*    public int getIndexOfProfile(UUID uuid) {
        for (int i = 0; i < mProfiles.size(); i++) {
            if (mProfiles.get(i).getUUID() == uuid)
                return i;
        }
        return -1;
    }*/

    public void removeProfile(Context context, UUID uuid) {
        String whereClause = TaskDbSchema.ProfileTable.Colms.UUID + " =? ";
        String[] whereArgs = new String[] {uuid.toString()};
        mDatabase.delete(TaskDbSchema.ProfileTable.NAME,whereClause,whereArgs);
        Toast.makeText(context, getProfile(uuid).getEmail() + "Profile Deleted", Toast.LENGTH_SHORT).show();
    }

    public Profile getProfileByEmail(String email) {
        String whereClause = TaskDbSchema.ProfileTable.Colms.E_MAIL + " =? ";
        String[] whereArgs = new String[]{email};
        ProfileCursorWrapper profileCursorWrapper = querryProfile(whereClause, whereArgs);

        try {
            if (profileCursorWrapper.getCount() == 0) {
                return null;
            }
            profileCursorWrapper.moveToFirst();

            return profileCursorWrapper.getProfile();


        } finally {
            profileCursorWrapper.close();
        }
    }

    public ContentValues getProfileContentValues(Profile profile) {

        ContentValues values = new ContentValues();
        values.put(TaskDbSchema.ProfileTable.Colms.UUID, profile.getUUID().toString());
        values.put(TaskDbSchema.ProfileTable.Colms.E_MAIL, profile.getEmail());
        values.put(TaskDbSchema.ProfileTable.Colms.NAME, profile.getName());
        values.put(TaskDbSchema.ProfileTable.Colms.DESCRIPTION, profile.getDescription());
        values.put(TaskDbSchema.ProfileTable.Colms.CALL_NUMBER, profile.getCallNumber());
        values.put(TaskDbSchema.ProfileTable.Colms.DATE, profile.getDate().getTime());
        values.put(TaskDbSchema.ProfileTable.Colms.PASSWORD, profile.getPasssword());

        return values;

    }

}


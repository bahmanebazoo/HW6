package com.example.mind.hw6.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskBaseHelper extends SQLiteOpenHelper {

    public TaskBaseHelper(Context context) {
        super(context, TaskDbSchema.NAME, null, TaskDbSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TaskDbSchema.TaskTable.NAME + "("
                + "_id integer primary key autoincrement, " +
                TaskDbSchema.TaskTable.Colms.UUID + "," +
                TaskDbSchema.TaskTable.Colms.USER_UUID + "," +
                TaskDbSchema.TaskTable.Colms.TITLE + "," +
                TaskDbSchema.TaskTable.Colms.DESCRIPTION + "," +
                TaskDbSchema.TaskTable.Colms.DATE + "," +
                TaskDbSchema.TaskTable.Colms.BOOLEAN_DONE  +
                ")"
        );
        db.execSQL("create table " + TaskDbSchema.ProfileTable.NAME + "("
                + "_id integer primary key autoincrement, " +
                TaskDbSchema.ProfileTable.Colms.UUID  + "," +
                TaskDbSchema.ProfileTable.Colms.E_MAIL  + "," +
                TaskDbSchema.ProfileTable.Colms.NAME + "," +
                TaskDbSchema.ProfileTable.Colms.DESCRIPTION+ "," +
                TaskDbSchema.ProfileTable.Colms.DATE+ "," +
                TaskDbSchema.ProfileTable.Colms.CALL_NUMBER + "," +
                TaskDbSchema.ProfileTable.Colms.PASSWORD +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// WHAT SHOULD BE HERE????
    }
}

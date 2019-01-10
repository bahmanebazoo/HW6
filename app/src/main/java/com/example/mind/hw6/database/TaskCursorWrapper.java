package com.example.mind.hw6.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.mind.hw6.model.Task;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);

    }

    public Task getTask(){
        UUID uuid  = UUID.fromString(getString(getColumnIndex(TaskDbSchema.TaskTable.Colms.UUID)));
        UUID user_uuid  =UUID.fromString(getString(getColumnIndex(TaskDbSchema.TaskTable.Colms.USER_UUID)));
        String title = getString(getColumnIndex(TaskDbSchema.TaskTable.Colms.TITLE));
        String description = getString(getColumnIndex(TaskDbSchema.TaskTable.Colms.DESCRIPTION));
        Date date = new Date(getLong(getColumnIndex(TaskDbSchema.TaskTable.Colms.DATE)));
        boolean done = getInt(getColumnIndex(TaskDbSchema.TaskTable.Colms.BOOLEAN_DONE)) !=0;

        Task toDoList = new Task(uuid , user_uuid);
        toDoList.setDone(done);
        toDoList.setTitle(title);
        toDoList.setDescription(description);
        toDoList.setDate(date);
        toDoList.setUserUUID(user_uuid);

        return toDoList;
    }

}

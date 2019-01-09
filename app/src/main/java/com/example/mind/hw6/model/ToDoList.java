package com.example.mind.hw6.model;

import android.support.annotation.Nullable;
import android.widget.CheckBox;

import java.util.Date;
import java.util.UUID;

public class ToDoList {
    private String mTitle;
    private String mDescription;
    private UUID mUUID;
    private Date mDate;
    private UUID mUserUUID;
    private boolean mDone;

    public UUID getUserUUID() {
        return mUserUUID;
    }

    public void setUserUUID(UUID userUUID) {
        mUserUUID = userUUID;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    public ToDoList(UUID user_uuid) {
        mUserUUID = user_uuid;
       mUUID=UUID.randomUUID();
        mDate = new Date();
        mDone=false;
    }
    public ToDoList( UUID uuid,UUID user_uuid){
        mUserUUID=user_uuid;
        mUUID=uuid;
        mDate = new Date();
        mDone=false;
    }

  /*  public ToDoList(String title, String description, boolean done) {
        mUUID = UUID.randomUUID();
       mDone =done;
        mTitle = title;
        mDescription = description;
        mDate = new Date();
    }*/
}

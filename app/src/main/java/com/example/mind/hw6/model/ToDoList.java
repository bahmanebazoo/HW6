package com.example.mind.hw6.model;

import java.util.Date;
import java.util.UUID;

public class ToDoList {
    private String mTitle;
    private String mDescription;
    private UUID mUUID;
    private Date mDate;
    private boolean mDone=false;

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

    public ToDoList(String title, String description,boolean done) {
        mDone=done;
        mTitle = title;
        mDescription = description;
       // mDate = date;
    }
}

package com.example.mind.hw6.model;

import java.util.Date;
import java.util.UUID;

public class Profile {
    private String mEmail;
    private String mName;
    private String mDescription;
    private UUID mUUID;
    private  Date mDate;
private int mCallNumber;
private String mPasssword;


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPasssword() {
        return mPasssword;
    }

    public void setPasssword(String passsword) {
        mPasssword = passsword;
    }

    public int getCallNumber() {
        return mCallNumber;
    }

    public void setCallNumber(int callNumber) {
        mCallNumber = callNumber;
    }

    public Profile(){
        this(UUID.randomUUID());


    }
    public Profile(UUID UUID) {
        mUUID = UUID;
    }


    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
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

    public UUID getUUID() {
        return mUUID;
    }
}

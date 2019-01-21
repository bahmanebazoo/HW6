package com.example.mind.hw6.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;
@Entity
public class Profile {
    @Unique
    private String mEmail;

    private String mName;
    private String mDescription;
    @Id(autoincrement = true)
    private Long mUserID;
    private Date mDate;
    private int mCallNumber;
    private String mPasssword;
    @Generated(hash = 356228986)
    public Profile(String mEmail, String mName, String mDescription, Long mUserID,
            Date mDate, int mCallNumber, String mPasssword) {
        this.mEmail = mEmail;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mUserID = mUserID;
        this.mDate = mDate;
        this.mCallNumber = mCallNumber;
        this.mPasssword = mPasssword;
    }
    @Generated(hash = 782787822)
    public Profile() {
    }
    public String getMEmail() {
        return this.mEmail;
    }
    public void setMEmail(String mEmail) {
        this.mEmail = mEmail;
    }
    public String getMName() {
        return this.mName;
    }
    public void setMName(String mName) {
        this.mName = mName;
    }
    public String getMDescription() {
        return this.mDescription;
    }
    public void setMDescription(String mDescription) {
        this.mDescription = mDescription;
    }
    public Long getMUserID() {
        return this.mUserID;
    }
    public void setMUserID(Long mUserID) {
        this.mUserID = mUserID;
    }
    public Date getMDate() {
        return this.mDate;
    }
    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }
    public int getMCallNumber() {
        return this.mCallNumber;
    }
    public void setMCallNumber(int mCallNumber) {
        this.mCallNumber = mCallNumber;
    }
    public String getMPasssword() {
        return this.mPasssword;
    }
    public void setMPasssword(String mPasssword) {
        this.mPasssword = mPasssword;
    }
    
  }
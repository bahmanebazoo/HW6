package com.example.mind.hw6.model;

import android.support.annotation.Nullable;
import android.widget.CheckBox;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.converter.PropertyConverter;

@Entity
public class Task {
    private String mTitle;
    private String mDescription;
    @Id(autoincrement = true)
    private Long mTaskID;
/*@Convert(converter = UUIDConverter.class,columnType = String.class)
    private UUID mUUID;*/
    private Date mDate;
    private Long mUserID;

    @ToOne(joinProperty = "mUserID")
    private Profile mProfile;

    //  private UUID mUserUUID;
    private boolean mDone;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1469429066)
    private transient TaskDao myDao;

    @Generated(hash = 470845414)
    public Task(String mTitle, String mDescription, Long mTaskID, Date mDate, Long mUserID, boolean mDone) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mTaskID = mTaskID;
        this.mDate = mDate;
        this.mUserID = mUserID;
        this.mDone = mDone;
    }

    @Generated(hash = 733837707)
    public Task(Long mUserID) {
    }

    public String getMTitle() {
        return this.mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMDescription() {
        return this.mDescription;
    }

    public void setMDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Long getMTaskID() {
        return this.mTaskID;
    }

    public void setMTaskID(Long mTaskID) {
        this.mTaskID = mTaskID;
    }

    public Date getMDate() {
        return this.mDate;
    }

    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }

    public Long getMUserID() {
        return this.mUserID;
    }

    public void setMUserID(Long mUserID) {
        this.mUserID = mUserID;
    }

    public boolean getMDone() {
        return this.mDone;
    }

    public void setMDone(boolean mDone) {
        this.mDone = mDone;
    }

    @Generated(hash = 1539386307)
    private transient Long mProfile__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 963230654)
    public Profile getMProfile() {
        Long __key = this.mUserID;
        if (mProfile__resolvedKey == null || !mProfile__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProfileDao targetDao = daoSession.getProfileDao();
            Profile mProfileNew = targetDao.load(__key);
            synchronized (this) {
                mProfile = mProfileNew;
                mProfile__resolvedKey = __key;
            }
        }
        return mProfile;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1075023695)
    public void setMProfile(Profile mProfile) {
        synchronized (this) {
            this.mProfile = mProfile;
            mUserID = mProfile == null ? null : mProfile.getMUserID();
            mProfile__resolvedKey = mUserID;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1442741304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTaskDao() : null;
    }


public static class UUIDConverter implements PropertyConverter<UUID ,String> {

    @Override
    public UUID convertToEntityProperty(String databaseValue) {
if(databaseValue==null)
    return null;


        return null;
    }

    @Override
    public String convertToDatabaseValue(UUID entityProperty) {
        return null;
    }
}



   /* public List getmProfileByEmail(String email){
       List<Profile> list =  mProfileDao.queryBuilder().where(ProfileDao.Properties.MEmail.eq(email)).list();
       return list;
    }*/

}
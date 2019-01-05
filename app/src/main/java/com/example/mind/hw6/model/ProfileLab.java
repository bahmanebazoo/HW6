package com.example.mind.hw6.model;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileLab {
    private static  ProfileLab profileInstance ;
    private  List<Profile> mProfiles=new ArrayList<>();
    private final UUID mUUIDGuest=null;


    public static ProfileLab getInstance() {
        if(profileInstance==null)
profileInstance=new ProfileLab();
        return profileInstance;
    }

    /*private ProfileLab() {
        mProfiles = new ArrayList<>();
    }*/

    public void addProfile(Context context, Profile profile) {
        mProfiles.add(profile);
        Toast.makeText(context, profile.getEmail() + "Sign Upped", Toast.LENGTH_SHORT);
    }

    public List<Profile> getList() {

        return mProfiles;
    }

    public Profile getProfile(UUID uuid) {
        for (int i = 0; i < mProfiles.size(); i++) {
            if (mProfiles.get(i).getUUID() == uuid)
                return mProfiles.get(i);

        }
        return null;
    }

    public int getIndexOfProfile(UUID uuid) {
        for (int i = 0; i < mProfiles.size(); i++) {
            if (mProfiles.get(i).getUUID() == uuid)
                return i;
        }
        return -1;
    }

    public void removeProfile(Context context, UUID uuid) {
        mProfiles.remove(getProfile(uuid));
        Toast.makeText(context, getProfile(uuid).getEmail() + "Profile Deleted", Toast.LENGTH_SHORT).show();
    }

    public Profile getProfileByEmail(String email) {
        for (int i = 0; i < getList().size(); i++) {
            if (mProfiles.get(i).getEmail().equals(email)){
                return mProfiles.get(i);
            }
        }
        return null;
    }
}

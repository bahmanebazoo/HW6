package com.example.mind.hw6.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.mind.hw6.model.Profile;

import java.util.Date;
import java.util.UUID;

public class ProfileCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ProfileCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Profile getProfile(){
        String name = getString(getColumnIndex(TaskDbSchema.ProfileTable.Colms.NAME));
        String description = getString(getColumnIndex(TaskDbSchema.ProfileTable.Colms.DESCRIPTION));
        String email = getString(getColumnIndex(TaskDbSchema.ProfileTable.Colms.E_MAIL));
        UUID uuid = UUID.fromString(getString(getColumnIndex(TaskDbSchema.ProfileTable.Colms.UUID)));
        String password = getString(getColumnIndex(TaskDbSchema.ProfileTable.Colms.PASSWORD));
        Date date = new Date(getLong(getColumnIndex(TaskDbSchema.ProfileTable.Colms.DATE)));
        int number = getInt(getColumnIndex(TaskDbSchema.ProfileTable.Colms.CALL_NUMBER));

        Profile profile = new Profile(uuid);
        profile.setName(name);
        profile.setDescription(description);
        profile.setEmail(email);
        profile.setPasssword(password);
        profile.setDate(date);
        profile.setCallNumber(number);

        return profile;
    }
}

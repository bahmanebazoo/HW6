package com.example.mind.hw6.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mind.hw6.model.DaoMaster;

public class AppDevOpenHelper extends DaoMaster.DevOpenHelper {

    public AppDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    public AppDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }


}

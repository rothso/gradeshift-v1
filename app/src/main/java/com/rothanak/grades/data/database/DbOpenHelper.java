package com.rothanak.grades.data.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DbOpenHelper extends SQLiteAssetHelper {

    private static final int VERSION = 1;
    private static final String NAME = "grades.db";

    public DbOpenHelper(Context context) {
        super(context, NAME, null /* factory */, VERSION);
    }
}

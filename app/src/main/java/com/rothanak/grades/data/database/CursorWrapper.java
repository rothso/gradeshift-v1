package com.rothanak.grades.data.database;

import android.database.Cursor;

public class CursorWrapper {

    private final Cursor cursor;

    public CursorWrapper(Cursor cursor) {
        this.cursor = cursor;
    }

    public String getStringFrom(String col) {
        return cursor.getString(fromColumn(col));
    }

    public int getIntFrom(String col) {
        return cursor.getInt(fromColumn(col));
    }

    public long getLongFrom(String col) {
        return cursor.getLong(fromColumn(col));
    }

    public double getDoubleFrom(String col) {
        return cursor.getDouble(fromColumn(col));
    }

    private int fromColumn(String columnName) {
        return cursor.getColumnIndexOrThrow(columnName);
    }

}

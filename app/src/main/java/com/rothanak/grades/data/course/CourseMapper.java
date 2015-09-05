package com.rothanak.grades.data.course;

import android.database.Cursor;

import com.rothanak.grades.data.database.CursorWrapper;

public class CourseMapper {

    public static Course map(Cursor cursor) {
        CursorWrapper row = new CursorWrapper(cursor);

        int yearId = row.getIntFrom("year");
        String name = row.getStringFrom("name");
        String instructor = row.getStringFrom("instructor");
        double grade = row.getDoubleFrom("grade");
        long period = row.getLongFrom("period");

        return new Course(yearId, name, instructor, (int) period, grade);
    }

}

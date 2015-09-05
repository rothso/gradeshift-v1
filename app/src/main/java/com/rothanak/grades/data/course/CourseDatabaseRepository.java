package com.rothanak.grades.data.course;

import com.rothanak.grades.data.auth.AuthFacade;
import com.rothanak.grades.data.database.ObservableCursor;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;
import rx.functions.Func2;

public class CourseDatabaseRepository implements CourseRepository {

    private final BriteDatabase db;
    private final AuthFacade authenticator;

    private final String COURSES_TABLE = "courses";

    public CourseDatabaseRepository(BriteDatabase db, AuthFacade authenticator) {
        this.db = db;
        this.authenticator = authenticator;
    }


    @Override
    public Observable<List<Course>> findByYear(final int yearId) {
        final String getCoursesQuery = String.format(
                "SELECT * FROM %s WHERE year = %d AND user_id = %d",
                COURSES_TABLE, yearId, authenticator.getUserId());

        return db.createQuery(COURSES_TABLE, getCoursesQuery)
                .flatMap(query -> ObservableCursor.from(query.run())
                        .map(CourseMapper::map)
                        .toList());
    }
}

package com.rothanak.grades.data.course;

import java.util.List;

import rx.Observable;

public class CourseCachingProxy implements CourseRepository {

    private CourseNetworkRepository network;
    private CourseDatabaseRepository database;

    public CourseCachingProxy(CourseNetworkRepository network, CourseDatabaseRepository database) {
        this.network = network;
        this.database = database;
    }

    /**
     * Returns a cached version of the data if it exists in the database, while also loading
     * fresh data in the background to be cached and ready for the next request.
     */
    @Override
    public Observable<List<Course>> findByYear(int yearId) {
        return null;
    }
}
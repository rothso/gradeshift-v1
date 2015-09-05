package com.rothanak.grades.data.course;

import java.util.List;

import rx.Observable;

public class CourseNetworkRepository implements CourseRepository {

    private final CourseSource provider;

    public CourseNetworkRepository(CourseSource provider) {
        this.provider = provider;
    }

    @Override
    public Observable<List<Course>> findByYear(final int yearId) {
        return null;
    }
}

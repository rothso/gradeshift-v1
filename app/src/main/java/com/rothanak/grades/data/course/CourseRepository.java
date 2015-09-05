package com.rothanak.grades.data.course;

import java.util.List;

import rx.Observable;

public interface CourseRepository {

    Observable<List<Course>> findByYear(int yearId);

}

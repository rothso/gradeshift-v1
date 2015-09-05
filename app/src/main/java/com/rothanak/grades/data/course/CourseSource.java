package com.rothanak.grades.data.course;

import rx.Observable;

public interface CourseSource {

    Observable<Course> fetchCourses(int yearId);

}

package com.rothanak.grades.data.course;

import com.rothanak.grades.model.*;
import com.rothanak.grades.model.Class;

import java.util.ArrayList;

import rx.Observable;

public interface CourseService {

    Observable<Course> getCoursesByYear(int yearId);

}

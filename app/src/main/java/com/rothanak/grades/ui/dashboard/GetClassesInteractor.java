package com.rothanak.grades.ui.dashboard;

import com.rothanak.grades.data.course.CourseService;
import com.rothanak.grades.ui.SchedulerStrategy;

import rx.Observable;

public class GetClassesInteractor {

    private CourseService service;
    private SchedulerStrategy scheduler;

    public GetClassesInteractor(CourseService service, SchedulerStrategy scheduler) {
        this.service = service;
        this.scheduler = scheduler;
    }

    public Observable getClasses(int yearId) {
        return scheduler.applySchedule(service.getCoursesByYear(yearId));
    }

}

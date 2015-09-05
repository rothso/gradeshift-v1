package com.rothanak.grades.ui.dashboard;

import com.rothanak.grades.data.course.CourseService;
import com.rothanak.grades.ui.SchedulerStrategy;
import com.rothanak.grades.model.Class;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import rx.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class GetClassesInteractorTest {

    @Mock CourseService service;
    private GetClassesInteractor interactor;

    private ArrayList<Class> dummyClasses;

    @Before
    public void init() {
        SchedulerStrategy scheduler = new SchedulerStrategy(Schedulers.immediate());
        interactor = new GetClassesInteractor(service, scheduler);

        dummyClasses = new ArrayList<>();
        dummyClasses.add(new Class(64242, 1, 85.0, "Name 1", "Teacher 1"));
        dummyClasses.add(new Class(64243, 2, 90.2, "Name 2", "Teacher 2"));
        dummyClasses.add(new Class(64244, 3, 24.5, "Name 3", "Teacher 3"));
        dummyClasses.add(new Class(64245, 4, 30.1, "Name 4", "Teacher 4"));
    }
}
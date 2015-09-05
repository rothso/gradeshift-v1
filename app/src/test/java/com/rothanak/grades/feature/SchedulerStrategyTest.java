package com.rothanak.grades.feature;

import com.rothanak.grades.ui.SchedulerStrategy;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;
import rx.Scheduler;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerStrategyTest {

    @Mock private Scheduler forExecutionScheduler;
    @Mock private Scheduler postExecutionScheduler;
    Observable mockObservable;

    private SchedulerStrategy strategy;

    @Before
    public void init() {
        mockObservable = spy(Observable.just(true));
    }

    // FIXME:
    // Mockito can't mock or spy final methods (e.g. subscribeOn and observeOn), add PowerMock
    // to the project for this kind of thing.

    /*
    @Test
    public void applySchedules_GivenSchedules_MapsProperly() {
        strategy = new SchedulerStrategy(forExecutionScheduler, postExecutionScheduler);

        strategy.applySchedule(mockObservable);

        verify(mockObservable).subscribeOn(forExecutionScheduler);
        verify(mockObservable).observeOn(postExecutionScheduler);
    }
    */

}
package com.rothanak.grades.ui;

import rx.Observable;
import rx.Scheduler;

public class SchedulerStrategy {

    private final Scheduler forExecution;
    private final Scheduler postExecution;

    public SchedulerStrategy(Scheduler entireExecution) {
        this(entireExecution, entireExecution);
    }

    public SchedulerStrategy(Scheduler forExecution, Scheduler postExecution) {
        this.forExecution = forExecution;
        this.postExecution = postExecution;
    }

    public Observable applySchedule(Observable observable) {
        return observable.subscribeOn(forExecution).observeOn(postExecution);
    }

}

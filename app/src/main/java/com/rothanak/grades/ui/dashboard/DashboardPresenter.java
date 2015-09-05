package com.rothanak.grades.ui.dashboard;

import com.rothanak.grades.ui.Presenter;

public class DashboardPresenter extends Presenter<DashboardPresenter.View> {

    private GetClassesInteractor interactor;

    public DashboardPresenter(GetClassesInteractor interactor) {
    }

    public interface View {
    }
}

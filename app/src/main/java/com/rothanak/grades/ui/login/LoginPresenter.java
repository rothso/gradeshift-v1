package com.rothanak.grades.ui.login;

import com.rothanak.grades.ui.Presenter;

public class LoginPresenter extends Presenter<LoginPresenter.View> {

    private final LoginInteractor loginInteractor;

    public LoginPresenter(LoginInteractor loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    public void performLogin() {
        final String username = getView().getUsernameField();
        final String password = getView().getPasswordField();

        getView().showLoading();

        // todo: unsubscribe on orientation change, resubscribe on rebind
        loginInteractor.login(username, password).subscribe(
                success -> {
                    if (success) getView().gotoDashboard();
                    else getView().showError();
                }
        );
    }

    public interface View {

        String getUsernameField();

        String getPasswordField();

        void gotoDashboard();

        void showError();

        void showLoading();
    }
}
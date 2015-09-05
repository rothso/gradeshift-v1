package com.rothanak.grades.ui.login;

import com.rothanak.grades.data.auth.AuthProvider;

import rx.Observable;

public class LoginInteractor {

    private AuthProvider authProvider;

    public LoginInteractor(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public Observable<Boolean> login(String username, String password) {
        return Observable.just(true);
    }

}

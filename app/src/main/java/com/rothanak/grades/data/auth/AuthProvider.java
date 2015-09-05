package com.rothanak.grades.data.auth;

import rx.Observable;

public interface AuthProvider {

    Observable<Boolean> login(String username, String password);

}

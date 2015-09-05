package com.rothanak.grades.data.auth;

import rx.Observable;

public interface AuthFacade {

    Observable<String> getAuthToken();

    int getUserId();

}
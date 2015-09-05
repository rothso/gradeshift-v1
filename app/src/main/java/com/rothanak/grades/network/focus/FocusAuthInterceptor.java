package com.rothanak.grades.network.focus;

import com.rothanak.grades.data.auth.AuthFacade;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import rx.Observable;

public class FocusAuthInterceptor implements Interceptor {

    private final AuthFacade auth;

    public FocusAuthInterceptor(AuthFacade auth) {
        this.auth = auth;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        // Append the auth token when requesting content from a particular path
        Observable.just(request.uri().getPath())
                .filter(path -> path.equals("/Modules.php"))
                .flatMap(path -> auth.getAuthToken())
                .subscribe(token -> requestBuilder.addHeader("Cookie", token));

        return chain.proceed(requestBuilder.build());
    }
}

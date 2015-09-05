package com.rothanak.grades.network.focus;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;

import javax.inject.Inject;

import rx.Observable;

public class AddCookiesInterceptor implements Interceptor {

    private final CookieManager cookieManager;

    @Inject public AddCookiesInterceptor(CookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        Observable.from(cookieManager.getCookieStore().getCookies()).subscribe(cookie ->
            requestBuilder.addHeader("Cookie", cookie.getName() + "=" + cookie.getValue())
        );

        return chain.proceed(requestBuilder.build());
    }

}

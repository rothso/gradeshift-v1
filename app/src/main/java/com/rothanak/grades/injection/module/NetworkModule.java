package com.rothanak.grades.injection.module;

import com.rothanak.grades.network.focus.AddCookiesInterceptor;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import retrofit.client.OkClient;

@Module
public class NetworkModule {

    @Provides CookieManager provideCookieManager() {
        return new CookieManager(null, CookiePolicy.ACCEPT_ALL);
    }

    @Provides List<Interceptor> provideClientInterceptors(CookieManager cookieManager) {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new AddCookiesInterceptor(cookieManager));
        return interceptors;
    }

    @Provides OkHttpClient provideOkHttpClient(CookieManager cookieManager, List<Interceptor> interceptors) {
        OkHttpClient client = new OkHttpClient();
        client.setCookieHandler(cookieManager);
        client.interceptors().addAll(interceptors);
        return client;
    }

    @Provides OkClient provideOkClient(OkHttpClient client) {
        return new OkClient(client);
    }

}

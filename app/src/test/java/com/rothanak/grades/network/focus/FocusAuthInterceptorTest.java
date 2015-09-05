package com.rothanak.grades.network.focus;

import com.rothanak.grades.data.auth.AuthFacade;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import com.squareup.okhttp.mockwebserver.rule.MockWebServerRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import rx.Observable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FocusAuthInterceptorTest {

    @Rule public MockWebServerRule server = new MockWebServerRule();

    private final AuthFacade auth = mock(AuthFacade.class);
    private final FocusAuthInterceptor interceptor = new FocusAuthInterceptor(auth);

    private final OkHttpClient client = new OkHttpClient();
    private final String COOKIE_AUTH = "SomeCookie=1; AnotherCookie=2";

    @Before
    public void init() {
        when(auth.getAuthToken()).thenReturn(Observable.just(COOKIE_AUTH));
    }

    @Test
    public void interceptRequest_WithinPath_AddsAuthCookies() throws IOException, InterruptedException {
        server.enqueue(new MockResponse());

        // Assign interceptor to client and send a request
        client.interceptors().add(interceptor);
        client.newCall(request("/Modules.php")).execute();

        RecordedRequest request = server.takeRequest();
        assertThat(request.getHeader("Cookie"), is(equalTo(COOKIE_AUTH)));
    }

    @Test
    public void interceptRequest_OutOfPath_DoesNotModify() throws IOException, InterruptedException {
        server.enqueue(new MockResponse());

        // Assign interceptor to client and send a request
        client.interceptors().add(interceptor);
        client.newCall(request("/NotModules.php")).execute();

        RecordedRequest request = server.takeRequest();
        assertThat(request.getHeader("Cookie"), is(nullValue()));
    }

    private Request request(String url) {
        return new Request.Builder().url(server.getUrl(url)).build();
    }
}
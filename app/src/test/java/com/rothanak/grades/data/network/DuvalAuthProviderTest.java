package com.rothanak.grades.data.network;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.rothanak.grades.network.focus.DuvalAuthProvider;
import com.rothanak.grades.network.focus.api.DuvalAuthApi;
import com.rothanak.grades.network.focus.api.FocusAuthApi;
import com.rothanak.grades.network.focus.AddCookiesInterceptor;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DuvalAuthProviderTest {

    private DuvalAuthProvider authService;

    @Rule public WireMockRule focusServer = new WireMockRule(8081);
    @Rule public WireMockRule duvalServer = new WireMockRule(8082);

    private final String FOCUS_ENDPOINT = "http://localhost:8081";  // https://duval.focusschoolsoftware.com/focus
    private final String DUVAL_ENDPOINT = "http://localhost:8082";  // https://fs.duvalschools.org/adfs/ls

    @Before
    public void init() {
        initMockServer();

        CookieManager cookieManager = provideCookieManager();
        List<Interceptor> interceptors = provideClientInterceptors(cookieManager);
        OkHttpClient okHttpClient = provideOkHttpClient(cookieManager, interceptors);
        OkClient client = provideOkClient(okHttpClient);
        FocusAuthApi focusApi = buildApi(client, FocusAuthApi.class, FOCUS_ENDPOINT);
        DuvalAuthApi duvalApi = buildApi(client, DuvalAuthApi.class, DUVAL_ENDPOINT);

        authService = provideAuthService(focusApi, duvalApi);
    }

    private void initMockServer() {
        focusServer.stubFor(get(urlEqualTo("/"))
                .willReturn(aResponse().withStatus(302).withHeader("Location", "/?SAMLRequest=1")));

        focusServer.stubFor(get(urlPathEqualTo("/"))
                .withQueryParam("SAMLRequest", equalTo("1"))
                .willReturn(aResponse().withBody("Redirecting")));

        duvalServer.stubFor(post(urlPathEqualTo("/")).atPriority(2)
                .withQueryParam("RedirectToIdentityProvider", equalTo("urn%3Afederation%3ADCPS"))
                .willReturn(aResponse().withBodyFile("mock_bad_login.html")));

        duvalServer.stubFor(post(urlPathEqualTo("/")).atPriority(1)
                .withQueryParam("RedirectToIdentityProvider", equalTo("urn%3Afederation%3ADCPS"))
                .withQueryParam("SAMLRequest", equalTo("1"))
                .withRequestBody(equalTo("UserName=DCPS%5CUsername&Password=Password"))
                .willReturn(aResponse().withBodyFile("mock_saml_response.html")));

        focusServer.stubFor(post(urlPathMatching("/simplesaml/*"))
                .withRequestBody(equalTo("SAMLResponse=A"))
                .willReturn(aResponse().withBodyFile("mock_authorized_dash.html")));
    }

    @Test
    public void authenticateUser_WithGoodLogin_ReturnsTrue() {
        boolean success = authService.login("Username", "Password").toBlocking().first();
        assertTrue(success);
    }

    @Test
    public void authenticateUser_WithBadLogin_ReturnsFalse() {
        boolean success = authService.login("BadUsername", "BadPassword").toBlocking().first();
        assertFalse(success);
    }

    public CookieManager provideCookieManager() {
        return new CookieManager(null, CookiePolicy.ACCEPT_ALL);
    }

    private List<Interceptor> provideClientInterceptors(CookieManager cookieManager) {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new AddCookiesInterceptor(cookieManager));
        return interceptors;
    }

    private OkHttpClient provideOkHttpClient(CookieManager cookieManager, List<Interceptor> interceptors) {
        OkHttpClient client = new OkHttpClient();
        client.setCookieHandler(cookieManager);
        client.interceptors().addAll(interceptors);
        return client;
    }

    private OkClient provideOkClient(OkHttpClient client) {
        return new OkClient(client);
    }

    private <T> T buildApi(OkClient client, Class<T> service, String endpoint) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setEndpoint(endpoint)
                .setClient(client)
                .build();
        return restAdapter.create(service);
    }

    private DuvalAuthProvider provideAuthService(FocusAuthApi focus, DuvalAuthApi duval) {
        return new DuvalAuthProvider(focus, duval);
    }
}

package com.rothanak.grades.network.focus;

import com.rothanak.grades.data.auth.AuthProvider;
import com.rothanak.grades.network.focus.api.DuvalAuthApi;
import com.rothanak.grades.network.focus.api.FocusAuthApi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URI;

import javax.inject.Inject;

import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import rx.Observable;

public class DuvalAuthProvider implements AuthProvider {

    private final FocusAuthApi focusApi;
    private final DuvalAuthApi duvalApi;

    @Inject
    public DuvalAuthProvider(FocusAuthApi focusApi, DuvalAuthApi duvalApi) {
        this.focusApi = focusApi;
        this.duvalApi = duvalApi;
    }

    @Override public Observable<Boolean> login(String user, String pass) {
        return focusApi.load()
                .flatMap(response -> duvalApi.login(getRequestKey(response), "DCPS\\" + user, pass))
                .map(this::getResponseKey)
                .filter(responseKey -> responseKey != null && !responseKey.isEmpty())
                .flatMap(focusApi::completeLogin)
                .map(response -> true)
                .defaultIfEmpty(false);
    }

    private String getRequestKey(Response response) {
        URI uri = URI.create(response.getUrl());
        return uri.getQuery().split("SAMLRequest=|&")[1];
    }

    private String getResponseKey(Response response) {
        Document document = Jsoup.parse(new String(((TypedByteArray) response.getBody()).getBytes()));
        Elements inputs = document.getElementsByAttributeValue("name", "SAMLResponse");
        return inputs.isEmpty() ? null : inputs.first().attr("value");
    }
}
package com.rothanak.grades.network.focus.api;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

public interface FocusAuthApi {
        @GET("/") Observable<Response> load();

        @FormUrlEncoded
        @POST("/simplesaml/module.php/saml/sp/saml2-acs.php/default-sp")
        Observable<Response> completeLogin(@Field("SAMLResponse") String responseKey);
}

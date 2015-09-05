package com.rothanak.grades.network.focus.api;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

public interface DuvalAuthApi {
        @FormUrlEncoded
        @POST("/?RedirectToIdentityProvider=urn%3Afederation%3ADCPS") Observable<Response> login(
                @Query("SAMLRequest") String requestKey,
                @Field("UserName") String username,
                @Field("Password") String password
        );
}
